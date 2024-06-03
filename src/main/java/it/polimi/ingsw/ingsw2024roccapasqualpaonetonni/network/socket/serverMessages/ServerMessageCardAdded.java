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
    private final Double coord0;
    private final Double coord1;
    private final int cardID;

    /**
     * Instantiates a new Server message card added.
     *
     * @param player the player
     */
    public ServerMessageCardAdded( Player player, Double coord0, Double coord1,int cardID){
        this.player=player;
        this.coord0=coord0;
        this.coord1=coord1;
        this.cardID=cardID;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.cardAdded(player,coord0,coord1,cardID);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
