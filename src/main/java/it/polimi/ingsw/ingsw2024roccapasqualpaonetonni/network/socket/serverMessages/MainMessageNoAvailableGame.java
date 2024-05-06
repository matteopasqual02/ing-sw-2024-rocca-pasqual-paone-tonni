package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class MainMessageNoAvailableGame extends ServerGenericMessage{
    public MainMessageNoAvailableGame(){}
    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        try {
            listener.noAvailableGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
