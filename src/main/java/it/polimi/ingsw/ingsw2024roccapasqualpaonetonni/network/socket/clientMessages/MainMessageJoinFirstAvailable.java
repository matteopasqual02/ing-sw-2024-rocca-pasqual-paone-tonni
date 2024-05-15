package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;

public class MainMessageJoinFirstAvailable extends ClientGenericMessage {
    private final GameListener listener;

    public MainMessageJoinFirstAvailable(String nickname, GameListener listener){
        this.nickname = nickname;
        this.isForMainController = true;
        this.listener = listener;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        GameControllerInterface controller = mainControllerInterface.joinFirstAvailableGame(nickname, notifier);
        if(controller!=null){
            controller.addMyselfAsListener(nickname, notifier);
        }
        return controller;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {
    }
}
