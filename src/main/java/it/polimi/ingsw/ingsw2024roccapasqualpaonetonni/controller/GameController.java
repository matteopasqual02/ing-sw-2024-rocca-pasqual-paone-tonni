package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.DeckEmptyException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.NoCardException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultControllerValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultModelValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.JSONUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Game controller.
 */
public class GameController implements GameControllerInterface {
    /**
     * The Model.
     */
    private final Game model;
    /**
     * The Random number to choose the first player.
     */
    private final Random random;
    /**
     * The Json Path to load the cards.
     */
    private final String path;

    /**
     * The Executor service.
     * the executor is a thread that can be fed a queue of Runnable or Callable, such as lambda expressions or
     * method-like expressions, and that executes them in order
     * it is used to de-synchronize the RMI calls, that now don't wait for the return at the end of the method
     * execution, but return after submitting the Runnable to the executor
     */
    private transient final ExecutorService executorService;

    /**
     * The Ping pong thread.
     */
    private transient final PingPongThread pingPongThread;

    /**
     * The Timer.
     */
    TimerReconnection timer;

    /**
     * Instantiates a new Game controller.
     *
     * @param id the id
     * @throws RemoteException the remote exception
     */
    public GameController(int id) throws RemoteException {
        super();
        this.model = new Game(id);
        this.random = new Random();
        this.path = DefaultControllerValues.jsonPath;
        this.executorService = Executors.newSingleThreadExecutor();
        this.pingPongThread = new PingPongThread();
        this.pingPongThread.start();
        this.timer = new TimerReconnection(model);
        this.timer.start();
    }

//---------------------------------SERVER SECTION
    /**
     * The type Ping pong thread.
     */
    private class PingPongThread extends Thread {

        /**
         * The Clients running.
         */
        private final List<String> clientsRunning = new ArrayList<>();
        /**
         * The Clients.
         */
        private final List<String> clients = new ArrayList<>();

        /**
         * Add client.
         *
         * @param client the client
         */
        private void addClient(String client) {
            synchronized (clientsRunning) {
                clientsRunning.add(client);
                //ConsolePrinter.consolePrinter("added listener " + client);
            }
        }

        /**
         * Pong.
         *
         * @param client the client
         */
        private void pong(String client) {
            synchronized (clientsRunning) {
                clientsRunning.add(client);
                //ConsolePrinter.consolePrinter("ponged " + client);
            }
        }

        /**
         * Run.
         */
        @Override
        public void run() {
            while (true) {
                // Send ping message to client
                synchronized (clientsRunning) {
                    clients.clear();
                    clients.addAll(clientsRunning);
                    clientsRunning.clear();
                }

                for (String client : clients) {
                    try {
                        model.ping(client);
                        //ConsolePrinter.consolePrinter("pinging " + client);
                    }
                    catch (Exception ignored) {}
                }

                // Wait for a certain period before sending the next ping
                try {
                    Thread.sleep(5000); // 1 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (clientsRunning) {
                    for (String client : clientsRunning) {
                        //ConsolePrinter.consolePrinter("safe " + client);
                        clients.remove(client);
                    }
                }

                // ping again whoever didn't ping back, to give another opportunity
                for (String client : clients) {
                    try {
                        model.ping(client);
                        ConsolePrinter.consolePrinter("Re pinging " + client);
                    }
                    catch (Exception ignored) {}
                }

                try {
                    Thread.sleep(5000); // 1 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (clientsRunning) {
                    for (String client : clientsRunning) {
                        ConsolePrinter.consolePrinter("safe " + client);
                        clients.remove(client);
                    }
                }

                for (String deadClient : clients) {
                    ConsolePrinter.consolePrinter("dead client " + deadClient);
                    if(!model.getPlayersDisconnected().stream().map(Player::getNickname).toList().contains(deadClient)){
                        disconnectPlayer(deadClient);
                        ConsolePrinter.consolePrinter("[DISCONNECTED] player " + deadClient);
                    }
                }
            }
        }
    }

