package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    /*It controls:
    the number of player and if they are ready to start
    then the creation of the table and if the player have all the needed cards */
    @Test
    void initialGameTest() throws RemoteException {
        GameController gameController = new GameController(0);

        //set the max number of player and
        gameController.setMaxNumberOfPlayer(4);

        //set the players
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        //assert all the player are well initialized
        assertEquals(4,gameController.getAllPlayer().size());
        assertEquals(4,gameController.getMaxNumberOfPlayer());

        for(Player player: gameController.getAllPlayer()){
            for(Player player1: gameController.getAllPlayer()){
                if(player.equals(player1)){
                    assertEquals(player1.getNickname(), player.getNickname());
                    assertEquals(player1.getColorPlayer(), player.getColorPlayer());
                }
                else {
                    assertNotEquals(player1.getNickname(), player.getNickname());
                    assertNotEquals(player1.getColorPlayer(), player.getColorPlayer());
                }
            }
        }

        //table creation and check if the creation returns true
        gameController.createTable();
        gameController.randomFirstPlayer();
        gameController.turnZero();
        gameController.getGame().setStatus(GameStatus.RUNNING);

        //assertion on decks
        assertEquals(30,gameController.getGame().getGameDrawableDeck().getDecks().get("resources").size());
        assertEquals(34,gameController.getGame().getGameDrawableDeck().getDecks().get("gold").size());
        assertEquals(2,gameController.getGame().getGameDrawableDeck().getDecks().get("starting").size());
        assertEquals(6,gameController.getGame().getGameDrawableDeck().getDecks().get("objective").size());

        assertNotNull(gameController.getGame().getGameBoardDeck().getCommonObjective(0));
        assertNotNull(gameController.getGame().getGameBoardDeck().getCommonObjective(1));
        assertNotNull(gameController.getGame().getGameBoardDeck().getCard(1));
        assertNotNull(gameController.getGame().getGameBoardDeck().getCard(2));
        assertNotNull(gameController.getGame().getGameBoardDeck().getCard(3));
        assertNotNull(gameController.getGame().getGameBoardDeck().getCard(4));

        //assert on player hand
        for (Player player: gameController.getAllPlayer()){
            assertNotNull(player.getStartingCard());
            assertNotNull(player.getObjectiveBeforeChoice()[0]);
            assertNotNull(player.getObjectiveBeforeChoice()[1]);

            assertEquals(3,player.getHand().size());

            for(PlayingCard playingCard: player.getHand()){
                assertNotNull(playingCard);
            }
        }

        //assert the game is set in the running state
        assertEquals(GameStatus.RUNNING,gameController.getGame().getGameStatus());

    }

    /*It controls a complete turn */
    @Test
    void turnGameTest() throws RemoteException{
        GameController gameController = new GameController(0);

        gameController.setMaxNumberOfPlayer(4);
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        gameController.createTable();
        gameController.randomFirstPlayer();
        gameController.turnZero();
        gameController.getGame().setStatus(GameStatus.RUNNING);

        String nick = gameController.getCurrentPlayer().getNickname();

        gameController.choosePlayerGoal(nick,1);
        gameController.addStartingCard(nick,false);

        Player me = gameController.getAllPlayer().stream().filter(player -> player.getNickname().equals(nick)).toList().getFirst();

        List<PlayingCard> hand = me.getHand();
        StartingCard startingCard = me.getStartingCard();
        PlayingCard cardToAdd = hand.getFirst();
        int pointsReceived = cardToAdd.getPoints();

        //series of assert
        //to check goal chosen
        ObjectiveCard goal = me.getGoal();
        ObjectiveCard goalVector = me.getObjectiveBeforeChoice()[1];
        assertEquals(goalVector,goal);

        //assert starting card
        StartingCard cardPlaced = me.getStartingCard();
        PlayingCard cardOnBoard = me.getBoard().getBoardMatrix()[5][5];
        assertEquals(cardPlaced,cardOnBoard);

        //assert correct second placing
        gameController.addCard(nick,cardToAdd,startingCard,1,false);
        gameController.drawGoldFromDeck(nick);

        //to check the correct draw
        for (Player player: gameController.getAllPlayer()){
            assertNotNull(player.getStartingCard());
            assertNotNull(player.getObjectiveBeforeChoice()[0]);
            assertNotNull(player.getObjectiveBeforeChoice()[1]);

            for(PlayingCard playingCard: player.getHand()){
                assertNotNull(playingCard);
            }
        }

    }
    /*It controls a complete turn */
    @Test
    void turnGameTestBeta() throws RemoteException{
        GameController gameController = new GameController(0);

        gameController.setMaxNumberOfPlayer(4);
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        gameController.createTable();
        gameController.randomFirstPlayer();
        gameController.turnZero();
        gameController.getGame().setStatus(GameStatus.RUNNING);

        String nick = gameController.getCurrentPlayer().getNickname();

        gameController.choosePlayerGoal(nick,1);
        gameController.addStartingCard(nick,false);

        Player me = gameController.getAllPlayer().stream().filter(player -> player.getNickname().equals(nick)).toList().getFirst();

        List<PlayingCard> hand = me.getHand();
        StartingCard startingCard = me.getStartingCard();
        PlayingCard cardToAdd = hand.getFirst();
        int pointsReceived = cardToAdd.getPoints();

        //series of assert
        //to check goal chosen
        ObjectiveCard goal = me.getGoal();
        ObjectiveCard goalVector = me.getObjectiveBeforeChoice()[1];
        assertEquals(goalVector,goal);

        //assert starting card
        StartingCard cardPlaced = me.getStartingCard();
        PlayingCard cardOnBoard = me.getBoard().getBoardMatrix()[5][5];
        assertEquals(cardPlaced,cardOnBoard);

        //assert correct second placing
        gameController.addCard(nick,cardToAdd,startingCard,1,false);
        gameController.drawFromBoard(nick,1);

        //to check the correct draw
        for (Player player: gameController.getAllPlayer()){
            assertNotNull(player.getStartingCard());
            assertNotNull(player.getObjectiveBeforeChoice()[0]);
            assertNotNull(player.getObjectiveBeforeChoice()[1]);

            for(PlayingCard playingCard: player.getHand()){
                assertNotNull(playingCard);
            }
        }

    }
    /*It controls a complete turn */
    @Test
    void turnGameTestGamma() throws RemoteException{
        GameController gameController = new GameController(0);

        gameController.setMaxNumberOfPlayer(4);
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        gameController.createTable();
        gameController.randomFirstPlayer();
        gameController.turnZero();
        gameController.getGame().setStatus(GameStatus.RUNNING);

        String nick = gameController.getCurrentPlayer().getNickname();

        gameController.choosePlayerGoal(nick,1);
        gameController.addStartingCard(nick,false);

        Player me = gameController.getAllPlayer().stream().filter(player -> player.getNickname().equals(nick)).toList().getFirst();

        List<PlayingCard> hand = me.getHand();
        StartingCard startingCard = me.getStartingCard();
        PlayingCard cardToAdd = hand.getFirst();
        int pointsReceived = cardToAdd.getPoints();

        //series of assert
        //to check goal chosen
        ObjectiveCard goal = me.getGoal();
        ObjectiveCard goalVector = me.getObjectiveBeforeChoice()[1];
        assertEquals(goalVector,goal);

        //assert starting card
        StartingCard cardPlaced = me.getStartingCard();
        PlayingCard cardOnBoard = me.getBoard().getBoardMatrix()[5][5];
        assertEquals(cardPlaced,cardOnBoard);

        //assert correct second placing
        gameController.addCard(nick,cardToAdd,startingCard,1,false);
        gameController.drawResourceFromDeck(nick);

        //to check the correct draw
        for (Player player: gameController.getAllPlayer()){
            assertNotNull(player.getStartingCard());
            assertNotNull(player.getObjectiveBeforeChoice()[0]);
            assertNotNull(player.getObjectiveBeforeChoice()[1]);

            for(PlayingCard playingCard: player.getHand()){
                assertNotNull(playingCard);
            }
        }

    }

    /*It controls that set status is correctly updated*/
    @Test
    void gameStatusTest() throws RemoteException {
        GameController gameController = new GameController(0);

        gameController.getGame().setStatus(GameStatus.PREPARATION);
        assertEquals(GameStatus.PREPARATION, gameController.getGame().getGameStatus() );
    }

    /*It controls the chat*/
    @Test
    void gameChatTest() throws RemoteException {
        GameController gameController = new GameController(0);

        gameController.setMaxNumberOfPlayer(4);
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        gameController.createTable();
        gameController.randomFirstPlayer();
        gameController.turnZero();
        gameController.getGame().setStatus(GameStatus.RUNNING);

        gameController.sendMessage("ciao a tutti", "a");
        gameController.sendPrivateMessage("b", "a","ciao a tutti");
        gameController.getPublicChatLog("a");
        gameController.getPrivateChatLog("a","b");
    }

}