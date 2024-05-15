package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message error.
 */
public class ServerMessageError extends ServerGenericMessage{
    /**
     * The String.
     */
    String string;

    /**
     * Instantiates a new Server message error.
     *
     * @param string the string
     */
    public ServerMessageError(String string){
        this.string=string;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.genericError(string);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
