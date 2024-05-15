package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageStatusSet extends ServerGenericMessage{
    private final GameStatus status;
    public ServerMessageStatusSet(GameStatus status){
        this.status = status;
    }
    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        try {
            listener.statusSet(status);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
