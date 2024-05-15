package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message max num.
 */
public class ServerMessageMaxNum extends ServerGenericMessage{
    private final int max;

    /**
     * Instantiates a new Server message max num.
     *
     * @param m the m
     */
    public ServerMessageMaxNum(int m){
        max=m;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.maxNumPlayersSet(max);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
