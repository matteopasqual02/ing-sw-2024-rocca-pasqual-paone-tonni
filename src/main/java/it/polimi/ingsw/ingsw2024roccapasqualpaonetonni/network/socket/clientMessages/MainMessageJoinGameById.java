package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.NotifierInterface;

import java.rmi.RemoteException;

public class MainMessageJoinGameById extends ClientGenericMessage{
    int idToConnect;

    public MainMessageJoinGameById(String nickname, int idToConnect){
        this.nickname = nickname;
        this.isForMainController = true;
        this.idToConnect = idToConnect;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.joinGameByID(nickname,idToConnect);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {

    }
}
