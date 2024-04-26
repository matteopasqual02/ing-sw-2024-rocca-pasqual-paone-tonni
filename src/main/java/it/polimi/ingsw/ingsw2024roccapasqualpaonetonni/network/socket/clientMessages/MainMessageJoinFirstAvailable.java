package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;

import java.rmi.RemoteException;

public class MainMessageJoinFirstAvailable extends ClientGenericMessage{
    public MainMessageJoinFirstAvailable(String nickname){
        this.nickname= nickname;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface) throws RemoteException {
        return mainControllerInterface.joinFirstAvailableGame(nickname);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {
    }
}
