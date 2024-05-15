package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

public class ServerMessageDrewGold extends ServerGenericMessage{
    private final Player player;
    private final DrawableDeck deck;
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
