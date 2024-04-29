package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;

import java.io.Serializable;

public class SerializableMaxNumPlayers extends ServerGenericMessage{
    int max;
    public SerializableMaxNumPlayers(int m){
        max=m;
    }
    public int getInt(){
        return max;
    }

    @Override
    public void launchMessage(GameListener listener) {

    }
}
