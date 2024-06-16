package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The interface Game controller interface.
 */
public interface GameControllerInterface extends Remote {

    //---------------------------------LISTENERS SECTION

    /**
     * Add myself as listener.
     *
     * @param me       my nickname
     * @param notifier the notifier
     * @throws RemoteException the remote exception
     */
    void addMyselfAsListener(String me, NotifierInterface notifier) throws RemoteException;

    /**
     * Remove myself as listener.
     *
     * @param me my nickname
     * @throws RemoteException the remote exception
     */
    void removeMyselfAsListener(String me) throws RemoteException;

    /**
     * Add player.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
//---------------------------------GAME CREATION SECTION
    void addPlayer(String nickname) throws RemoteException;

    /**
     * Sets max number of player.
     *
     * @param num the num
     * @throws RemoteException the remote exception
     */
    void setMaxNumberOfPlayer(int num) throws RemoteException;

    /**
     * player Ready.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void ready(String nickname) throws RemoteException;

    //---------------------------------TABLE AND INIT SECTION

    /**
     * Add card.
     *
     * @param nickname       the nickname
     * @param cardToAdd      the card to add
     * @param cardOnBoard    the card on board
     * @param cornerToAttach the corner to attach
     * @param flip           the flip
     * @throws RemoteException the remote exception
     */
    void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws RemoteException;

    /**
     * Add starting card.
     *
     * @param nickname the nickname
     * @param flip     the flip
     * @throws RemoteException the remote exception
     */
    void addStartingCard(String nickname, Boolean flip) throws RemoteException;

    /**
     * Choose player goal.
     *
     * @param nickname the nickname
     * @param choice   the choice
     * @throws RemoteException the remote exception
     */
    void choosePlayerGoal(String nickname, int choice) throws RemoteException;

    /**
     * Draw resource from deck.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void drawResourceFromDeck(String nickname) throws RemoteException;

    /**
     * Draw gold from deck.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void drawGoldFromDeck(String nickname) throws RemoteException;

    /**
     * Draw from board.
     *
     * @param nickname the nickname
     * @param position the position
     * @throws RemoteException the remote exception
     */
    void drawFromBoard(String nickname, int position) throws RemoteException;

    /**
     * Send message.
     *
     * @param txt      the txt
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    void sendMessage(String txt, String nickname) throws RemoteException;

    /**
     * Send private message.
     *
     * @param senderName   the sender name
     * @param recieverName the reciever name
     * @param txt          the txt
     * @throws RemoteException the remote exception
     */
    void sendPrivateMessage(String senderName, String recieverName, String txt) throws RemoteException;

    /**
     * Gets public chat log.
     *
     * @param requesterName the requester name
     * @throws RemoteException the remote exception
     */
    void getPublicChatLog(String requesterName) throws RemoteException;

    /**
     * Gets private chat log.
     *
     * @param yourName  your name
     * @param otherName the other name
     * @throws RemoteException the remote exception
     */
    void getPrivateChatLog(String yourName, String otherName) throws RemoteException;

    /**
     *
     * @param myName nickname of the caller
     * @param otherName nickname of the owner of the board to show
     * @throws RemoteException the remote exception
     */
    void getOtherBoard(String myName, String otherName) throws RemoteException;

    /**
     * Gets game id.
     *
     * @return the game id
     * @throws RemoteException the remote exception
     */
    int getGameId() throws RemoteException;

    /**
     * Pong.
     *
     * @param client the client
     * @throws RemoteException the remote exception
     */
    void pong(String client) throws RemoteException;

    /**
     * Add to ping pong.
     *
     * @param client the client
     * @throws RemoteException the remote exception
     */
    void addToPingPong(String client) throws RemoteException;

}
