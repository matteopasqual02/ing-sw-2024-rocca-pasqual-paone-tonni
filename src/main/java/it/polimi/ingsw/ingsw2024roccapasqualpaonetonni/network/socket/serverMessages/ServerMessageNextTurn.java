package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageNextTurn extends ServerGenericMessage{
    private final String nickname;
    public ServerMessageNextTurn(String nickname){
        this.nickname = nickname;
    }
    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        try {
            listener.nextTurn(nickname);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
