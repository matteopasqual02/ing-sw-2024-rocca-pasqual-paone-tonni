package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMINotifier;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main.MainServer;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumConnectionType;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumViewType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;



class MainControllerTest {
    @Test
    void createGameTest() throws IOException {
        MainControllerInterface mainController;
        mainController = MainController.getInstance();
        mainController.clearSingleton();
        MainServer.test();

        GameControllerInterface p1 = mainController.createGameController("p1",4,
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));

        assertFalse(mainController.getRunningGames().isEmpty());
        assertEquals(1,mainController.getRunningGames().getFirst().getGameId());
        assertNotNull(mainController.getRunningGames().getFirst().getAllPlayer().stream().filter(p -> p.getNickname().equals("p1")));
        assertEquals(1,mainController.getRunningGames().getFirst().getAllPlayer().size());

        GameControllerInterface p2 = mainController.joinFirstAvailableGame("p2",
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));
        GameControllerInterface p3 = mainController.joinFirstAvailableGame("p3",
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));
        GameControllerInterface p4 = mainController.joinFirstAvailableGame("p4",
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));

        assertEquals(4,mainController.getRunningGames().getFirst().getAllPlayer().size());

        assertEquals(p1,p2);
        assertEquals(p1,p3);
        assertEquals(p1,p4);

        assertEquals(GameStatus.PREPARATION,mainController.getRunningGames().getFirst().getGame().getGameStatus());

        mainController.getRunningGames().getFirst().getGame().setStatus(GameStatus.RUNNING);

        assertEquals(GameStatus.RUNNING,mainController.getRunningGames().getFirst().getGame().getGameStatus());

    }


    @Test
    void multipleGameTest() throws IOException {
        MainControllerInterface mainController;
        mainController = MainController.getInstance();
        mainController.clearSingleton();


        GameControllerInterface p1 = mainController.createGameController("p1",4,
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));
        GameControllerInterface p2= mainController.joinGameByID("p2",1,
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));
        GameControllerInterface p3= mainController.joinFirstAvailableGame("p3",
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));
        GameControllerInterface p4= mainController.joinFirstAvailableGame("p4",
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));

        GameControllerInterface p5 = mainController.createGameController("p5",2,
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));
        GameControllerInterface p6 = mainController.joinFirstAvailableGame("p6",
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));

        assertEquals(2,mainController.getRunningGames().size());
        assertEquals(p1,p2);
        assertEquals(p1,p3);
        assertEquals(p1,p4);

        assertEquals(p5,p6);

        assertNotEquals(p1,p5);
        assertNotEquals(p1,p6);

        assertEquals(4,mainController.getRunningGames().get(0).getAllPlayer().size());
        assertEquals(2,mainController.getRunningGames().get(1).getAllPlayer().size());

        assertNotEquals(mainController.getRunningGames().get(0),mainController.getRunningGames().get(1));
        int id1 = mainController.getRunningGames().get(0).getGameID();
        int id2 = mainController.getRunningGames().get(1).getGameID();
        assertNotEquals(id1,id2);

    }

    @Test
    void reconnectAndLeaveTest() throws IOException {
        MainControllerInterface mainController;
        mainController = MainController.getInstance();
        mainController.clearSingleton();


        mainController.createGameController("p1",4,
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));
        mainController.joinFirstAvailableGame("p2",
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));
        mainController.joinFirstAvailableGame("p3",
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));
        mainController.joinFirstAvailableGame("p4",
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));

        int idGame = mainController.getRunningGames().getFirst().getGameID();

        assertEquals(GameStatus.PREPARATION,mainController.getRunningGames().getFirst().getGame().getGameStatus());
        mainController.getRunningGames().getFirst().getGame().setStatus(GameStatus.RUNNING);
        assertEquals(GameStatus.RUNNING,mainController.getRunningGames().getFirst().getGame().getGameStatus());

        mainController.leaveGame("p1",idGame);
        mainController.reconnect("p1",idGame,
                new RMINotifier(new Client(EnumConnectionType.RMI, EnumViewType.GUI)));

        assertEquals(GameStatus.RUNNING,mainController.getRunningGames().getFirst().getGame().getGameStatus());

        assertEquals(0,
                mainController.getRunningGames().getFirst().getGame().getPlayersDisconnected().stream()
                        .filter(player -> player.getNickname().equals("p1")).toList().size()
        );
    }

}