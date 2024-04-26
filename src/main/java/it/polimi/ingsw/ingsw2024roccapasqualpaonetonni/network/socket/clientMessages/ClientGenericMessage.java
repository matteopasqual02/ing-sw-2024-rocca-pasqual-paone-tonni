package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public abstract class ClientGenericMessage implements Serializable {
    protected String nickname;
    protected boolean isForMainController;

    public abstract GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface) throws RemoteException;
    public abstract void launchMessage(GameControllerInterface gameControllerInterface);


    public boolean isForMainController() {
        return isForMainController;
    }
    public void setForMainController(boolean isForMainController) {
        this.isForMainController = isForMainController;
    }

    public String getNickname(){
        return nickname;
    }
}
