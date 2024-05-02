package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;

import java.io.Serializable;
import java.time.LocalTime;

public class PrivateMessage implements Serializable {
    private String text;
    private String sender;
    private String reciever;
    private LocalTime localTime;

    public PrivateMessage() {
        text=null;
        localTime=null;
        sender=null;
        reciever = null;
    }
    public PrivateMessage(String txt, String sender, String reciever){
        this.text=txt;
        this.sender=sender;
        this.reciever = reciever;
        localTime= java.time.LocalTime.now();
    }

    public String getText(){return text;}
    public void setText(String text) {this.text = text;}
    public String getSender(){return sender;}
    public String getReciever(){return reciever;}
    public void setSender(String sender) { this.sender = sender; }
    public LocalTime getTime(){return localTime;}
    public void setLocalTime(LocalTime localTime){this.localTime=localTime;}

}
