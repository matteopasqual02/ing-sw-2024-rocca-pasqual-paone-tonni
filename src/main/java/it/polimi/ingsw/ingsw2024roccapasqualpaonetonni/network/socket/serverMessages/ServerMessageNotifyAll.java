package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageNotifyAll extends ServerGenericMessage{
    private final GameImmutable gameImmutable;
    public ServerMessageNotifyAll(GameImmutable gameImmutable){
        this.gameImmutable=gameImmutable;
    }

    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        try {
            listener.allGame(gameImmutable);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
