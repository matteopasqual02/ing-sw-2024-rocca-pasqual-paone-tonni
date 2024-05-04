package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;

import java.rmi.RemoteException;

public class MainMessageJoinedGame extends ServerGenericMessage{
    int gameId;
    public MainMessageJoinedGame(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.youJoinedGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
