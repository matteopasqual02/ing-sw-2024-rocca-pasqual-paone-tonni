package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

public class MessageSendPrivateMessage extends ClientGenericMessage{
    private String recieverName;
    private String txt;
    public MessageSendPrivateMessage(String senderName, String recieverName, String txt){
        this.isForMainController = false;
        this.nickname = senderName;
        this.recieverName = recieverName;
        this.txt = txt;
    }
    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.sendPrivateMessage(nickname,recieverName,txt);
    }
}
