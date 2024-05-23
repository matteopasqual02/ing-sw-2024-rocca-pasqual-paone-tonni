package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;
import java.util.List;

/**
 * The type Server message winners.
 */
public class ServerMessageWinners extends ServerGenericMessage{
    /**
     * The Players.
     */
    List<Player> players;

    /**
     * Instantiates a new Server message winners.
     *
     * @param players the players
     */
    public ServerMessageWinners(List<Player> players){
        this.players=players;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.winners(players);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
