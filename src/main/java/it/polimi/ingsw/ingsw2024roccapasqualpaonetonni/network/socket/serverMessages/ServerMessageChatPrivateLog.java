package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;
import java.util.List;

/**
 * The type Server message chat private log.
 */
public class ServerMessageChatPrivateLog extends ServerGenericMessage{
    /**
     * Your name.
     */
    private final String yourName;
    /**
     * The Other name.
     */
    private final String otherName;
    /**
     * The Private chat.
     */
    private final List<PrivateMessage> privateChat;

    /**
     * Instantiates a new Server message chat private log.
     *
     * @param yourName    your name
     * @param otherName   the other name
     * @param privateChat the private chat
     */
    public ServerMessageChatPrivateLog(String yourName, String otherName, List<PrivateMessage> privateChat){
        this.privateChat = privateChat;
        this.yourName = yourName;
        this.otherName = otherName;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            if(listener.getNickname().equals(yourName)){
                listener.privateChatLog(otherName,privateChat);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
