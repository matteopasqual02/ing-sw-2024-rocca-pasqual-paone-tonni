package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;

import java.rmi.RemoteException;

public class ServerMessageChatNewPrivateMessage extends ServerGenericMessage{
    private PrivateMessage message;
    public ServerMessageChatNewPrivateMessage(PrivateMessage message){
        this.message = message;
    }
    @Override
    public void launchMessage(GameListener listener) {
        try {
            if(listener.getNickname().equals(message.getReceiver())){
                listener.newPrivateMessage(message);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
