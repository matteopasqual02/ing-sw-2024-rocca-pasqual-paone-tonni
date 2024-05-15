package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageError extends ServerGenericMessage{
    String string;
    public ServerMessageError(String string){
        this.string=string;
    }
    @Override
    public void launchMessage(GameListener listener) {
        try {
            listener.genericError(string);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
