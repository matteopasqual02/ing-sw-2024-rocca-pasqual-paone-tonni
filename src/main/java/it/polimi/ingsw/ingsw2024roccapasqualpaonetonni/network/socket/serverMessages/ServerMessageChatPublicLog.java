package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;
import java.util.List;

/**
 * The type Server message chat public log.
 */
public class ServerMessageChatPublicLog extends ServerGenericMessage{
    /**
     * The Requester name.
     */
    private final String requesterName;
    /**
     * All messages.
     */
    private final List<Message> allMessages;

    /**
     * Instantiates a new Server message chat public log.
     *
     * @param requesterName the requester name
     * @param allMessages   the all messages
     */
    public ServerMessageChatPublicLog(String requesterName, List<Message> allMessages){
        this.allMessages = allMessages;
        this.requesterName = requesterName;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            if(listener.getNickname().equals(requesterName)){
                listener.publicChatLog(allMessages);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
