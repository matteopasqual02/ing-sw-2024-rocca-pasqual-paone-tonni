package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class MainMessageAreYouReady extends ServerGenericMessage {
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.areYouReady();
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
