package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    @Test
    void createGameTest() throws RemoteException {
        MainControllerInterface mainController;
        mainController = MainController.getInstance();

        GameControllerInterface gameController = mainController.createGameController("p1",4);

        assertFalse(mainController.getRunningGames().isEmpty());
        assertEquals(1,mainController.getRunningGames().getFirst().getID());
        assertNotNull(mainController.getRunningGames().getFirst().getAllPlayer().stream().filter(p -> p.getNickname().equals("p1")));

        mainController.joinFirstAvailableGame("p2");
        mainController.joinFirstAvailableGame("p3");
        mainController.joinFirstAvailableGame("p4");

        assertEquals(4,mainController.getRunningGames().getFirst().getAllPlayer().size());

    }

}