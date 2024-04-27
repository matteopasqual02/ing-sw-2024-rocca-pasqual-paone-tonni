package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;

import java.rmi.RemoteException;

public class MessageDrawResources extends ClientGenericMessage{

    public MessageDrawResources(){
        this.isForMainController = false;
    }


    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.drawResourceFromDeck();
    }
}
