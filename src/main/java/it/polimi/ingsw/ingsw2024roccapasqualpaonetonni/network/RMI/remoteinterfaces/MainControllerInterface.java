package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
public interface MainControllerInterface extends Remote{
    public GameControllerInterface CreateGameController(String nickname);
    public GameControllerInterface joinFirstAvailableGame(String nickname);
    public GameControllerInterface reconnect(String nickname);
    public GameControllerInterface leaveGame(String nickname);

}
