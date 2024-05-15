package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Main message create game.
 */
public class MainMessageCreateGame extends ClientGenericMessage{
    private final int numberOfPlayers;

    /**
     * Instantiates a new Main message create game.
     *
     * @param nickname        the nickname
     * @param numberOfPlayers the number of players
     */
    public MainMessageCreateGame(String nickname, int numberOfPlayers){
        this.nickname = nickname;
        this.isForMainController = true;
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.createGameController(nickname, numberOfPlayers, notifier);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {
    }

}
