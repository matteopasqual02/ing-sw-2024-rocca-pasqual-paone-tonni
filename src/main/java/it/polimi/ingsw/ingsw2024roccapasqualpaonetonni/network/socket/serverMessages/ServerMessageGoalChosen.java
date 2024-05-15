package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageGoalChosen extends ServerGenericMessage {
    private final Player p;

    public ServerMessageGoalChosen(Player p) {
        this.p =p;
    }

    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        listener.personalGoalChosen(p);
    }
}
