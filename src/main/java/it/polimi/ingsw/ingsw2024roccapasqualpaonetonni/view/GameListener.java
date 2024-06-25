package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.BoardDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The interface Game listener.
 * GameListener is an interface with all the methods that are used to communicate with the RMIClient in order to show update of changes.
 * The methods here are only the ones that set something's, not those who get something, because we only want to notify the client if something has actually changed
 */
public interface GameListener extends Remote {
    /**
     * Gets nickname.
     *
     * @return the nickname
     * @throws RemoteException the remote exception
     */
    String getNickname() throws RemoteException;

    /**
     * All game.
     *
     * @param gameImmutable the game immutable
     * @throws RemoteException the remote exception
     */
    void allGame(GameImmutable gameImmutable) throws RemoteException;

    /**
     * Max num players set.
     *
     * @param max the max
     * @throws RemoteException the remote exception
     */
//--------------------------GAME
    void maxNumPlayersSet(int max) throws RemoteException;

    /**
     * Created game.
     *
     * @param gameId the game id
     * @throws RemoteException the remote exception
     */
    void createdGame(int gameId) throws RemoteException;

    /**
     * You joined game.
     *
     * @param gameId the game id
     * @throws RemoteException the remote exception
     */
    void youJoinedGame(int gameId) throws RemoteException;

    /**
     * Added new player.
     *
     * @param pNickname the p nickname
     * @throws RemoteException the remote exception
     */
    void addedNewPlayer(String pNickname) throws RemoteException;

    /**
     * No available game.
     *
     * @throws RemoteException the remote exception
     */
    void noAvailableGame() throws RemoteException;


    /**
     * Are you ready
     *
     * @throws RemoteException the remote exception
     */
    void areYouReady() throws RemoteException;

    /**
     * Next turn.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void nextTurn(String nickname) throws RemoteException;


    /**
     * Reconnected player.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void reconnectedPlayer(String nickname) throws RemoteException;

    /**
     * Reconnection impossible.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void reconnectionImpossible(String nickname) throws RemoteException;

    /**
     * Disconnected player.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void disconnectedPlayer(String nickname) throws RemoteException;
    void sendKill(String nickname) throws RemoteException;

    /**
     * Status set.
     *
     * @param status the status
     * @throws RemoteException the remote exception
     */
    void statusSet(GameStatus status) throws RemoteException;

    /**
     * Status set to last status.
     *
     * @param status the status
     * @throws RemoteException the remote exception
     */
    void statusSetToLastStatus(GameStatus status) throws RemoteException;

    /**
     * Last status reset.
     *
     * @throws RemoteException the remote exception
     */
    void lastStatusReset() throws RemoteException;

    /**
     * New message.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
//--------------------------CHAT
    void newMessage(Message message) throws RemoteException;

    /**
     * New private message.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
    void newPrivateMessage(PrivateMessage message) throws RemoteException;

    /**
     * Public chat log.
     *
     * @param allMessages the all messages
     * @throws RemoteException the remote exception
     */
    void publicChatLog(List<Message> allMessages) throws RemoteException;

    /**
     * Private chat log.
     *
     * @param otherName   the other name
     * @param privateChat the private chat
     * @throws RemoteException the remote exception
     */
    void privateChatLog(String otherName, List<PrivateMessage> privateChat) throws RemoteException;

    /**
     * Start added.
     *
     * @param p the p
     * @throws RemoteException the remote exception
     */
//--------------------------PLAYER
    void startAdded(Player p) throws RemoteException;

    /**
     * Card added.
     *
     * @param p the p
     * @throws RemoteException the remote exception
     */
    void cardAdded(Player p,int cardID) throws RemoteException;

    /**
     * Personal goal chosen.
     *
     * @param p the p
     * @throws RemoteException the remote exception
     */
    void personalGoalChosen( Player p) throws RemoteException;

    /**
     * Resource drawn.
     *
     * @param p the p
     * @param d the d
     * @throws RemoteException the remote exception
     */
    void resourceDrawn(Player p, DrawableDeck d) throws RemoteException;

    /**
     * Gold drawn.
     *
     * @param p the p
     * @param d the d
     * @throws RemoteException the remote exception
     */
    void goldDrawn(Player p, DrawableDeck d) throws RemoteException;

    /**
     * Drew from board.
     *
     * @param p the p
     * @param b the b
     * @param d the d
     * @throws RemoteException the remote exception
     */
    void drewFromBoard(Player p, BoardDeck b, DrawableDeck d) throws RemoteException;

    /**
     * Ping.
     *
     * @throws RemoteException the remote exception
     */
//--------------------------PING
    void ping() throws RemoteException;

    /**
     * Generic error.
     *
     * @param s the s
     * @throws RemoteException the remote exception
     */
//--------------------------ERROR
    void genericError(String s) throws RemoteException;

    /**
     * Winners.
     *
     * @param list the list
     * @throws RemoteException the remote exception
     */
    void winners(List<Player> list)throws RemoteException;
}
