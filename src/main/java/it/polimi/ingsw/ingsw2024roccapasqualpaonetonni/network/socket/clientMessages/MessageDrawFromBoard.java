package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;

import java.rmi.RemoteException;

public class MessageDrawFromBoard extends ClientGenericMessage{
    private final int position;

    public MessageDrawFromBoard(String nickname, int position){
        this.isForMainController = false;
        this.position= position;
        this.nickname = nickname;
    }


    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.drawFromBoard(nickname,position);
    }
}
