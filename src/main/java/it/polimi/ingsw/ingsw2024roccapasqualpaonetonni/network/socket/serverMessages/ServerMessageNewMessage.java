package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message new message.
 */
public class ServerMessageNewMessage extends ServerGenericMessage{
    /**
     * The Message.
     */
    private final Message message;

    /**
     * Instantiates a new Server message new message.
     *
     * @param message the message
     */
    public ServerMessageNewMessage(Message message){
        this.message = message;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.newMessage(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
