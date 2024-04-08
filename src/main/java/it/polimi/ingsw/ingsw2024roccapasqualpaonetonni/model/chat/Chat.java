package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private final List<Message> messagesList;
    public Chat(){
        messagesList=new ArrayList<>();
    }
    public Chat(List<Message> messages){
        this.messagesList=messages;
    }

    public void addMessage(Message m){
        messagesList.add(m);
    }
    public List<Message> getAllMessages(){
        return messagesList;
    }
}
