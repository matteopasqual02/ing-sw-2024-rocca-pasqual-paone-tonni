package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message player added.
 */
public class ServerMessagePlayerAdded extends ServerGenericMessage{
    /**
     * The Nickname.
     */
    private final String nickname;

    /**
     * Instantiates a new Server message player added.
     *
     * @param gameId the game id
     */
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
