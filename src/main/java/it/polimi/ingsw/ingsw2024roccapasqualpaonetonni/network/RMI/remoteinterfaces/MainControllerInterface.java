package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
public interface MainControllerInterface extends Remote{
    List<GameController> getRunningGames() ;
    GameControllerInterface createGameController(String nickname, int numMaxOfPlayer)throws RemoteException;
    GameControllerInterface joinFirstAvailableGame(String nickname) throws RemoteException;
    GameControllerInterface reconnect(String nickname, int idToReconnect) throws RemoteException;
    GameControllerInterface leaveGame(String nickname, int idToDisconnect) throws RemoteException;
    public void clearSingleton();
}
