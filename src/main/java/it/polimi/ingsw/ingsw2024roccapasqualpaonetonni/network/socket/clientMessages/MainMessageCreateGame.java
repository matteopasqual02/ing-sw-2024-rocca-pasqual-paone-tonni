package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

public class MainMessageCreateGame extends ClientGenericMessage{
    private final int numberOfPlayers;
    private final GameListener listener;

    public MainMessageCreateGame(String nickname, int numberOfPlayers, GameListener listener){
        this.nickname = nickname;
        this.isForMainController = true;
        this.numberOfPlayers = numberOfPlayers;
        this.listener = listener;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.createGameController(nickname, numberOfPlayers, listener, notifier);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {
    }

}
