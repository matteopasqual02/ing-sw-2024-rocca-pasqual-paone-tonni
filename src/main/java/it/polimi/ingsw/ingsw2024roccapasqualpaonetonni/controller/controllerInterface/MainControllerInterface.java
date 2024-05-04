package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

public interface MainControllerInterface extends Remote{
    List<GameController> getRunningGames() throws RemoteException;
    GameControllerInterface createGameController(String nickname, int numMaxOfPlayer, GameListener listener, NotifierInterface notifier) throws RemoteException;
    GameControllerInterface joinFirstAvailableGame(String nickname, GameListener me, NotifierInterface notifier) throws RemoteException;
    GameControllerInterface joinGameByID(String nickname, int idToConnect, GameListener me, NotifierInterface notifier) throws RemoteException;
    GameControllerInterface reconnect(String nickname, int idToReconnect, GameListener me, NotifierInterface notifier) throws RemoteException;
    GameControllerInterface leaveGame(String nickname, int idToDisconnect, GameListener me) throws RemoteException;

    void clearSingleton() throws RemoteException;
}
