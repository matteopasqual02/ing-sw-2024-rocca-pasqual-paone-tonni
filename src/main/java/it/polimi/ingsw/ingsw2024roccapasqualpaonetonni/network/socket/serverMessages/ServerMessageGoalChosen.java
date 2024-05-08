package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageGoalChosen extends ServerGenericMessage {
    private final ObjectiveCard goal;
    private final Player p;
    private final int choice;
    public ServerMessageGoalChosen(ObjectiveCard goal, Player p, int choice) {
        this.goal=goal;
        this.p=p;
        this.choice=choice;
    }

    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        listener.personalGoalChosen(goal,p,choice);
    }
}
