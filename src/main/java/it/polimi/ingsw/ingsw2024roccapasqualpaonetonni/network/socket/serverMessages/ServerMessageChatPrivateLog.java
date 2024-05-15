package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;
import java.util.List;

public class ServerMessageChatPrivateLog extends ServerGenericMessage{
    private final String yourName;
    private final String otherName;
    private final List<PrivateMessage> privateChat;
    public ServerMessageChatPrivateLog(String yourName, String otherName, List<PrivateMessage> privateChat){
        this.privateChat = privateChat;
        this.yourName = yourName;
        this.otherName = otherName;
    }
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
