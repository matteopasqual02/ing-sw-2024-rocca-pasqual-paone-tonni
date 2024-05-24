package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Main message reconnect.
 */
public class MainMessageReconnect extends ClientGenericMessage{
    /**
     * The ID to connect.
     */
    private final int idToConnect;

    /**
     * Instantiates a new Main message reconnect.
     *
     * @param nickname    the nickname
     * @param idToConnect the id to connect
     */
    public MainMessageReconnect(String nickname, int idToConnect){
        this.nickname = nickname;
        this.isForMainController = true;
        this.idToConnect = idToConnect;
    }

    /**
     * Launch message game controller interface.
     *
     * @param mainControllerInterface the main controller interface
     * @param notifier                the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.reconnect(nickname, idToConnect, notifier);
    }

    /**
     * Launch message.
     *
     * @param gameControllerInterface the game controller interface
     */
    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {

    }
}
