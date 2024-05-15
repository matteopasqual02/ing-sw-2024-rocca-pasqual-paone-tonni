package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.time.LocalTime;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Private message.
 */
public class PrivateMessage implements Serializable {
    private String text;
    private String sender;
    private String receiver;
    private LocalTime localTime;

    /**
     * Instantiates a new Private message.
     */
    public PrivateMessage() {
        text=null;
        localTime=null;
        sender=null;
        receiver = null;
    }

    /**
     * Instantiates a new Private message.
     *
     * @param txt      the txt
     * @param sender   the sender
     * @param receiver the receiver
     */
    public PrivateMessage(String txt, String sender, String receiver){
        this.text=txt;
        this.sender=sender;
        this.receiver = receiver;
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
     * Get receiver string.
     *
     * @return the string
     */
    public String getReceiver(){return receiver;}

    /**
     * Sets sender.
     *
     * @param sender the sender
     */
    public void setSender(String sender) { this.sender = sender; }

    /**
     * Set receiver.
     *
     * @param receiver the receiver
     */
    public void setReceiver(String receiver){this.receiver =receiver;}

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

    @Override
    public String toString() {
        return String.valueOf(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.DEFAULT).
                a("[").a(localTime.getHour()).a(":")
                .a(localTime.getMinute()).a(":")
                .a(localTime.getSecond()).a(" - ")
                .a(sender).a(" - PRIVATE] ")
                .fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(text).a("\n"));
    }
}
