package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Main message join game by id.
 */
public class MainMessageJoinGameById extends ClientGenericMessage{
    /**
     * The Id to connect.
     */
    private final int idToConnect;
    /**
     * The Listener.
     */
    private final GameListener listener;

    /**
     * Instantiates a new Main message join game by id.
     *
     * @param nickname    the nickname
     * @param idToConnect the id to connect
     * @param listener    the listener
     */
    public MainMessageJoinGameById(String nickname, int idToConnect, GameListener listener){
        this.nickname = nickname;
        this.isForMainController = true;
        this.idToConnect = idToConnect;
        this.listener = listener;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.joinGameByID(nickname, idToConnect, notifier);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {

    }
}
