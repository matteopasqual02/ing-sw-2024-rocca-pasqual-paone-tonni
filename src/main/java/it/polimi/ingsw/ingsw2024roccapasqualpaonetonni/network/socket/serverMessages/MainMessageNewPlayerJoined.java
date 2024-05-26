package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Main message new player joined.
 */
public class MainMessageNewPlayerJoined extends ServerGenericMessage {
    /**
     * The Name.
     */
    private final String name;

    /**
     * Instantiates a new Main message new player joined.
     *
     * @param name the name
     */
    public MainMessageNewPlayerJoined(String name) {
        this.name = name;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.addedNewPlayer(name);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
