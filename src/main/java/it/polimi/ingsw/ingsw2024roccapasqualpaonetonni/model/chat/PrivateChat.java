package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrivateChat implements Serializable {
    private String player1;
    private String player2;
    private final List<PrivateMessage> privateMessageList;

    public PrivateChat(String sender, String reciever){
        this.player1 = sender;
        this.player2 = reciever;
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
