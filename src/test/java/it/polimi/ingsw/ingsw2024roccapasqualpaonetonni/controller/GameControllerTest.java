package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    /*It controls:
    the number of player and if they are ready to start
    then the creation of the table and if the player have all the needed cards */
    @Test
    void initialGameTest() {
        GameController gameController = new GameController();

        //set the max number of player and
        gameController.setNumberOfPlayer(4);

        //set the players
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        //assert all the player are well initialized
        assertEquals(4,gameController.getAllPlayer().size());
        assertTrue(gameController.getGame().arePlayerReady());
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
        Boolean tableCreated;
        tableCreated = gameController.createTable();
        assertTrue(tableCreated);

        //assertion on decks
        assertEquals(30,gameController.getGame().getGameDrawableDeck().getDecks().get("resources").size());
        assertEquals(34,gameController.getGame().getGameDrawableDeck().getDecks().get("gold").size());
        assertEquals(2,gameController.getGame().getGameDrawableDeck().getDecks().get("starting").size());
        assertEquals(6,gameController.getGame().getGameDrawableDeck().getDecks().get("objective").size());

        assertNotNull(gameController.getBoardDeck().getCommonObjective()[0]);
        assertNotNull(gameController.getBoardDeck().getCommonObjective()[1]);
        assertNotNull(gameController.getBoardDeck().getGoldCards()[0]);
        assertNotNull(gameController.getBoardDeck().getGoldCards()[1]);
        assertNotNull(gameController.getBoardDeck().getResourceCards()[0]);
        assertNotNull(gameController.getBoardDeck().getResourceCards()[1]);

        //assert on player hand
        for (Player player: gameController.getAllPlayer()){
            assertNotNull(player.getStartingCard());
            assertNotNull(player.getObjectiveBeforeChoice()[0]);
            assertNotNull(player.getObjectiveBeforeChoice()[1]);

            for(PlayingCard playingCard: player.getHand()){
                assertNotNull(playingCard);
            }
        }

        //assert the game is set in the running state
        assertEquals(GameStatus.RUNNING,gameController.getGameStatus());
    }

    /*It controls:
    a complete turn */
    @Test
    void turnGameTest() {
        GameController gameController = new GameController();

        gameController.setNumberOfPlayer(4);
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        gameController.createTable();

        gameController.getAllPlayer().peek().chooseGoal(1);
        gameController.addStartingCard(false);

        List<PlayingCard> hand = gameController.getAllPlayer().peek().getHand();
        StartingCard startingCard = gameController.getAllPlayer().peek().getStartingCard();
        PlayingCard cardToAdd = hand.get(0);
        gameController.addCard(cardToAdd,startingCard,1,true);
        gameController.drawGoldFromDeck();

        //series of assert
        //to check goal chosen
        ObjectiveCard goal = gameController.getAllPlayer().peek().getGoal();
        ObjectiveCard goalVector = gameController.getAllPlayer().peek().getObjectiveBeforeChoice()[1];
        assertEquals(goalVector,goal);

        //assert starting card
        StartingCard cardPlaced = gameController.getAllPlayer().peek().getStartingCard();
        PlayingCard cardOnBoard = gameController.getAllPlayer().peek().getBoard().getBoard()[20][20];
        assertEquals(cardPlaced,cardOnBoard);

        //assert correct second placing

        PlayingCard cardOnBoard2 = gameController.getAllPlayer().peek().getBoard().getBoard()[19][19];
        assertEquals(cardToAdd,cardOnBoard2);

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
}