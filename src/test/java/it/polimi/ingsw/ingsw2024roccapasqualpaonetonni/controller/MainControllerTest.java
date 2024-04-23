package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
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
        mainController.clearSingleton();

        GameControllerInterface p1 = mainController.createGameController("p1",4);

        assertFalse(mainController.getRunningGames().isEmpty());
        assertEquals(1,mainController.getRunningGames().getFirst().getID());
        assertNotNull(mainController.getRunningGames().getFirst().getAllPlayer().stream().filter(p -> p.getNickname().equals("p1")));
        assertEquals(1,mainController.getRunningGames().getFirst().getAllPlayer().size());

        GameControllerInterface p2 = mainController.joinFirstAvailableGame("p2");
        GameControllerInterface p3 = mainController.joinFirstAvailableGame("p3");
        GameControllerInterface p4 = mainController.joinFirstAvailableGame("p4");

        assertEquals(4,mainController.getRunningGames().getFirst().getAllPlayer().size());

        assertEquals(p1,p2);
        assertEquals(p1,p3);
        assertEquals(p1,p4);

        assertEquals(GameStatus.PREPARATION,mainController.getRunningGames().getFirst().getGameStatus());

        mainController.getRunningGames().getFirst().createTable();
        assertEquals(GameStatus.RUNNING,mainController.getRunningGames().getFirst().getGameStatus());

    }

    @Test
    void multipleGameTest() throws RemoteException {
        MainControllerInterface mainController;
        mainController = MainController.getInstance();
        mainController.clearSingleton();

        GameControllerInterface p1 = mainController.createGameController("p1",4);
        GameControllerInterface p2= mainController.joinFirstAvailableGame("p2");
        GameControllerInterface p3= mainController.joinFirstAvailableGame("p3");
        GameControllerInterface p4= mainController.joinFirstAvailableGame("p4");

        GameControllerInterface p5 = mainController.createGameController("p5",2);
        GameControllerInterface p6 = mainController.joinFirstAvailableGame("p6");

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
        int id1 = mainController.getRunningGames().get(0).getID();
        int id2 = mainController.getRunningGames().get(1).getID();
        assertNotEquals(id1,id2);

    }

    @Test
    void leaveGameTest() throws RemoteException{
        MainControllerInterface mainController;
        mainController = MainController.getInstance();
        mainController.clearSingleton();

        mainController.createGameController("p1",4);
        mainController.joinFirstAvailableGame("p2");
        mainController.joinFirstAvailableGame("p3");
        mainController.joinFirstAvailableGame("p4");

        int idGame = mainController.getRunningGames().getFirst().getID();

        assertEquals(GameStatus.PREPARATION,mainController.getRunningGames().getFirst().getGameStatus());
        mainController.getRunningGames().getFirst().createTable();
        assertEquals(GameStatus.RUNNING,mainController.getRunningGames().getFirst().getGameStatus());

        mainController.leaveGame("p1",idGame);
        assertEquals(1,mainController.getRunningGames().getFirst().getGame().numberDisconnectedPlayers());
        assertEquals(GameStatus.RUNNING,mainController.getRunningGames().getFirst().getGameStatus());

        mainController.leaveGame("p3",idGame);
        mainController.leaveGame("p2",idGame);

        assertEquals(3,mainController.getRunningGames().getFirst().getGame().numberDisconnectedPlayers());
        assertEquals(GameStatus.WAITING_RECONNECTION,mainController.getRunningGames().getFirst().getGameStatus());
        assertEquals(GameStatus.RUNNING,mainController.getRunningGames().getFirst().getLastStatus());

        Boolean connected= mainController.getRunningGames().getFirst().getAllPlayer().stream().filter(p->p.getNickname().equals("p1")).toList().getFirst().getIsConnected();
        assertFalse(connected);
        connected= mainController.getRunningGames().getFirst().getAllPlayer().stream().filter(p->p.getNickname().equals("p2")).toList().getFirst().getIsConnected();
        assertFalse(connected);
        connected= mainController.getRunningGames().getFirst().getAllPlayer().stream().filter(p->p.getNickname().equals("p3")).toList().getFirst().getIsConnected();
        assertFalse(connected);
    }

    @Test
    void reconnectTest() throws RemoteException{
        MainControllerInterface mainController;
        mainController = MainController.getInstance();
        mainController.clearSingleton();

        mainController.createGameController("p1",4);
        int idGame = mainController.getRunningGames().getFirst().getID();
        mainController.joinFirstAvailableGame("p2");
        mainController.joinFirstAvailableGame("p3");
        mainController.joinFirstAvailableGame("p4");

        mainController.getRunningGames().getFirst().createTable();

        mainController.leaveGame("p2",idGame);
        mainController.reconnect("p2",idGame);

        assertEquals(GameStatus.RUNNING,mainController.getRunningGames().getFirst().getGameStatus());
        assertNull(mainController.getRunningGames().getFirst().getLastStatus());

    }

}