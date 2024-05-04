package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;

import java.rmi.RemoteException;

public class ServerMessageCardInvalidPlace extends ServerGenericMessage{
    private final Player player;
    public ServerMessageCardInvalidPlace(Player player){

        this.player=player;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.choseInvalidPlace(player);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
