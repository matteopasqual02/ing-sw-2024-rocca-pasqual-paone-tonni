package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Message get public chat log.
 */
public class MessageGetPublicChatLog extends ClientGenericMessage{
    /**
     * The Requester name.
     */
    private final String requesterName;

    /**
     * Instantiates a new Message get public chat log.
     *
     * @param requesterName the requester name
     */
    public MessageGetPublicChatLog(String requesterName){
        this.requesterName = requesterName;
    }
    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.getPublicChatLog(requesterName);
    }
}
