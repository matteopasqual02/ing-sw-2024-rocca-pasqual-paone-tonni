package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrivateChat implements Serializable {
    private final String player1;
    private final String player2;
    private final List<PrivateMessage> privateMessageList;

    public PrivateChat(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
        privateMessageList = new ArrayList<>();

    }
    public void addPrivateMessage(PrivateMessage m)
    {
        privateMessageList.add(m);
    }
    public String getPlayer1(){
        return player1;
    }
    public String getPlayer2(){
        return player2;
    }
    public List<PrivateMessage> getPrivateMessageList(){
        return privateMessageList;
    }
}
