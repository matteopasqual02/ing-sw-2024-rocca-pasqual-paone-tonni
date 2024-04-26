package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;

import java.rmi.RemoteException;

public class MainMessageCreateGame extends ClientGenericMessage{
    private final int numberOfPlayers;
    public MainMessageCreateGame(String nickname, int numberOfPlayers){
        this.nickname = nickname;
        this.isForMainController = true;
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface) throws RemoteException {
        return mainControllerInterface.createGameController(nickname,numberOfPlayers);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {
    }

}
