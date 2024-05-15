package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageStartAdded extends ServerGenericMessage{
    private final Player player;
    public ServerMessageStartAdded(Player player){
        this.player=player;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.startAdded(player);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
