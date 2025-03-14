package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Main message no available game.
 */
public class MainMessageNoAvailableGame extends ServerGenericMessage{
    /**
     * Instantiates a new Main message no available game.
     */
    public MainMessageNoAvailableGame(){}

    /**
     * Launch message.
     *
     * @param listener the listener
     * @throws RemoteException the remote exception
     */
    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        try {
            listener.noAvailableGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
