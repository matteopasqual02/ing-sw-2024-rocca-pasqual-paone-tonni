package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Server message next turn.
 */
public class ServerMessageNextTurn extends ServerGenericMessage{
    /**
     * The Nickname.
     */
    private final String nickname;

    /**
     * Instantiates a new Server message next turn.
     *
     * @param nickname the nickname
     */
    public ServerMessageNextTurn(String nickname){
        this.nickname = nickname;
    }

    /**
     * Launch message.
     *
     * @param listener the listener
     * @throws RemoteException the remote exception
     */
    @Override
    public void launchMessage(GameListener listener) throws RemoteException {
        try {
            listener.nextTurn(nickname);
        }
        catch (RemoteException e) {
            ConsolePrinter.consolePrinter("[ERROR]: message failed");
        }
    }
}
