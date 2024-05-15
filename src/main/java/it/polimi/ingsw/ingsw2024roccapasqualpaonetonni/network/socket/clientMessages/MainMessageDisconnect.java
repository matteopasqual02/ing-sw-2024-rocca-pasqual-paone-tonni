package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Main message disconnect.
 */
public class MainMessageDisconnect extends ClientGenericMessage{
    /**
     * The Id to disconnect.
     */
    int idToDisconnect;
    /**
     * The Listener.
     */
    GameListener listener;

    /**
     * Instantiates a new Main message disconnect.
     *
     * @param nickname       the nickname
     * @param idToDisconnect the id to disconnect
     */
    public MainMessageDisconnect(String nickname, int idToDisconnect){
        this.nickname = nickname;
        this.isForMainController = true;
        this.idToDisconnect = idToDisconnect;
        this.listener = listener;
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
        return mainControllerInterface.leaveGame(nickname, idToDisconnect);
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
