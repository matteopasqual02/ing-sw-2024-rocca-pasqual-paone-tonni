package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.BoardDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

public class ServerMessageDrewBoard extends ServerGenericMessage{
    private final Player player;
    private final BoardDeck boardDeck;
    private final DrawableDeck drawableDeck;
    public ServerMessageDrewBoard(Player player,BoardDeck boardDeck,DrawableDeck drawableDeck){
        this.player=player;
        this.boardDeck=boardDeck;
        this.drawableDeck=drawableDeck;
    }

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