    /**
     * Gets game id.
     *
     * @return the game id
     */
    public synchronized int getGameID() {
        return model.getGameId();
    }

    /**
     * Pong.
     *
     * @param client the client
     * @throws RemoteException the remote exception
     */
    @Override
    public void pong(String client) throws RemoteException {
        Runnable runnable = () -> this.pingPongThread.pong(client);
        executorService.submit(runnable);
    }

    /**
     * Add to ping pong.
     *
     * @param client the client
     * @throws RemoteException the remote exception
     */
    public void addToPingPong(String client) throws RemoteException {
        this.pingPongThread.addClient(client);
    }

//---------------------------------LISTENERS SECTION
    /**
     * Add myself as listener.
     *
     * @param me       me
     * @param notifier the notifier
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized void addMyselfAsListener(String me, NotifierInterface notifier) throws RemoteException{
        model.addListeners(me, notifier);
    }

    /**
     * Remove myself as listener.
     *
     * @param me me
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized void removeMyselfAsListener(String me) throws RemoteException {
        Runnable runnable = () -> model.removeListener(me);
        executorService.submit(runnable);
    }

//---------------------------------GAME CREATION PHASE
    /**
     * Add player.
     *
     * @param nickname the nickname
     */
    @Override
    public synchronized void addPlayer(String nickname) {
        Player px;
        int player_number = model.getPlayerNum() + 1;
        px = new Player(nickname, player_number);
        try {
            model.addPlayer(px);
        } catch (GameAlreadyFullException | PlayerAlreadyInException e) {
            e.printStackTrace();
        }
        if (model.getPlayerNum() == model.getMaxNumberOfPlayer()) {
            Runnable runnable = model::askPlayersReady;
            executorService.submit(runnable);
        }
    }

    /**
     * Sets max number of player.
     *
     * @param num the num
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized void setMaxNumberOfPlayer(int num) throws RemoteException {
        model.setMaxNumberOfPlayer(num);
    }

    /**
     * Ready.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized void ready(String nickname) throws RemoteException {
        Runnable runnable = () -> {
            model.setPlayerReady(nickname);
            if (model.getReadyPlayersNum() == model.getPlayerNum()) {
                //create Table
                createTable();
                //random first player
                randomFirstPlayer();
                //run TurnZero
                turnZero();
                //set Running
                model.setStatus(GameStatus.RUNNING);
                //notify ALL
                model.gameReady();
            }
        };
        executorService.submit(runnable);
    }

//---------------------------------PLAYER SECTION

    /**
     * Gets all player.
     *
     * @return all players
     */
    public synchronized Queue<Player> getAllPlayer() {
        return model.getPlayers();
    }

    /**
     * Gets all players disconnected.
     *
     * @return all disconnected players
     */
    public List<Player> getAllDisconnectedPlayer() {
        return model.getPlayersDisconnected();
    }

    /**
     * Gets current player.
     *
     * @return the current player
     */
    public synchronized Player getCurrentPlayer() {
        return model.getCurrentPlayer();
    }

    /**
     * Gets max number of player.
     *
     * @return the max number of player
     */
    public synchronized int getMaxNumberOfPlayer() {
        return model.getMaxNumberOfPlayer();
    }

    /**
     * Reconnect player.
     *
     * @param nickname the nickname
     */
    public synchronized void reconnectPlayer(String nickname) {
        timer.stopMyTimer();
        model.reconnectPlayer(nickname);
        if (GameStatus.WAITING_RECONNECTION == getGame().getGameStatus() && model.getMaxNumberOfPlayer() - model.numberDisconnectedPlayers() > 1) {
            model.setStatus(model.getLastStatus());
            model.resetLastStatus();
        }
        model.notifyAllGame();

    }

