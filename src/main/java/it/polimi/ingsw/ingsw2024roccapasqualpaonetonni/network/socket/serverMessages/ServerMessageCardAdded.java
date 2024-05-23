package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

/**
 * The type Server message card added.
 */
public class ServerMessageCardAdded extends ServerGenericMessage{
    /**
     * The Player.
     */
    private final Player player;

    /**
     * Instantiates a new Server message card added.
     *
     * @param player the player
     */
    public ServerMessageCardAdded( Player player){
        this.player=player;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.cardAdded(player);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
