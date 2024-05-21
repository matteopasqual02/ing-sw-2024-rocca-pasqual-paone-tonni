package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {

    @Test
    void chatInitTest() {
        Chat chat = new Chat();
        Message message = new Message("ciao a tutti","a");


        chat.addMessage(message);
        chat.addMessage(message);

        assertEquals(2, chat.getAllMessages().size());
        assertEquals("a", chat.getAllMessages().getFirst().getSender());
        assertEquals("ciao a tutti", chat.getAllMessages().getFirst().getText());
        chat.getAllMessages().forEach(System.out::println);
        assertNotNull(chat.getAllMessages().getFirst().getTime());
    }

    @Test
    void chatListTest() {

        Message message = new Message("ciao a tutti","a");
        List<Message> messageList = new ArrayList<>();
        messageList.add(message);
        Message message2 = new Message();
        message2.setSender("a");
        message2.setLocalTime(LocalTime.now());
        message2.setText("ciao a tutti");
        messageList.add(message2);
        Chat chat = new Chat(messageList);


        assertEquals(2, chat.getAllMessages().size());
        assertEquals("a", chat.getAllMessages().getFirst().getSender());
        assertEquals("ciao a tutti", chat.getAllMessages().getFirst().getText());
        chat.getAllMessages().forEach(System.out::println);
        assertNotNull(chat.getAllMessages().getFirst().getTime());
    }

    @Test
    void chatPrivateInitTest() {
        Chat chat = new Chat();
        PrivateMessage privateMessage = new PrivateMessage("ciao a tutti","me","you");

        chat.addPrivateMessage("me","you",privateMessage);
        chat.addPrivateMessage("me","you",privateMessage);

        assertEquals(2, chat.getPrivateChat("me","you").size());
        assertEquals("me", chat.getPrivateChat("me","you").getFirst().getSender());
        assertEquals("you", chat.getPrivateChat("me","you").getFirst().getReceiver());
        assertEquals("ciao a tutti",chat.getPrivateChat("me","you").getFirst().getText());
        chat.getPrivateChat("me","you").forEach(System.out::println);
        assertNotNull(chat.getPrivateChat("me", "you").getFirst().getTime());

        chat.getPrivateChat("meTee", "youTee");
    }

    @Test
    void chatPrivateInitTest2() {
        Chat chat = new Chat();
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setReceiver("you");
        privateMessage.setSender("me");
        privateMessage.setText("ciao a tutti");
        privateMessage.setLocalTime(LocalTime.now());

        chat.addPrivateMessage("me","you",privateMessage);
        chat.addPrivateMessage("me","you",privateMessage);

        assertEquals(2, chat.getPrivateChat("me","you").size());
        assertEquals("me", chat.getPrivateChat("me","you").getFirst().getSender());
        assertEquals("you", chat.getPrivateChat("me","you").getFirst().getReceiver());
        assertEquals("ciao a tutti",chat.getPrivateChat("me","you").getFirst().getText());
        chat.getPrivateChat("me","you").forEach(System.out::println);
        assertNotNull(chat.getPrivateChat("me", "you").getFirst().getTime());
    }

}