    /**
     * Disconnect player.
     *
     * @param nickname the nickname
     */
    public synchronized void disconnectPlayer(String nickname) {
        model.disconnectPlayer(nickname);
        // if (model.getMaxNumberOfPlayer() - model.numberDisconnectedPlayers() == 1) {
        if (model.getPlayerNum() == 0) {
            MainController.getInstance().removeGame(this);
            return;
        }
        if (model.getPlayerNum() == 1) {
            model.setLastStatus();
            model.setStatus(GameStatus.WAITING_RECONNECTION);

            timer.startMyTimer();
        }

    }

//---------------------------------TABLE AND INIT SECTION

    /**
     * Create table.
     */
    public synchronized void createTable() {
        Map<String, List<Card>> cardsMap = null;
        while (cardsMap == null) {
            try {
                cardsMap = JSONUtils.createCardsFromJson(path);
            } catch (IOException e) {
                ConsolePrinter.consolePrinter("[ERROR]: path doesn't contain cards");
            }
        }

        Map<String, Queue<Card>> shuffledDecks = new HashMap<>();
        for (Map.Entry<String, List<Card>> entry : cardsMap.entrySet()) {
            String type = entry.getKey(); // Get the card type
            List<Card> cards = entry.getValue(); // Get the list of cards for this type

            // Shuffle the list of cards
            Collections.shuffle(cards);

            // Create a new deck as a Queue
            Queue<Card> deck = new LinkedList<>(cards);

            // Put the shuffled deck into the map
            shuffledDecks.put(type, deck);
        }
        //create decks
        DrawableDeck decks = new DrawableDeck(shuffledDecks);
        BoardDeck boardDeck = new BoardDeck(model);

        //set the BoardDeck
        try {
            boardDeck.setObjectiveCards(decks.drawFirstObjective(), 0);
            boardDeck.setObjectiveCards(decks.drawFirstObjective(), 1);
            boardDeck.setResourceCards(decks.drawFirstResource(), 0);
            boardDeck.setResourceCards(decks.drawFirstResource(), 1);
            boardDeck.setGoldCards(decks.drawFirstGold(), 0);
            boardDeck.setGoldCards(decks.drawFirstGold(), 1);

        } catch (DeckEmptyException e) {
            ConsolePrinter.consolePrinter("[ERROR]: set the BoardDeck");
        }

        model.setGameDrawableDeck(decks);
        model.setGameBoardDeck(boardDeck);

    }

    /**
     * Random first player.
     */
    public synchronized void randomFirstPlayer() {
        int first = random.nextInt(4);

        for (int i = 0; i < first; i++) {
            model.nextPlayer();
        }

        model.setFirstPlayer(model.getCurrentPlayer());
    }

