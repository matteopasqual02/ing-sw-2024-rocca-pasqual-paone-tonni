package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Chat.
 */
public class Chat implements Serializable {
    /**
     * The Messages list.
     */
    private final List<Message> messagesList;
    /**
     * The Private chats.
     */
    private List<PrivateChat> privateChats;
    /**
     * The Found.
     */
    private Boolean found;

    /**
     * Instantiates a new Chat.
     */
    public Chat(){
        found = Boolean.FALSE;
        privateChats = new ArrayList<>();
        messagesList=new ArrayList<>();
    }

    /**
     * Instantiates a new Chat.
     *
     * @param messages the messages
     */
    public Chat(List<Message> messages){
        this.messagesList=messages;
    }

    /**
     * Add message.
     *
     * @param m the m
     */
    public void addMessage(Message m){
        messagesList.add(m);
    }

    /**
     * Add private message.
     *
     * @param senderName   the sender name
     * @param receiverName the receiver name
     * @param m            the m
     */
    public void addPrivateMessage(String senderName, String receiverName, PrivateMessage m){
        for(PrivateChat chat: privateChats)
        {
            found = false;
            if((chat.getPlayer1().equals(senderName) && chat.getPlayer2().equals(receiverName)) || (chat.getPlayer1().equals(receiverName) && chat.getPlayer2().equals(senderName))){
                chat.addPrivateMessage(m);
                found = true;
            }
        }
        if(!found){
            privateChats.add(new PrivateChat(senderName,receiverName));
            privateChats.getLast().addPrivateMessage(m);
        }
    }

    /**
     * Get all messages list.
     *
     * @return the list
     */
    public List<Message> getAllMessages(){
        return messagesList;
    }

    /**
     * Gets private chat.
     *
     * @param senderName   the sender name
     * @param receiverName the receiver name
     * @return the private chat
     */
    public List<PrivateMessage> getPrivateChat(String senderName, String receiverName) {
        for (PrivateChat chat : privateChats) {
            if ((chat.getPlayer1().equals(senderName) && chat.getPlayer2().equals(receiverName)) || (chat.getPlayer1().equals(receiverName) && chat.getPlayer2().equals(senderName))) {
                return chat.getPrivateMessageList();
            }
        }
        return null;
    }
}
