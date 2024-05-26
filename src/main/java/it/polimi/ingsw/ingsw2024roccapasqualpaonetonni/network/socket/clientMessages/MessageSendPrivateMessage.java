package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Message send private message.
 */
public class MessageSendPrivateMessage extends ClientGenericMessage{
    /**
     * The Reciever name.
     */
    private final String recieverName;
    /**
     * The Txt.
     */
    private final String txt;

    /**
     * Instantiates a new Message send private message.
     *
     * @param senderName   the sender name
     * @param recieverName the reciever name
     * @param txt          the txt
     */
    public MessageSendPrivateMessage(String senderName, String recieverName, String txt){
        this.isForMainController = false;
        this.nickname = senderName;
        this.recieverName = recieverName;
        this.txt = txt;
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
        return null;
    }

    /**
     * Launch message.
     *
     * @param gameControllerInterface the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.sendPrivateMessage(nickname,recieverName,txt);
    }
}
