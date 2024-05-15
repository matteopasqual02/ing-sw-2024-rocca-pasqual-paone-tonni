package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Main message reconnect.
 */
public class MainMessageReconnect extends ClientGenericMessage{
    private final int idToConnect;
    private final GameListener listener;

    /**
     * Instantiates a new Main message reconnect.
     *
     * @param nickname    the nickname
     * @param idToConnect the id to connect
     * @param listener    the listener
     */
    public MainMessageReconnect(String nickname, int idToConnect, GameListener listener){
        this.nickname = nickname;
        this.isForMainController = true;
        this.idToConnect = idToConnect;
        this.listener = listener;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.reconnect(nickname, idToConnect, notifier);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {

    }
}
