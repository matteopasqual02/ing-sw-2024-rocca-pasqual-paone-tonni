package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;

import java.rmi.RemoteException;

public class MessageAddStarting extends ClientGenericMessage{
    private final Boolean flip;

    public MessageAddStarting(String nickname, Boolean flip){
        this.flip=flip;
        this.isForMainController = false;
        this.nickname = nickname;
    }


    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.addStartingCard(nickname,flip);
    }
}
