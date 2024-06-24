package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers.GenericController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageKIll extends ServerGenericMessage {
    String nickname = null;
    public ServerMessageKIll(String nickname){
        this.nickname = nickname;
    }
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.sendKill(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
