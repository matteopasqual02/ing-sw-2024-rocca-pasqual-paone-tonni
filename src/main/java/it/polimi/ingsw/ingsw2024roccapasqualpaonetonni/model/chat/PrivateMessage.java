package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import java.io.Serializable;
import java.time.LocalTime;

public class PrivateMessage implements Serializable {
    private String text;
    private String sender;
    private String receiver;
    private LocalTime localTime;

    public PrivateMessage() {
        text=null;
        localTime=null;
        sender=null;
        receiver = null;
    }
    public PrivateMessage(String txt, String sender, String receiver){
        this.text=txt;
        this.sender=sender;
        this.receiver = receiver;
        localTime= java.time.LocalTime.now();
    }

    public String getText(){return text;}
    public void setText(String text) {this.text = text;}
    public String getSender(){return sender;}
    public String getReceiver(){return receiver;}
    public void setSender(String sender) { this.sender = sender; }
    public void setReceiver(String receiver){this.receiver =receiver;}
    public LocalTime getTime(){return localTime;}
    public void setLocalTime(LocalTime localTime){this.localTime=localTime;}

}
