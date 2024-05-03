package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;

public class ServerMessageDrewResources extends ServerGenericMessage{
    private final PlayingCard playingCard;
    private final Player player;
    public ServerMessageDrewResources(PlayingCard playingCard, Player player){
        this.playingCard=playingCard;
        this.player=player;
    }

    @Override
    public void launchMessage(GameListener listener) {
        listener.resourceDrawn(playingCard,player);
    }
}
