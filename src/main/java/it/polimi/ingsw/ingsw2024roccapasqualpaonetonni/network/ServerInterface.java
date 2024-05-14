package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;

public interface ServerInterface extends Remote{

    //--------------------------GAME CREATION PHASE
    public void createGame(String name, int maxNumPlayers, GameListener me) throws IOException, NotBoundException;
    public void joinFirstAvailable(String name, GameListener me) throws IOException, NotBoundException;
    public void joinGameByID(String name, int idGame, GameListener me) throws IOException, NotBoundException;
    public void reconnect(String name, int idGame, GameListener me) throws IOException, NotBoundException;
    public void leave(String nick, int idGame) throws IOException, NotBoundException;

    //--------------------------READY PHASE
    public void ready(String nickname) throws IOException, NotBoundException;

    //--------------------------GAME FLOW PHASE
    public void setMaxNUm(int num) throws IOException;
    public void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws IOException;
    public void addStartingCard(String nickname, Boolean flip) throws IOException;
    public void choosePlayerGoal(String nickname, int choice) throws IOException;
    public void drawResourceFromDeck(String nickname) throws IOException;
    public void drawGoldFromDeck(String nickname) throws IOException;
    public void drawFromBoard(String nickname, int position) throws IOException;

    //--------------------------CHAT
    public void sendMessage(String txt, String nickname) throws IOException;
    public void sendPrivateMessage(String txt, String nicknameSender, String nicknameReciever) throws IOException;
    public void getPublicChatLog(String requesterName) throws IOException;
    public void getPrivateChatLog(String yourName, String otherName) throws IOException;

    public void pong(String me) throws IOException, NotBoundException;
}
