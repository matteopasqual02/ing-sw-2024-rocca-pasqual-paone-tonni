package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;

import java.rmi.RemoteException;

public class ServerMessageStartAdded extends ServerGenericMessage{
    private final PlayerBoard playerBoard;
    private final Player player;
    public ServerMessageStartAdded(PlayerBoard playerBoard,Player player){
        this.playerBoard=playerBoard;
        this.player=player;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.startAdded(playerBoard,player);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
