package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.DeckEmptyException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.NoCardException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
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
     * The Random number for first player.
     */
    private final Random random;
    /**
     * The Path.
     */
    private final String path;

    /**
     * The Executor service.
     */
    private transient final ExecutorService executorService;

    /**
     * The Ping pong thread.
     */
    private transient final PingPongThread pingPongThread;

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
    }

    //---------------------------------EXECUTOR SECTION

    /**
     * the executor is a thread that can be fed a queue of Runnable or Callable, such as lambda expressions or
     * method-like expressions, and that executes them in order
     * it is used to de-synchronize the RMI calls, that now don't wait for the return at the end of the method
     * execution, but return after submitting the Runnable to the executor
     */
    private void stopExecutor() {
        executorService.shutdown();
    }

    //---------------------------------SERVER SECTION

    /**
     * The type Ping pong thread.
     */
    private class PingPongThread extends Thread {

        /**
         * The Clients running.
         */
        List<String> clientsRunning = new ArrayList<>();
        /**
         * The Clients.
         */
        List<String> clients;

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
                    clients = new ArrayList<>(clientsRunning);
                    clientsRunning.clear();
                }
                for (String client : clients) {
                    try {
                        model.ping(client);
                        //ConsolePrinter.consolePrinter("pinging " + client);
                    }
                    catch (Exception e) {

                    }
                }

                // Wait for a certain period before sending the next ping
                try {
                    Thread.sleep(1000); // 1 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (clientsRunning) {
                    for (String client : clientsRunning) {
                        //ConsolePrinter.consolePrinter("safe " + client);
                        clients.remove(client);
                    }
                }
                for (String deadClient : clients) {
                    //ConsolePrinter.consolePrinter("dead client " + deadClient);

                    // CONTROLLARE SE FUNZIONA CON REMOTE OBJECT
                    disconnectPlayer(deadClient);
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
        this.pingPongThread.pong(client);
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

    /**
     * Add myself as listener.
     *
     * @param me       my nickname
     * @param notifier the notifier
     * @throws RemoteException the remote exception
     */
//---------------------------------LISTENERS SECTION
    @Override
    public synchronized void addMyselfAsListener(String me, NotifierInterface notifier) throws RemoteException{
        model.addListeners(me, notifier);
    }

    /**
     * Remove myself as listener.
     *
     * @param me my nickname
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized void removeMyselfAsListener(String me) throws RemoteException {
        Runnable runnable = () -> {
            model.removeListener(me);
        };
        executorService.submit(runnable);
    }

    /**
     * Add player.
     *
     * @param nickname the nickname
     */
//---------------------------------GAME CREATION PHASE
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

    }

    /**
     * Gets all player.
     *
     * @return the all player
     */
