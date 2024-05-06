package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

public class MainMessageJoinGameById extends ClientGenericMessage{
    private final int idToConnect;
    private final GameListener listener;

    public MainMessageJoinGameById(String nickname, int idToConnect, GameListener listener){
        this.nickname = nickname;
        this.isForMainController = true;
        this.idToConnect = idToConnect;
        this.listener = listener;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.joinGameByID(nickname, idToConnect, listener, notifier);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {

    }
}
