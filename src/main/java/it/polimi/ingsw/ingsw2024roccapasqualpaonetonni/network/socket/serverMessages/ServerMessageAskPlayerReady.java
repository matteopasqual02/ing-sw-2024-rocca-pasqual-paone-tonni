package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageAskPlayerReady extends ServerGenericMessage{

    public ServerMessageAskPlayerReady(){
    }

    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        listener.areYouReady();
    }
}
