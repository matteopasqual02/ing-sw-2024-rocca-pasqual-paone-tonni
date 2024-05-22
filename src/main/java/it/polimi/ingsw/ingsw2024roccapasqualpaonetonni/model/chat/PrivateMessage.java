package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.time.LocalTime;

import static org.fusesource.jansi.Ansi.ansi;

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

    public String toStringTUI() {
        return String.valueOf(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.DEFAULT).
                a("[").a(localTime.getHour()).a(":")
                .a(localTime.getMinute()).a(":")
                .a(localTime.getSecond()).a(" - ")
                .a(sender).a(" - PRIVATE] ")
                .fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(text));
    }

    public String toStringGUI() {
        return "[" + localTime.getHour() + ":" + localTime.getMinute() + ":" +
                localTime.getSecond() + " - " + sender + " - PRIVATE] " + text;
    }
}
