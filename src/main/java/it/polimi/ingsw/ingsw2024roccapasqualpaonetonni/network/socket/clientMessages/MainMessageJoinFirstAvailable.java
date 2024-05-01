package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.NotifierInterface;

import java.rmi.RemoteException;

public class MainMessageJoinFirstAvailable extends ClientGenericMessage{
    public MainMessageJoinFirstAvailable(String nickname){
        this.nickname = nickname;
        this.isForMainController = true;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.joinFirstAvailableGame(nickname);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {
    }
}
