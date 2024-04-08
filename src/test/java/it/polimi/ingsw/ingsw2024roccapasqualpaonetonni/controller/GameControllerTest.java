package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    @Test
    void initialGameTest() {
        //controls the number of player and if they are ready to start
        GameController gameController = new GameController();

        gameController.setNumberOfPlayer(4);

        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        assertEquals(4,gameController.getAllPlayer().size());
        assertTrue(gameController.getGame().arePlayerReady());

        gameController.createTable();

        assertEquals(30,gameController.getGame().getGameDrawableDeck().getDecks().get("resources").size());
    }
}