package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class MainMessageDisconnect extends ClientGenericMessage{
    int idToDisconnect;
    GameListener listener;

    public MainMessageDisconnect(String nickname, int idToDisconnect){
        this.nickname = nickname;
        this.isForMainController = true;
        this.idToDisconnect = idToDisconnect;
        this.listener = listener;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.leaveGame(nickname, idToDisconnect);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {

    }
}
