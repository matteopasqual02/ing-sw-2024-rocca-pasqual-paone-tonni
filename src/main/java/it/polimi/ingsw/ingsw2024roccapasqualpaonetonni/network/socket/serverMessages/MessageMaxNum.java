package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;

public class MessageMaxNum extends ServerGenericMessage{
    int max;
    public MessageMaxNum(int m){
        max=m;
    }

    @Override
    public void launchMessage(GameListener listener) {
        listener.maxNumPlayersSet(max);
    }
}
