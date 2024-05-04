package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;

import java.rmi.RemoteException;

public class MainMessageNewPlayerJoined extends ServerGenericMessage {
    String name;

    public MainMessageNewPlayerJoined(String name) {
        this.name = name;
    }

    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.addedNewPlayer(name);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
