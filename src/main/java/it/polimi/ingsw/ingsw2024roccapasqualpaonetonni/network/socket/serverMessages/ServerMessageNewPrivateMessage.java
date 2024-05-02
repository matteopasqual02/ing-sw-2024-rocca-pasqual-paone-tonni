package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;

import java.rmi.RemoteException;

public class ServerMessageNewPrivateMessage extends ServerGenericMessage{
    private final String recieverName;
    private final String senderName;

    private final String txt;
    public ServerMessageNewPrivateMessage(String txt, String senderName, String recieverName){
        this.senderName = senderName;
        this.recieverName = recieverName;
        this.txt = txt;
    }
    @Override
    public void launchMessage(GameListener listener) {
        try {
            if(listener.getNickname().equals(recieverName)){
                listener.newPrivateMessage(senderName, recieverName,txt);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