//---------------------------------PLAYER SECTION
    public synchronized Queue<Player> getAllPlayer() {
        return model.getPlayers();
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
        model.reconnectPlayer(nickname);
        if (model.getMaxNumberOfPlayer() - model.numberDisconnectedPlayers() > 1) {
            model.setStatus(model.getLastStatus());
            model.resetLastStatus();
        }
    }

    /**
     * Disconnect player.
     *
     * @param nickname the nickname
     */
    public synchronized void disconnectPlayer(String nickname) {
        model.disconnectPlayer(nickname);
        // if (model.getMaxNumberOfPlayer() - model.numberDisconnectedPlayers() == 1) {
        if (model.getPlayerNum() == 1) {
            model.setLastStatus();
            model.setStatus(GameStatus.WAITING_RECONNECTION);
        }
        else if (model.getPlayerNum() == 0) {
            MainController.getInstance().removeGame(this);
        }
    }

    /**
     * Gets last status.
     *
     * @return the last status
     */
    public synchronized GameStatus getLastStatus() {
        return model.getLastStatus();
    }

    //---------------------------------TABLE AND INIT SECTION
    /**
     * Create table.
     */
    public synchronized void createTable() {
        Map<String, List<Card>> cardsMap = null;
        try {
            cardsMap = JSONUtils.createCardsFromJson(path);
        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }
        try {
            boardDeck.setObjectiveCards(decks.drawFirstObjective(), 1);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }
        try {
            boardDeck.setResourceCards(decks.drawFirstResource(), 0);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }
        try {
            boardDeck.setResourceCards(decks.drawFirstResource(), 1);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }
        try {
            boardDeck.setGoldCards(decks.drawFirstGold(), 0);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }
        try {
            boardDeck.setGoldCards(decks.drawFirstGold(), 1);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }

        model.setGameDrawableDeck(decks);
        model.setGameBoardDeck(boardDeck);

    }

    /**
     * Random first player.
     */
    private synchronized void randomFirstPlayer() {
        int first = random.nextInt(4);

        for (int i = 0; i < first; i++) {
            model.nextPlayer();
        }

        model.setFirstPlayer(model.getCurrentPlayer());
    }

    /**
     * Turn zero.
     */
    private synchronized void turnZero() {
        for (Player player : getAllPlayer()) {
            try {
                player.drawStarting(model.getGameDrawableDeck());
            } catch (DeckEmptyException e) {
                e.printStackTrace();
            }
            try {
                player.drawGoals(model.getGameDrawableDeck());
            } catch (DeckEmptyException e) {
                e.printStackTrace();
            }
            try {
                player.drawResourcesFromDeck(model.getGameDrawableDeck());
            } catch (DeckEmptyException e) {
                e.printStackTrace();
            }
            try {
                player.drawResourcesFromDeck(model.getGameDrawableDeck());
            } catch (DeckEmptyException e) {
                e.printStackTrace();
            }
            try {
                player.drawGoldFromDeck(model.getGameDrawableDeck());
            } catch (DeckEmptyException e) {
                e.printStackTrace();
            }
        }

    }

    //---------------------------------ADD CARD SECTION
    /**
     * Add card.
     *
     * @param nickname       the nickname
     * @param cardToAdd      the card to add
     * @param cardOnBoard    the card on board
     * @param cornerToAttach the corner to attach
     * @param flip           the flip
     */
    @Override
    public synchronized void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) {
        if (!getCurrentPlayer().getNickname().equals(nickname)){
            model.gameError("Not your turn");
            // listener invalid action
            return;
        }
        if (getCurrentPlayer().getHand().size()<DefaultModelValues.Default_Hand_Dimension){
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
        if(GameStatus.RUNNING==model.getGameStatus()){
            checkPoints20Points();
        }
        if(GameStatus.LAST_TURN==model.getGameStatus() && done){
            model.nextPlayer();
        }

    }

    /**
     * Add starting card.
     *
     * @param nickname the nickname
     * @param flip     the flip
     */
    @Override
    public synchronized void addStartingCard(String nickname, Boolean flip) {
        if (!getCurrentPlayer().getNickname().equals(nickname)){
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
    }

    /**
     * Choose player goal.
     *
     * @param nickname the nickname
     * @param choice   the choice
     */
    @Override
    public synchronized void choosePlayerGoal(String nickname, int choice) {
        if (!getCurrentPlayer().getNickname().equals(nickname)){
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
        if (!getCurrentPlayer().getNickname().equals(nickname)){
            // listener invalid action
            model.gameError("Not your turn");
            return;
        }
        if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN))) {
            // listener you cannot draw in this phase
            model.gameError("You cannot draw a Resource Card in this phase");
            return;
        }
        if(getCurrentPlayer().getHand().size()>=DefaultModelValues.Default_Hand_Dimension){
            // listener you cannot draw in this phase
            model.gameError("You cannot draw before a card is placed");
            return;
        }
        if (decksAreAllEmpty()) {
            model.setStatus(GameStatus.WAITING_LAST_TURN);
        }
        else {
            try {
                getCurrentPlayer().drawResourcesFromDeck(model.getGameDrawableDeck());
                model.nextPlayer();
            } catch (DeckEmptyException e) {
                // listener change deck
                model.gameError("Resource deck is empty");
            }
        }
    }

    /**
     * Draw gold from deck.
     *
     * @param nickname the nickname
     */
    @Override
    public synchronized void drawGoldFromDeck(String nickname) {
        if (!getCurrentPlayer().getNickname().equals(nickname)){
            // listener invalid action
            model.gameError("Not your turn");
            return;
        }
        if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN))) {
            // listener you cannot draw in this phase
            model.gameError("You cannot draw a Gold Card in this phase");
            return;
        }
        if(getCurrentPlayer().getHand().size()>=DefaultModelValues.Default_Hand_Dimension){
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
    }

    /**
     * Draw from board.
     *
     * @param nickname the nickname
     * @param position the position
     */
    @Override
    public synchronized void drawFromBoard(String nickname, int position) {
        if (!getCurrentPlayer().getNickname().equals(nickname)){
            // listener invalid action
            model.gameError("Not your turn");
            return;
        }
        if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN))) {
            // listener you cannot draw in this phase
            model.gameError("You cannot draw from Common Board in this phase");
            return;
        }
        if(getCurrentPlayer().getHand().size()>=DefaultModelValues.Default_Hand_Dimension){
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
        model.sendMessage(txt,nickname);
    }

    /**
     * Send private message.
     *
     * @param senderName   the sender name
     * @param recieverName the reciever name
     * @param txt          the txt
     */
    @Override
    public void sendPrivateMessage(String senderName, String recieverName, String txt) {
        model.sendPrivateMessage(senderName,recieverName,txt);
    }

    /**
     * Gets public chat log.
     *
     * @param requesterName the requester name
     * @throws RemoteException the remote exception
     */
    @Override
    public void getPublicChatLog(String requesterName) throws RemoteException {
        model.getPublicChatLog(requesterName);
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
        model.getPrivateChatLog(yourName,otherName);
    }


    /**
     * Gets game id.
     *
     * @return the game id
     */
//---------------------------------GAME ID
    @Override
    public int getGameId() {
        return model.getGameId();
    }


    /**
     * Check points 20 points.
     */
//---------------------------------CHECK END SECTION
    private synchronized void checkPoints20Points() {
        for (Player player : getAllPlayer()) {
            // ATTENZIONE: aggiornare il currentPlayer a fine turno, prima di chiamare questa funzione
            if (player.getCurrentPoints() >= DefaultModelValues.Default_LastTurn_Points) {
                model.setStatus(GameStatus.WAITING_LAST_TURN);
            }
        }
    }


    /**
     * Gets game.
     *
     * @return the game
     */
//---------------------------------GET SECTION TO DISPLAY THE PUBLIC PART
    public Game getGame() {
        return model;
    }

}
