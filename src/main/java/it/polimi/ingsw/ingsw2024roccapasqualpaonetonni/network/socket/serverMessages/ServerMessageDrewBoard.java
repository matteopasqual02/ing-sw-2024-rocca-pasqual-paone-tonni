package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.BoardDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

/**
 * The type Server message drew board.
 */
public class ServerMessageDrewBoard extends ServerGenericMessage{
    /**
     * The Player.
     */
    private final Player player;
    /**
     * The Board deck.
     */
    private final BoardDeck boardDeck;
    /**
     * The Drawable deck.
     */
    private final DrawableDeck drawableDeck;

    /**
     * Instantiates a new Server message drew board.
     *
     * @param player       the player
     * @param boardDeck    the board deck
     * @param drawableDeck the drawable deck
     */
    public ServerMessageDrewBoard(Player player,BoardDeck boardDeck,DrawableDeck drawableDeck){
        this.player=player;
        this.boardDeck=boardDeck;
        this.drawableDeck=drawableDeck;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     */
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.drewFromBoard(player, boardDeck, drawableDeck);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
