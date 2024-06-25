package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class ServerMessageStatusSetToLastStatus extends ServerGenericMessage{
    private final GameStatus status;

    /**
     * Instantiates a new Server message status set.
     *
     * @param status the status
     */
    public ServerMessageStatusSetToLastStatus(GameStatus status){
        this.status = status;
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
            listener.statusSetToLastStatus(status);
        }
        catch (RemoteException e) {
            ConsolePrinter.consolePrinter("[ERROR]: message failed");
        }
    }
}