    /**
     * Turn zero.
     */
    public synchronized void turnZero() {
        for (Player player : getAllPlayer()) {
            try {
                player.drawStarting(model.getGameDrawableDeck());
                player.drawGoals(model.getGameDrawableDeck());
                player.drawResourcesFromDeck(model.getGameDrawableDeck());
                player.drawResourcesFromDeck(model.getGameDrawableDeck());
                player.drawGoldFromDeck(model.getGameDrawableDeck());

            } catch (DeckEmptyException e) {
                ConsolePrinter.consolePrinter("[ERROR]: turn zero failed");
            }
        }

    }

//---------------------------------ADD CARD SECTION
    /**
     * Add a card to the board.
     *
     * @param nickname       the nickname
     * @param cardToAdd      the card to add
     * @param cardOnBoard    the card on board
     * @param cornerToAttach the corner to attach
     * @param flip           the flip
     */
    @Override
    public synchronized void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) {
        Runnable runnable = () -> {
            if (!getCurrentPlayer().getNickname().equals(nickname)) {
                model.gameError("Not your turn");
                // listener invalid action
                return;
            }
            if (getCurrentPlayer().getHand().size() < DefaultModelValues.Default_Hand_Dimension) {
                model.gameError("You cannot add two cards in a turn");
                // listener invalid action
                return;
            }
            if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN) || model.getGameStatus().equals(GameStatus.LAST_TURN))) {
                // listener you cannot draw in this phase
                model.gameError("You cannot add a Card in this Phase");
                return;
            }
            if (flip) {
                cardToAdd.flip();
            }
            boolean done = getCurrentPlayer().addToBoard(cardToAdd, cardOnBoard, cornerToAttach);
            if (GameStatus.RUNNING == model.getGameStatus()) {
                checkPoints20Points();
            }
            if (GameStatus.LAST_TURN == model.getGameStatus() && done) {
                model.nextPlayer();
            }
        };
        executorService.submit(runnable);
    }

    /**
     * Add starting card.
     *
     * @param nickname the nickname
     * @param flip     the flip
     */
    @Override
    public synchronized void addStartingCard(String nickname, Boolean flip) {
        Runnable runnable = () -> {
            if (!getCurrentPlayer().getNickname().equals(nickname)) {
                // listener invalid action
                model.gameError("Not your turn");
                return;
            }
            if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN) || model.getGameStatus().equals(GameStatus.LAST_TURN))) {
                // listener you cannot draw in this phase
                model.gameError("You cannot add a Starting Card in this Phase");
                return;
            }
            if (flip) {
                getCurrentPlayer().getStartingCard().flip();
            }
            getCurrentPlayer().addStarting();
        };
        executorService.submit(runnable);
    }

    /**
     * Choose player goal.
     *
     * @param nickname the nickname
     * @param choice   the choice
     */
    @Override
    public synchronized void choosePlayerGoal(String nickname, int choice) {
        Runnable runnable = () -> {
            if (!getCurrentPlayer().getNickname().equals(nickname)) {
                // listener invalid action
                model.gameError("Not your turn");
                return;
            }
            if (!(model.getGameStatus().equals(GameStatus.RUNNING))) {
                // listener you cannot draw in this phase
                model.gameError("You cannot choose the Objective Card in this Phase");
                return;
            }
            getCurrentPlayer().chooseGoal(choice);
        };
        executorService.submit(runnable);
    }

//---------------------------------DRAW SECTION
    /**
     * Decks are all empty boolean.
     *
     * @return the boolean
     */
    private boolean decksAreAllEmpty() {
        return model.getGameDrawableDeck().getDecks().get("resources").isEmpty()
                && model.getGameDrawableDeck().getDecks().get("gold").isEmpty()
                && model.getGameBoardDeck().isEmpty();
    }

    /**
     * Draw resource from deck.
     *
     * @param nickname the nickname
     */
    @Override
    public synchronized void drawResourceFromDeck(String nickname) {
        Runnable runnable = () -> {
            if (!getCurrentPlayer().getNickname().equals(nickname)) {
                // listener invalid action
                model.gameError("Not your turn");
                return;
            }
            if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN))) {
                // listener you cannot draw in this phase
                model.gameError("You cannot draw a Resource Card in this phase");
                return;
            }
            if (getCurrentPlayer().getHand().size() >= DefaultModelValues.Default_Hand_Dimension) {
                // listener you cannot draw in this phase
                model.gameError("You cannot draw before a card is placed");
                return;
            }
            if (decksAreAllEmpty()) {
                model.setStatus(GameStatus.WAITING_LAST_TURN);
            } else {
                try {
                    getCurrentPlayer().drawResourcesFromDeck(model.getGameDrawableDeck());
                    model.nextPlayer();
                } catch (DeckEmptyException e) {
                    // listener change deck
                    model.gameError("Resource deck is empty");
                }
            }
        };
        executorService.submit(runnable);
    }

    /**
     * Draw gold from deck.
     *
     * @param nickname the nickname
     */
    @Override
    public synchronized void drawGoldFromDeck(String nickname) {
        Runnable runnable = () -> {
            if (!getCurrentPlayer().getNickname().equals(nickname)) {
                // listener invalid action
                model.gameError("Not your turn");
                return;
            }
            if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN))) {
                // listener you cannot draw in this phase
                model.gameError("You cannot draw a Gold Card in this phase");
                return;
            }
            if (getCurrentPlayer().getHand().size() >= DefaultModelValues.Default_Hand_Dimension) {
                // listener you cannot draw in this phase
                model.gameError("You cannot draw before a card is placed");
                return;
            }
            if (decksAreAllEmpty()) {
                model.setStatus(GameStatus.WAITING_LAST_TURN);

            } else {
                try {
                    getCurrentPlayer().drawGoldFromDeck(model.getGameDrawableDeck());
                    model.nextPlayer();
                } catch (DeckEmptyException e) {
                    // listener change deck
                    model.gameError("Gold deck is empty");
                }
            }
        };
        executorService.submit(runnable);
    }

    /**
     * Draw from board.
     *
     * @param nickname the nickname
     * @param position the position
     */
    @Override
    public synchronized void drawFromBoard(String nickname, int position) {
        Runnable runnable = () -> {
            if (!getCurrentPlayer().getNickname().equals(nickname)) {
                // listener invalid action
                model.gameError("Not your turn");
                return;
            }
            if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN))) {
                // listener you cannot draw in this phase
                model.gameError("You cannot draw from Common Board in this phase");
                return;
            }
            if (getCurrentPlayer().getHand().size() >= DefaultModelValues.Default_Hand_Dimension) {
                // listener you cannot draw in this phase
                model.gameError("You cannot draw before a card is placed");
                return;
            }
            if (decksAreAllEmpty()) {
                model.setStatus(GameStatus.WAITING_LAST_TURN);

            } else {
                try {
                    getCurrentPlayer().drawFromBoard(position, model.getGameBoardDeck());
                    model.nextPlayer();
                } catch (NoCardException e) {
                    model.gameError("This position is empty");
                }
            }
        };
        executorService.submit(runnable);
    }

