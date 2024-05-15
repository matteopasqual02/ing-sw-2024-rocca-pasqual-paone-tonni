package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

public class ServerMessageDrewResources extends ServerGenericMessage{
    private final Player player;
    private final DrawableDeck deck;
    public ServerMessageDrewResources(Player player,DrawableDeck deck){
        this.player=player;
        this.deck=deck;
    }

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
