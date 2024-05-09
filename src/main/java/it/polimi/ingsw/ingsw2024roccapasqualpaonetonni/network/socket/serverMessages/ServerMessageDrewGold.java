package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;

public class ServerMessageDrewGold extends ServerGenericMessage{
    private final Player player;
    public ServerMessageDrewGold( Player player){
        this.player=player;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.goldDrawn(player);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
