package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message goal chosen.
 */
public class ServerMessageGoalChosen extends ServerGenericMessage {
    /**
     * The P.
     */
    private final Player p;

    /**
     * Instantiates a new Server message goal chosen.
     *
     * @param p the p
     */
    public ServerMessageGoalChosen(Player p) {
        this.p =p;
    }

    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        listener.personalGoalChosen(p);
    }
}
