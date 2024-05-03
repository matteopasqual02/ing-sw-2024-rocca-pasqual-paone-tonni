package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;

import java.rmi.RemoteException;

public class ServerMessageNewPlayer extends ServerGenericMessage{
    private final String nickname;
    public ServerMessageNewPlayer(String gameId){
        this.nickname=gameId;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.addedNewPlayer(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
