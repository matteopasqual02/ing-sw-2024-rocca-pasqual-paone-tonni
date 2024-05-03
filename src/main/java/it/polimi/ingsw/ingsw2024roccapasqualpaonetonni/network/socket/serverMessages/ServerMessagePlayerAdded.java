package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;

import java.rmi.RemoteException;

public class ServerMessagePlayerAdded extends ServerGenericMessage{
    private final String nickname;
    public ServerMessagePlayerAdded(String gameId){
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
