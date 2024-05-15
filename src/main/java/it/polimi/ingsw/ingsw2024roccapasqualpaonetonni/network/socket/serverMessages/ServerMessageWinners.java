package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;
import java.util.List;

public class ServerMessageWinners extends ServerGenericMessage{
    List<Player> players;
    public ServerMessageWinners(List<Player> players){
        this.players=players;
    }
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.winners(players);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
