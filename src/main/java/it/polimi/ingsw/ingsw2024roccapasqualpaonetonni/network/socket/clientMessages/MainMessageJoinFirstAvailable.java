package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

/**
 * The type Main message join first available.
 */
public class MainMessageJoinFirstAvailable extends ClientGenericMessage {

    /**
     * Instantiates a new Main message join first available.
     *
     * @param nickname the nickname
     */
    public MainMessageJoinFirstAvailable(String nickname){
        this.nickname = nickname;
        this.isForMainController = true;
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
        GameControllerInterface controller = mainControllerInterface.joinFirstAvailableGame(nickname, notifier);
        if(controller!=null){
            controller.addMyselfAsListener(nickname, notifier);
        }
        return controller;
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
