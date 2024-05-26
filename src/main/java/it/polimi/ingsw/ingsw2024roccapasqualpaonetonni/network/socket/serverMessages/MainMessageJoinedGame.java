package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Main message joined game.
 */
public class MainMessageJoinedGame extends ServerGenericMessage{
    /**
     * The Game id.
     */
    private final int gameId;

    /**
     * Instantiates a new Main message joined game.
     *
     * @param gameId the game id
     */
    public MainMessageJoinedGame(int gameId) {
        this.gameId = gameId;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.youJoinedGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
