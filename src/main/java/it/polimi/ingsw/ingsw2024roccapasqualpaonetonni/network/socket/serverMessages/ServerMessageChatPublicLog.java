package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;

import java.rmi.RemoteException;
import java.util.List;

public class ServerMessageChatPublicLog extends ServerGenericMessage{
    private String requesterName;
    private List<Message> allMessages;
    public ServerMessageChatPublicLog(String requesterName, List<Message> allMessages){
        this.allMessages = allMessages;
        this.requesterName = requesterName;
    }
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
