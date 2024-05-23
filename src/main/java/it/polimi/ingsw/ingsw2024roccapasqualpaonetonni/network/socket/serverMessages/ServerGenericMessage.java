package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * The type Server generic message.
 */
public abstract class ServerGenericMessage implements Serializable {

    /**
     * Launch message.
     *
     * @param listener the listener
     * @throws RemoteException the remote exception
     */
    public abstract void launchMessage(GameListener listener) throws RemoteException;

}
