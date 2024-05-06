package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;

import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {
    private String text;
    private String sender;
    private LocalTime localTime;

    public Message(){
        text=null;
        localTime=null;
        sender=null;
    }
    public Message(String txt, String sender){
        this.text=txt;
        this.sender=sender;
        localTime= java.time.LocalTime.now();
    }

    public String getText(){return text;}
    public void setText(String text) {this.text = text;}
    public String getSender(){return sender;}
    public void setSender(String sender) { this.sender = sender; }
    public LocalTime getTime(){return localTime;}
    public void setLocalTime(LocalTime localTime){this.localTime=localTime;}

}
