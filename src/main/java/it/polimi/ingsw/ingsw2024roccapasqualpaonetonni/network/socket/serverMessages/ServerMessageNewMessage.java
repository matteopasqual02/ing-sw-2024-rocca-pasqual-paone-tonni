package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;

import java.rmi.RemoteException;

public class ServerMessageNewMessage extends ServerGenericMessage{
    private String nickname;
    private String txt;
    public ServerMessageNewMessage(String txt, String nickname){
        this.nickname = nickname;
        this.txt = txt;
    }
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.newMessage(nickname,txt);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
