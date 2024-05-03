package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;

public class ServerMessageInvalidPlace extends ServerGenericMessage{
    private final Player player;
    public ServerMessageInvalidPlace( Player player){

        this.player=player;
    }

    @Override
    public void launchMessage(GameListener listener) {
        listener.choseInvalidPlace(player);
    }
}
