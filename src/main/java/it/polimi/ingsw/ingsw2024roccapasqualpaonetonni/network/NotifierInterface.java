package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.BoardDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NotifierInterface extends Remote {
    void sendAll(GameImmutable gameImmutable)throws IOException, RemoteException;

    //--------------------------GAME
    void sendMaxNumPlayersSet(int gameId,int max) throws IOException, ClassNotFoundException, RemoteException;
    void sendCreatedGame(int gameId) throws IOException, RemoteException;
    void sendYouJoinedGame(int gameId) throws IOException, RemoteException;
    void sendAddedNewPlayer(String pNickname) throws IOException, RemoteException;
    void sendNoAvailableGame() throws RemoteException, IOException;
    void sendAskPlayersReady() throws RemoteException, IOException;
    void sendFullGame() throws RemoteException;
    void sendNameAlreadyInGame() throws RemoteException;
    void sendPlayerRemoved(String pNickname) throws RemoteException;
    void sendNextTurn(String nickname) throws IOException;
    void sendLastTurn() throws RemoteException;
    void sendReconnectedPlayer(String nickname) throws RemoteException;
    void sendReconnectionImpossible(String nickname) throws RemoteException;
    void sendDisconnectedPlayer(String nickname) throws RemoteException;
    void sendStatusSet(GameStatus status) throws IOException;
    void sendStatusSetToLastStatus(GameStatus status) throws RemoteException;
    void sendLastStatusReset() throws RemoteException;

    //--------------------------PLAYER
    void sendStartAdded(Player p) throws IOException;
    void sendCardAdded(Player p) throws IOException;
    void sendPersonalGoalChosen(Player p) throws IOException;
    void sendResourceDrawn(Player p, DrawableDeck d) throws IOException;
    void sendGoldDrawn(Player p, DrawableDeck d) throws IOException;
    void sendDrewFromBoard(Player p, BoardDeck b, DrawableDeck d) throws IOException;
    void sendYouWereRemoved(String pNickname) throws IOException;
    void youWereReconnected() throws RemoteException;

    //--------------------------CHAT
    void sendMessage(Message message) throws IOException;
    void sendPrivateMessage(PrivateMessage message) throws IOException;
    void sendPublicChatLog(String requesterName, List<Message> allMessages) throws IOException;
    void sendPrivateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) throws IOException;

    //--------------------------PING
    void sendPing() throws IOException;

    //--------------------------GENERIC ERROR
    void genericError(String s)throws IOException;

    //--------------------------END
    void winners(List<Player> list)throws IOException;
}
