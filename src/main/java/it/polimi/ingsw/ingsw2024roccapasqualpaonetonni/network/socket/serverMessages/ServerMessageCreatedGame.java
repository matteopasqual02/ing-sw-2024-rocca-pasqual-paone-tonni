package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message created game.
 */
public class ServerMessageCreatedGame extends ServerGenericMessage{
    /**
     * The Game id.
     */
    private final int gameId;

    /**
     * Instantiates a new Server message created game.
     *
     * @param gameId the game id
     */
    public ServerMessageCreatedGame(int gameId){
        this.gameId=gameId;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.createdGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
