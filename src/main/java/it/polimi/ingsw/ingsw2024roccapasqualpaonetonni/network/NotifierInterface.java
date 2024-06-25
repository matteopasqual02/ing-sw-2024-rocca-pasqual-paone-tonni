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

/**
 * The interface Notifier interface.
 */
public interface NotifierInterface extends Remote {
    /**
     * Send all.
     *
     * @param gameImmutable the game immutable
     * @throws IOException     the io exception
     * @throws RemoteException the remote exception
     */
    void sendAll(GameImmutable gameImmutable)throws IOException;

    /**
     * Send max num players set.
     *
     * @param gameId the game id
     * @param max    the max
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     * @throws RemoteException        the remote exception
     */
//--------------------------GAME
    void sendMaxNumPlayersSet(int gameId,int max) throws IOException, ClassNotFoundException;

    /**
     * Send created game.
     *
     * @param gameId the game id
     * @throws IOException     the io exception
     * @throws RemoteException the remote exception
     */
    void sendCreatedGame(int gameId) throws IOException;

    /**
     * Send you joined game.
     *
     * @param gameId the game id
     * @throws IOException     the io exception
     * @throws RemoteException the remote exception
     */
    void sendYouJoinedGame(int gameId) throws IOException;

    /**
     * Send added new player.
     *
     * @param pNickname the p nickname
     * @throws IOException     the io exception
     * @throws RemoteException the remote exception
     */
    void sendAddedNewPlayer(String pNickname) throws IOException;

    /**
     * Send no available game.
     *
     * @throws RemoteException the remote exception
     * @throws IOException     the io exception
     */
    void sendNoAvailableGame() throws IOException;

    /**
     * Send ask players ready.
     *
     * @throws RemoteException the remote exception
     * @throws IOException     the io exception
     */
    void sendAskPlayersReady() throws IOException;


    /**
     * Send next turn.
     *
     * @param nickname the nickname
     * @throws IOException the io exception
     */
    void sendNextTurn(String nickname) throws IOException;



    /**
     * Send reconnected player.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void sendReconnectedPlayer(String nickname) throws IOException;

    /**
     * Send reconnection impossible.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void sendReconnectionImpossible(String nickname) throws IOException;

    /**
     * Send disconnected player.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void sendDisconnectedPlayer(String nickname) throws IOException;

    /**
     * Send status set.
     *
     * @param status the status
     * @throws IOException the io exception
     */
    void sendStatusSet(GameStatus status) throws IOException;

    /**
     * Send status set to last status.
     *
     * @param status the status
     * @throws RemoteException the remote exception
     */
    void sendStatusSetToLastStatus(GameStatus status) throws IOException;

    /**
     * Send last status reset.
     *
     * @throws RemoteException the remote exception
     */
    void sendLastStatusReset() throws IOException;

    /**
     * Send start added.
     *
     * @param p the p
     * @throws IOException the io exception
     */
//--------------------------PLAYER
    void sendStartAdded(Player p) throws IOException;

    /**
     * Send card added.
     *
     * @param p the p
     * @throws IOException the io exception
     */
    void sendCardAdded(Player p,int cardID) throws IOException;

    /**
     * Send personal goal chosen.
     *
     * @param p the p
     * @throws IOException the io exception
     */
    void sendPersonalGoalChosen(Player p) throws IOException;

    /**
     * Send resource drawn.
     *
     * @param p the p
     * @param d the d
     * @throws IOException the io exception
     */
    void sendResourceDrawn(Player p, DrawableDeck d) throws IOException;

    /**
     * Send gold drawn.
     *
     * @param p the p
     * @param d the d
     * @throws IOException the io exception
     */
    void sendGoldDrawn(Player p, DrawableDeck d) throws IOException;

    /**
     * Send drew from board.
     *
     * @param p the p
     * @param b the b
     * @param d the d
     * @throws IOException the io exception
     */
    void sendDrewFromBoard(Player p, BoardDeck b, DrawableDeck d) throws IOException;


    /**
     * Send message.
     *
     * @param message the message
     * @throws IOException the io exception
     */
//--------------------------CHAT
    void sendMessage(Message message) throws IOException;

    /**
     * Send private message.
     *
     * @param message the message
     * @throws IOException the io exception
     */
    void sendPrivateMessage(PrivateMessage message) throws IOException;

    /**
     * Send public chat log.
     *
     * @param requesterName the requester name
     * @param allMessages   the all messages
     * @throws IOException the io exception
     */
    void sendPublicChatLog(String requesterName, List<Message> allMessages) throws IOException;

    /**
     * Send private chat log.
     *
     * @param yourName    your name
     * @param otherName   the other name
     * @param privateChat the private chat
     * @throws IOException the io exception
     */
    void sendPrivateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) throws IOException;

    /**
     * Send ping.
     *
     * @throws IOException the io exception
     */
//--------------------------PING
    void sendPing() throws IOException;

    /**
     * Generic error.
     *
     * @param s the s
     * @throws IOException the io exception
     */
//--------------------------GENERIC ERROR
    void genericError(String s)throws IOException;

    /**
     * Winners.
     *
     * @param list the list
     * @throws IOException the io exception
     */
//--------------------------END
    void winners(List<Player> list)throws IOException;

    void sendKill(String nickname) throws IOException;
}
