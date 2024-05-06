package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageCreatedGame extends ServerGenericMessage{
    private final int gameId;
    public ServerMessageCreatedGame(int gameId){
        this.gameId=gameId;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.createdGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
