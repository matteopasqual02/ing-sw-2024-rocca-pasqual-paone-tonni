package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Private chat.
 */
public class PrivateChat implements Serializable {
    private final String player1;
    private final String player2;
    private final List<PrivateMessage> privateMessageList;

    /**
     * Instantiates a new Private chat.
     *
     * @param player1 the player 1
     * @param player2 the player 2
     */
    public PrivateChat(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
        privateMessageList = new ArrayList<>();

    }

    /**
     * Add private message.
     *
     * @param m the m
     */
    public void addPrivateMessage(PrivateMessage m)
    {
        privateMessageList.add(m);
    }

    /**
     * Get player 1 string.
     *
     * @return the string
     */
    public String getPlayer1(){
        return player1;
    }

    /**
     * Get player 2 string.
     *
     * @return the string
     */
    public String getPlayer2(){
        return player2;
    }

    /**
     * Get private message list list.
     *
     * @return the list
     */
    public List<PrivateMessage> getPrivateMessageList(){
        return privateMessageList;
    }
}
