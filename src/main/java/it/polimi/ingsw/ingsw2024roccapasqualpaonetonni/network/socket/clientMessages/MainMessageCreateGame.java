package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Main message create game.
 */
public class MainMessageCreateGame extends ClientGenericMessage{
    /**
     * The Number of players.
     */
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

    /**
     * Launch message game controller interface.
     *
     * @param mainControllerInterface the main controller interface
     * @param notifier                the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return mainControllerInterface.createGameController(nickname, numberOfPlayers, notifier);
    }

    /**
     * Launch message.
     *
     * @param gameControllerInterface the game controller interface
     */
    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {
    }

}
