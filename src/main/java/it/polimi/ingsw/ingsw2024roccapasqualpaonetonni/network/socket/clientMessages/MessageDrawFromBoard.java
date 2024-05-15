package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Message draw from board.
 */
public class MessageDrawFromBoard extends ClientGenericMessage{
    /**
     * The Position.
     */
    private final int position;

    /**
     * Instantiates a new Message draw from board.
     *
     * @param nickname the nickname
     * @param position the position
     */
    public MessageDrawFromBoard(String nickname, int position){
        this.isForMainController = false;
        this.position= position;
        this.nickname = nickname;
    }


    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.drawFromBoard(nickname,position);
    }
}
