package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

public class MainMessageDisconnect extends ClientGenericMessage{
    int idToDisconnect;

    public MainMessageDisconnect(String nickname, int idToDisconnect){
        this.nickname = nickname;
        this.isForMainController = true;
        this.idToDisconnect = idToDisconnect;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.leaveGame(nickname,idToDisconnect);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {

    }
}
