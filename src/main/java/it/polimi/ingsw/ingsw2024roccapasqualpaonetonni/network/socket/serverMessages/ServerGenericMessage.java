package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;

import java.io.Serializable;

public abstract class ServerGenericMessage implements Serializable {

    public abstract void launchMessage(GameListener listener);

}
