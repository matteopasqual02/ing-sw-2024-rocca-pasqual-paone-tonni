package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.DeckEmptyException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameWinnerTest {

    @Test
    void gameWinnerTest() throws RemoteException, DeckEmptyException {
        GameController gameController = new GameController(0);

        gameController.setMaxNumberOfPlayer(2);
        gameController.addPlayer("a");
        gameController.addPlayer("b");

        gameController.createTable();
        gameController.turnZero();
        gameController.getGame().setStatus(GameStatus.RUNNING);

        String nick = gameController.getCurrentPlayer().getNickname();

        gameController.choosePlayerGoal(nick,1);
        gameController.addStartingCard(nick,false);

        Player me = gameController.getAllPlayer().stream().filter(player -> player.getNickname().equals(nick)).toList().getFirst();

        ObjectiveCard goal = me.getGoal();
        ObjectiveCard goalVector = me.getObjectiveBeforeChoice()[1];
        assertEquals(goalVector,goal);

        gameController.getGame().nextPlayer();

        String nickk = gameController.getCurrentPlayer().getNickname();

        gameController.choosePlayerGoal(nickk,1);
        gameController.addStartingCard(nickk,false);

        me = gameController.getAllPlayer().stream().filter(player -> player.getNickname().equals(nickk)).toList().getFirst();

        goal = me.getGoal();
        goalVector = me.getObjectiveBeforeChoice()[1];
        assertEquals(goalVector,goal);

        gameController.getGame().checkWinner();
        assertFalse(gameController.getGame().getWinners().isEmpty());
        assertFalse(gameController.getGame().getWinners().stream()
                .filter(player -> player.getNickname().equals("b")).toList().isEmpty()
        );

    }

}