package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageDisconnectedPlayer extends ServerGenericMessage{

    private final String nickname;

    public ServerMessageDisconnectedPlayer(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.disconnectedPlayer(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
