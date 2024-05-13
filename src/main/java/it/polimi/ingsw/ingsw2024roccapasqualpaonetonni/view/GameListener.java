package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/*
GameListener is an interface with all the methods that are used to communicate with the RMIClient in order to show update of changes.
The methods here are only the ones that set something's, not those who get something, because we only want to notify the client if something has actually changed
 */
public interface GameListener extends Remote {
    String getNickname() throws RemoteException;
    void allGame(GameImmutable gameImmutable) throws RemoteException;

    //--------------------------GAME
    void maxNumPlayersSet(int max) throws RemoteException;
    void createdGame(int gameId) throws RemoteException;
    void youJoinedGame(int gameId) throws RemoteException;
    void addedNewPlayer(String pNickname) throws RemoteException;
    void noAvailableGame() throws RemoteException;
    void fullGame() throws RemoteException;
    void nameAlreadyInGame() throws RemoteException;
    void areYouReady() throws RemoteException;
    void playerRemoved(String p) throws RemoteException;
    void nextTurn(String nickname) throws RemoteException;
    void lastTurn() throws RemoteException;
    void reconnectedPlayer(String nickname) throws RemoteException;
    void reconnectionImpossible(String nickname) throws RemoteException;
    void disconnectedPlayer(String nickname) throws RemoteException;
    void statusSet(GameStatus status) throws RemoteException;
    void statusSetToLastStatus(GameStatus status) throws RemoteException;
    void lastStatusReset() throws RemoteException;

    //--------------------------CHAT
    void newMessage(Message message) throws RemoteException;
    void newPrivateMessage(PrivateMessage message) throws RemoteException;
    void publicChatLog(List<Message> allMessages) throws RemoteException;
    void privateChatLog(String otherName, List<PrivateMessage> privateChat) throws RemoteException;

    //--------------------------PLAYER
    void startAdded(Player p) throws RemoteException;
    void cardAdded(Player p) throws RemoteException;
    void personalGoalChosen( Player p) throws RemoteException;
    void resourceDrawn(Player p, DrawableDeck d) throws RemoteException;
    void goldDrawn(Player p, DrawableDeck d) throws RemoteException;
    void drewFromBoard(Player p, BoardDeck b, DrawableDeck d) throws RemoteException;

    //--------------------------PING
    void ping() throws RemoteException;

    //--------------------------ERROR
    void genericError(String s) throws RemoteException;
}
