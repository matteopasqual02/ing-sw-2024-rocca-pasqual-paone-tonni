package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.time.LocalTime;

import static org.fusesource.jansi.Ansi.ansi;

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

    @Override
    public String toString() {
        return String.valueOf(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.DEFAULT).
                a("[").a(localTime.getHour()).a(":")
                .a(localTime.getMinute()).a(":")
                .a(localTime.getSecond()).a(" - ")
                .a(sender).a(" ] ")
                .fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(text));
    }

}
