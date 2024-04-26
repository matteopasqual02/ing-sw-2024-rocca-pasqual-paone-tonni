package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.DeckEmptyException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.NoCardException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.JSONUtils;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultControllerValues;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class GameController implements GameControllerInterface, Runnable, Serializable {
    private final Game model;
    private final Random random;
    private final String path;

    // attributes needed to implement the executor
    private final Queue<Runnable> methodsQueue;
    private ExecutorService executorService;

    public GameController(int id) {
        this.model = new Game(id);
        this.random = new Random();
        this.path = DefaultControllerValues.jsonPath;
        this.methodsQueue = new LinkedBlockingQueue<>();
        this.executorService = Executors.newSingleThreadExecutor();
        startExecutor();
    }

    //---------------------------------EXECUTOR SECTION
    // the executor is a thread that can be fed a queue of Runnable, such as lambda expressions, and that executes
    // them in order
    // it is used to de-synchronize the RMI calls, that now don't wait for the return at the end of the method
    // execution, but return after submitting the Runnable to the executor
    private void startExecutor() {
        executorService.submit(() -> {
            while (true) {
                try {
                    Runnable runnable = methodsQueue.poll();
                    if (runnable != null) {
                        runnable.run();
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void stopExecutor() {
        executorService.shutdown();
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //****************************************************UNKNOWN****************************************************
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @SuppressWarnings("BusyWait")
    public void run() {
        while (!Thread.interrupted()) {
            //some code here
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //---------------------------------LISTENERS SECTION
    public void addMyselfAsListener(GameListener me) {
        Runnable runnable = () -> {
            model.addListeners(me);
        };
        executorService.submit(runnable);
    }

    public void removeMyselfAsListener(GameListener me) {
        Runnable runnable = () -> {
            model.removeListener(me);
        };
        executorService.submit(runnable);
    }

    //---------------------------------PLAYER SECTION
    public int getID() {
        Runnable runnable = () -> {
            return model.getGameId();
        };
        executorService.submit(runnable);
    }

    @Override
    public void addPlayer(String nickname) {
        Runnable runnable = () -> {
            Player px;
            int player_number = model.getPlayers().size() + 1;
            px = new Player(nickname, player_number);
            try {
                model.addPlayer(px);
            } catch (GameAlreadyFullException | PlayerAlreadyInException e) {
                e.printStackTrace();
            }

            model.playerIsReadyToStart(px);
        };
        executorService.submit(runnable);
    }

    public Queue<Player> getAllPlayer() {
        return model.getPlayers();
    }

    public Player getCurrentPlayer() {
        return model.getCurrentPlayer();
    }

    @Override
    public Boolean isCurrentPlaying(Player p) {
        Runnable runnable = () -> {
            return getCurrentPlayer().equals(p);
        };
        executorService.submit(runnable);
    }

    @Override
    public void setMaxNumberOfPlayer(int num) throws RemoteException {
        Runnable runnable = () -> {
            model.setMaxNumberOfPlayer(num);
        };
        executorService.submit(runnable);
    }

    public int getMaxNumberOfPlayer() {
        return model.getMaxNumberOfPlayer();
    }

    public void nextTurn() {
        model.nextPlayer();
    }

    public void reconnectPlayer(String nickname) {
        model.reconnectPlayer(nickname);
        if (model.getMaxNumberOfPlayer() - model.numberDisconnectedPlayers() > 1) {
            model.setStatus(model.getLastStatus());
            model.resetLastStatus();
        }
    }

    public void disconnectPlayer(String nickname) {
        model.disconnectPlayer(nickname);
        if (model.getMaxNumberOfPlayer() - model.numberDisconnectedPlayers() == 1) {
            model.setLastStatus();
            model.setStatus(GameStatus.WAITING_RECONNECTION);
        }
    }

    @Override
    public void removePlayer(Player player) {
        Runnable runnable = () -> {
            model.removePlayer(player);
        };
        executorService.submit(runnable);
    }

    public GameStatus getGameStatus() {
        return model.getGameStatus();
    }

    public GameStatus getLastStatus() {
        return model.getLastStatus();
    }

    public Boolean playersAreReady() {
        return model.arePlayerReady();
    }


    //---------------------------------TABLE AND INIT SECTION
    @Override
    public Boolean createTable() {
        Runnable runnable = () -> {
            if (model.arePlayerReady()) {
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

                //random first player
                randomFirstPlayer();

                //run TurnZero
                turnZero();

                model.setStatus(GameStatus.RUNNING);
                return true;
            } else return false;
        };
        executorService.submit(runnable);
    }

    private void randomFirstPlayer() {
        int first = random.nextInt(4);

        for (int i = 0; i < first; i++) {
            model.nextPlayer();
        }

        model.setFirstPlayer(model.getCurrentPlayer());
    }

    private void turnZero() {
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
    public void addCard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) {
        if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN) || model.getGameStatus().equals(GameStatus.LAST_TURN))) {
            // listener you cannot draw in this phase
            return;
        }
        if (flip) {
            cardToAdd.flip();
        }
        getCurrentPlayer().addToBoard(cardToAdd, cardOnBoard, cornerToAttach);
        checkPoints20Points();
    }

    public void addStartingCard(Boolean flip) {
        if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN) || model.getGameStatus().equals(GameStatus.LAST_TURN))) {
            // listener you cannot draw in this phase
            return;
        }
        if (flip) {
            getCurrentPlayer().getStartingCard().flip();
        }
        getCurrentPlayer().addStarting();
    }

    public void choosePlayerGoal(int choice) {
        if (!(model.getGameStatus().equals(GameStatus.RUNNING))) {
            // listener you cannot draw in this phase
            return;
        }
        getCurrentPlayer().chooseGoal(choice);
    }


    //---------------------------------DRAW SECTION
    private boolean decksAreAllEmpty() {
        return model.getGameDrawableDeck().getDecks().get("resources").isEmpty()
                && model.getGameDrawableDeck().getDecks().get("gold").isEmpty()
                && model.getGameBoardDeck().isEmpty();
    }

    public void drawResourceFromDeck() {
        if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN))) {
            // listener you cannot draw in this phase
            return;
        }
        if (decksAreAllEmpty()) {
            model.setStatus(GameStatus.WAITING_LAST_TURN);
        } else {
            try {
                getCurrentPlayer().drawResourcesFromDeck(model.getGameDrawableDeck());
            } catch (DeckEmptyException e) {
                // listener change deck
                e.printStackTrace();
            }
        }
    }

    public void drawGoldFromDeck() {
        if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN))) {
            // listener you cannot draw in this phase
            return;
        }
        if (decksAreAllEmpty()) {
            model.setStatus(GameStatus.WAITING_LAST_TURN);

        } else {
            try {
                getCurrentPlayer().drawGoldFromDeck(model.getGameDrawableDeck());
            } catch (DeckEmptyException e) {
                // listener change deck
                e.printStackTrace();
            }
        }
    }

    public void drawFromBoard(int position) {
        if (!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN))) {
            // listener you cannot draw in this phase
            return;
        }
        if (decksAreAllEmpty()) {
            model.setStatus(GameStatus.WAITING_LAST_TURN);

        } else {
            try {
                getCurrentPlayer().drawFromBoard(position, model.getGameBoardDeck());
            } catch (NoCardException e) {
                // listener change deck
                return;
            }
        }
    }


    //---------------------------------END SECTION
    private void checkPoints20Points() {
        for (Player player : getAllPlayer()) {
            // ATTENZIONE: aggiornare il currentPlayer a fine turno, prima di chiamare questa funzione
            if (player.getCurrentPoints() >= 20) {
                model.setStatus(GameStatus.WAITING_LAST_TURN);
            }
        }
    }

    public void checkWinner() {
        //model.checkWinner();
        model.setStatus(GameStatus.ENDED);
    }

    //---------------------------------GET SECTION TO DISPLAY THE PUBLIC PART
    public Game getGame() {
        return model;
    }

    public GameImmutable getImmutableGame() {
        return new GameImmutable(model);
    }
}