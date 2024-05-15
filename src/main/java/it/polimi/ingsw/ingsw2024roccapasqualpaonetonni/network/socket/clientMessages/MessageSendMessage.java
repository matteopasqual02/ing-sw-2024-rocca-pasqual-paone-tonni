package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Message send message.
 */
public class MessageSendMessage extends ClientGenericMessage{
    /**
     * The Txt.
     */
    private String txt;

    /**
     * Instantiates a new Message send message.
     *
     * @param txt      the txt
     * @param nickname the nickname
     */
    public MessageSendMessage(String txt, String nickname){
        this.isForMainController = false;
        this.nickname = nickname;
        this.txt = txt;
    }
    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.sendMessage(txt,nickname);
    }
}
