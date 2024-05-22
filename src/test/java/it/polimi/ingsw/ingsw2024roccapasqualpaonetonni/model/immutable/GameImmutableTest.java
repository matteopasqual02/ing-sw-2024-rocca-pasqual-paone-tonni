package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameImmutableTest {

    @Test
    void gameImmutableTest() throws RemoteException {
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

        GameImmutable gameImmutable = new GameImmutable(gameController.getGame());
        assertEquals(0,gameImmutable.getGameId());
        assertEquals(4,gameImmutable.getMaxNumberOfPlayers());
        assertNotNull(gameImmutable.getPlayers());
        assertNotNull(gameImmutable.getWinners());
        assertNotNull(gameImmutable.getStatus());
        assertNotNull(gameImmutable.getChat());
        assertNotNull(gameImmutable.getBoardDeck());
        assertNotNull(gameImmutable.getDrawableDeck());
        assertNotNull(gameImmutable.getAllPoints());

        System.out.println(gameImmutable.toString("a"));

        gameImmutable.setBoardDeck(new BoardDeck(gameController.getGame()));
        gameImmutable.setDrawableDeck(new DrawableDeck(new HashMap<>()));
        gameImmutable.refreshPlayer(new Player("a",1));
    }

}