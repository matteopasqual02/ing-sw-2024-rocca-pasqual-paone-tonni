package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;

import java.rmi.RemoteException;

public class MainMessageReconnect extends ClientGenericMessage{
    int idToConnect;

    public MainMessageReconnect(String nickname, int idToConnect){
        this.nickname = nickname;
        this.isForMainController = true;
        this.idToConnect = idToConnect;
    }

    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface) throws RemoteException {
        return mainControllerInterface.reconnect(nickname,idToConnect);
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) {

    }
}
