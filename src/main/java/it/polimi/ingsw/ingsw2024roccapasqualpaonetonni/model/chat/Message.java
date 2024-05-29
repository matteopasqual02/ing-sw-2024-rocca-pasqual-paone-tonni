package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.time.LocalTime;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Message.
 */
public class Message implements Serializable {
    /**
     * The Text.
     */
    private String text;
    /**
     * The Sender.
     */
    private String sender;
    /**
     * The Local time.
     */
    private LocalTime localTime;

    /**
     * Instantiates a new Message.
     */
    public Message(){
        text=null;
        localTime=null;
        sender=null;
    }

    /**
     * Instantiates a new Message.
     *
     * @param txt    the txt
     * @param sender the sender
     */
    public Message(String txt, String sender){
        this.text=txt;
        this.sender=sender;
        localTime= java.time.LocalTime.now();
    }

    /**
     * Get text string.
     *
     * @return the string
     */
    public String getText(){return text;}

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {this.text = text;}

    /**
     * Get sender string.
     *
     * @return the string
     */
    public String getSender(){return sender;}

    /**
     * Sets sender.
     *
     * @param sender the sender
     */
    public void setSender(String sender) { this.sender = sender; }

    /**
     * Get time local time.
     *
     * @return the local time
     */
    public LocalTime getTime(){return localTime;}

    /**
     * Set local time.
     *
     * @param localTime the local time
     */
    public void setLocalTime(LocalTime localTime){this.localTime=localTime;}

    public String toStringTUI() {
        return String.valueOf(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.DEFAULT).
                a("[").a(localTime.getHour()).a(":")
                .a(localTime.getMinute()).a(":")
                .a(localTime.getSecond()).a(" - ")
                .a(sender).a(" ] ")
                .fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(text).a("\n"));
    }

    public String toStringGUI() {
        return "[" + localTime.getHour() + ":" + localTime.getMinute() + ":" +
                localTime.getSecond() + " - " + sender + "] " + text + "\n";
    }

}
