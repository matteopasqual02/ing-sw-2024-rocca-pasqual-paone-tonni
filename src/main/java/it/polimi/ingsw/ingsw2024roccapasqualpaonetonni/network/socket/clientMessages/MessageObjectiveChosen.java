package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Message objective chosen.
 */
public class MessageObjectiveChosen extends ClientGenericMessage{
    /**
     * The Choice.
     */
    private final int choice;

    /**
     * Instantiates a new Message objective chosen.
     *
     * @param nickname the nickname
     * @param choice   the choice
     */
    public MessageObjectiveChosen(String nickname, int choice){
        this.isForMainController = false;
        this.choice=choice;
        this.nickname = nickname;
    }


    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.choosePlayerGoal(nickname, choice);
    }
}
