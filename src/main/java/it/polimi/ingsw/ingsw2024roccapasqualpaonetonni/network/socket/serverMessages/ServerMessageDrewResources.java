package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

/**
 * The type Server message drew resources.
 */
public class ServerMessageDrewResources extends ServerGenericMessage{
    /**
     * The Player.
     */
    private final Player player;
    /**
     * The Deck.
     */
    private final DrawableDeck deck;

    /**
     * Instantiates a new Server message drew resources.
     *
     * @param player the player
     * @param deck   the deck
     */
    public ServerMessageDrewResources(Player player,DrawableDeck deck){
        this.player=player;
        this.deck=deck;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.resourceDrawn(player,deck );
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
