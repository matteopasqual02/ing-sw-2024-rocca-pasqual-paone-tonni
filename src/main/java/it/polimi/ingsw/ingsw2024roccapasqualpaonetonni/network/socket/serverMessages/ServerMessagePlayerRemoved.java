package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message player removed.
 */
public class ServerMessagePlayerRemoved extends ServerGenericMessage{
    /**
     * The Nickname.
     */
    private final String nickname;

    /**
     * Instantiates a new Server message player removed.
     *
     * @param nickname the nickname
     */
    public ServerMessagePlayerRemoved(String nickname){
        this.nickname=nickname;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.playerRemoved(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
