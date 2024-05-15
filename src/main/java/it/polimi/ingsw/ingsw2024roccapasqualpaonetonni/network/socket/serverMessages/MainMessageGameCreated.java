package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;


import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Main message game created.
 */
public class MainMessageGameCreated extends ServerGenericMessage {
    /**
     * The Game id.
     */
    int gameId;

    /**
     * Instantiates a new Main message game created.
     *
     * @param gameId the game id
     */
    public MainMessageGameCreated(int gameId) {
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
            listener.createdGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
