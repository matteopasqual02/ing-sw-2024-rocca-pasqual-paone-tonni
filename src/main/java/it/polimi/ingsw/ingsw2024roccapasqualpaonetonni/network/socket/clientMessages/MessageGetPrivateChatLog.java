package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Message get private chat log.
 */
public class MessageGetPrivateChatLog extends ClientGenericMessage{
    private final String yourName;
    private final String otherName;

    /**
     * Instantiates a new Message get private chat log.
     *
     * @param yourName  the your name
     * @param otherName the other name
     */
    public MessageGetPrivateChatLog(String yourName, String otherName){
        this.yourName = yourName;
        this.otherName = otherName;
    }
    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.getPrivateChatLog(yourName,otherName);
    }
}
