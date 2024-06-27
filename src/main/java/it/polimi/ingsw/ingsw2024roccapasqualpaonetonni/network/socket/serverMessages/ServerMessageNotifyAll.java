package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message notify all.
 */
public class ServerMessageNotifyAll extends ServerGenericMessage{
    /**
     * The Game immutable.
     */
    private final GameImmutable gameImmutable;
    private final Boolean afterReconnection;

    /**
     * Instantiates a new Server message notify all.
     *
     * @param gameImmutable the game immutable
     */
    public ServerMessageNotifyAll(GameImmutable gameImmutable, Boolean afterReconnection){
        this.gameImmutable=gameImmutable;
        this.afterReconnection = afterReconnection;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     * @throws RemoteException the remote exception
     */
    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        try {
            listener.allGame(gameImmutable,afterReconnection);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
