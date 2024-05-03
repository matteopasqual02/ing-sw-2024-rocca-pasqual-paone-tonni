package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;

import java.rmi.RemoteException;
import java.util.List;

public class ServerMessagePrivateChatLog extends ServerGenericMessage{
    private final String yourName;
    private final String otherName;
    private final List<PrivateMessage> privateChat;
    public ServerMessagePrivateChatLog(String yourName,String otherName,List<PrivateMessage> privateChat){
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
