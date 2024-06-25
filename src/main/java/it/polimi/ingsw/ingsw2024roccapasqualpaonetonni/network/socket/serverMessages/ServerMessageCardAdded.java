package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

/**
 * The type Server message card added.
 */
public class ServerMessageCardAdded extends ServerGenericMessage{
    /**
     * The Player.
     */
    private final Player player;
    private final int cardID;

    /**
     * Instantiates a new Server message card added.
     *
     * @param player the player
     */
    public ServerMessageCardAdded( Player player,int cardID){
        this.player=player;
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
            listener.cardAdded(player,cardID);
        }
        catch(Exception e){
            ConsolePrinter.consolePrinter("[ERROR]: message failed");
        }
    }
}