//---------------------------------CHAT
    /**
     * Send message.
     *
     * @param txt      the txt
     * @param nickname the nickname
     */
    @Override
    public void sendMessage(String txt, String nickname) {
        Runnable runnable = () -> model.sendMessage(txt, nickname);
        executorService.submit(runnable);
    }

    /**
     * Send private message.
     *
     * @param senderName   the sender name
     * @param receiverName the receiver name
     * @param txt          the txt
     */
    @Override
    public void sendPrivateMessage(String senderName, String receiverName, String txt) {
        Runnable runnable = () -> model.sendPrivateMessage(senderName, receiverName, txt);
        executorService.submit(runnable);
    }

    /**
     * Gets public chat log.
     *
     * @param requesterName the requester name
     * @throws RemoteException the remote exception
     */
    @Override
    public void getPublicChatLog(String requesterName) throws RemoteException {
        Runnable runnable = () -> model.getPublicChatLog(requesterName);
        executorService.submit(runnable);
    }

    /**
     * Gets private chat log.
     *
     * @param yourName  your name
     * @param otherName the other name
     * @throws RemoteException the remote exception
     */
    @Override
    public void getPrivateChatLog(String yourName, String otherName) throws RemoteException {
        Runnable runnable = () -> model.getPrivateChatLog(yourName, otherName);
        executorService.submit(runnable);
    }

//---------------------------------GAME ID
    /**
     * Gets game id.
     *
     * @return the game id
     */
    @Override
    public int getGameId() {
        return model.getGameId();
    }

//---------------------------------CHECK END SECTION
    /**
     * Check points 20 points.
     */
    private synchronized void checkPoints20Points() {
        for (Player player : getAllPlayer()) {
            // ATTENTION: the current player needs to be updated at the end of turn, before using this function
            if (player.getCurrentPoints() >= DefaultModelValues.Default_LastTurn_Points) {
                model.setStatus(GameStatus.WAITING_LAST_TURN);
            }
        }
    }

//---------------------------------GET SECTION TO DISPLAY THE PUBLIC PART

    /**
     * Gets game.
     *
     * @return the game
     */
    public Game getGame() {
        return model;
    }

}
