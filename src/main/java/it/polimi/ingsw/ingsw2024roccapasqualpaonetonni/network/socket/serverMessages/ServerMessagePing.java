package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message ping.
 */
public class ServerMessagePing extends ServerGenericMessage{

    /**
     * Launch message.
     *
     * @param listener the listener
     * @throws RemoteException the remote exception
     */
    public void launchMessage(GameListener listener) throws RemoteException {
        try {
            listener.ping();
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
