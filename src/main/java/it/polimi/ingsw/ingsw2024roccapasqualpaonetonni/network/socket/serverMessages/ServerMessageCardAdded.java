package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;

public class ServerMessageCardAdded extends ServerGenericMessage{
    private final PlayerBoard playerBoard;
    private final Player player;
    public ServerMessageCardAdded(PlayerBoard playerBoard, Player player){
        this.playerBoard=playerBoard;
        this.player=player;
    }

    @Override
    public void launchMessage(GameListener listener) {
        listener.cardAdded(playerBoard,player);
    }
}
