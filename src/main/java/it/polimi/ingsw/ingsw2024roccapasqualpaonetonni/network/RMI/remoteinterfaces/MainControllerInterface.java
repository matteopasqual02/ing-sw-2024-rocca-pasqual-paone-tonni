package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
public interface MainControllerInterface extends Remote{
    GameControllerInterface createGameController(String nickname);
    GameControllerInterface joinFirstAvailableGame(String nickname);
    GameControllerInterface reconnect(String nickname, int idToReconnect);
    GameControllerInterface leaveGame(String nickname, int idToDisconnect);

}
