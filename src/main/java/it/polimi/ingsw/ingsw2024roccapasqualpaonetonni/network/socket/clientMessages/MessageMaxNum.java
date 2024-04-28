package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;

import java.rmi.RemoteException;

public class MessageMaxNum extends ClientGenericMessage{
    private final int max;
    public MessageMaxNum(int num){
        this.isForMainController = false;
        this.max=num;
    }


    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.setMaxNumberOfPlayer(max);
    }
}
