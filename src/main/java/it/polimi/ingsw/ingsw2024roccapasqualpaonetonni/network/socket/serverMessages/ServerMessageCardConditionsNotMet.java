package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;

public class ServerMessageCardConditionsNotMet extends ServerGenericMessage{
    private final Player player;
    public ServerMessageCardConditionsNotMet(Player player){

        this.player=player;
    }

    @Override
    public void launchMessage(GameListener listener) {
        listener.conditionsNotMet(player);
    }
}
