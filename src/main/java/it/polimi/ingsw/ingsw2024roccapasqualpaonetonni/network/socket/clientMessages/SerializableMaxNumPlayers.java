package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import java.io.Serializable;

public class SerializableMaxNumPlayers implements Serializable {
    int max;
    public SerializableMaxNumPlayers(int m){
        max=m;
    }
    public int getInt(){
        return max;
    }
}
