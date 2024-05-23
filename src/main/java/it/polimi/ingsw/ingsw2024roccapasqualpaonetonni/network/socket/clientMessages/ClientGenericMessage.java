package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * The type Client generic message.
 */
public abstract class ClientGenericMessage implements Serializable {
    /**
     * The Nickname.
     */
    protected String nickname;
    /**
     * The Is for main controller.
     */
    protected boolean isForMainController;

    /**
     * Launch message game controller interface.
     *
     * @param mainControllerInterface the main controller interface
     * @param notifier                the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    public abstract GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException;

    /**
     * Launch message.
     *
     * @param gameControllerInterface the game controller interface
     * @throws RemoteException the remote exception
     */
    public abstract void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException;


    /**
     * Is for main controller boolean.
     *
     * @return the boolean
     */
    public boolean isForMainController() {
        return isForMainController;
    }

    /**
     * Get nickname string.
     *
     * @return the string
     */
    public String getNickname(){
        return nickname;
    }
}
