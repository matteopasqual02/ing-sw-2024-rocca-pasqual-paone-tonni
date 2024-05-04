package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private final List<Message> messagesList;
    private List<PrivateChat> privateChats;
    private Boolean found;
    public Chat(){
        found = Boolean.FALSE;
        privateChats = new ArrayList<>();
        messagesList=new ArrayList<>();
    }
    public Chat(List<Message> messages){
        this.messagesList=messages;
    }

    public void addMessage(Message m){
        messagesList.add(m);
    }
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
    public List<Message> getAllMessages(){
        return messagesList;
    }
    public List<PrivateMessage> getPrivateChat(String senderName, String receiverName) {
        for (PrivateChat chat : privateChats) {
            if ((chat.getPlayer1().equals(senderName) && chat.getPlayer2().equals(receiverName)) || (chat.getPlayer1().equals(receiverName) && chat.getPlayer2().equals(senderName))) {
                return chat.getPrivateMessageList();
            }
        }
        return null;
    }
}
