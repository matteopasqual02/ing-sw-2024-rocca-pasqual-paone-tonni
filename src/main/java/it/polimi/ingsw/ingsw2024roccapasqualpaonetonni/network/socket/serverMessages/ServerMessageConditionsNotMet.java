package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;

public class ServerMessageConditionsNotMet extends ServerGenericMessage{
    private final Player player;
    public ServerMessageConditionsNotMet(Player player){

        this.player=player;
    }

    @Override
    public void launchMessage(GameListener listener) {
        listener.conditionsNotMet(player);
    }
}
