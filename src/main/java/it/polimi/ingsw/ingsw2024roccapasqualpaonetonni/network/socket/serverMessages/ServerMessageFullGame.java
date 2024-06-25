package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageFullGame extends ServerGenericMessage{
    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        try{
            listener.fullGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
