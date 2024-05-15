package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

/**
 * The type Server message drew gold.
 */
public class ServerMessageDrewGold extends ServerGenericMessage{
    private final Player player;
    private final DrawableDeck deck;

    /**
     * Instantiates a new Server message drew gold.
     *
     * @param player the player
     * @param deck   the deck
     */
    public ServerMessageDrewGold( Player player, DrawableDeck deck){
        this.player=player;
        this.deck=deck;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.goldDrawn(player, deck);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
