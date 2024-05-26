package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;

/**
 * The interface Server interface.
 */
public interface ServerInterface extends Remote{

    /**
     * Create game.
     *
     * @param name          the name
     * @param maxNumPlayers the max num players
     * @param me            me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
//--------------------------GAME CREATION PHASE
    void createGame(String name, int maxNumPlayers, GameListener me) throws IOException, NotBoundException;

    /**
     * Join first available.
     *
     * @param name the name
     * @param me   me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    void joinFirstAvailable(String name, GameListener me) throws IOException, NotBoundException;

    /**
     * Join game by id.
     *
     * @param name   the name
     * @param idGame the id game
     * @param me     me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    void joinGameByID(String name, int idGame, GameListener me) throws IOException, NotBoundException;

    /**
     * Reconnect.
     *
     * @param name   the name
     * @param idGame the id game
     * @param me     me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    void reconnect(String name, int idGame, GameListener me) throws IOException, NotBoundException;

    /**
     * Leave.
     *
     * @param nick   the nick
     * @param idGame the id game
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    void leave(String nick, int idGame) throws IOException, NotBoundException;

    /**
     * Ready.
     *
     * @param nickname the nickname
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
//--------------------------READY PHASE
    void ready(String nickname) throws IOException, NotBoundException;

    /**
     * Sets max n um.
     *
     * @param num the num
     * @throws IOException the io exception
     */
//--------------------------GAME FLOW PHASE
    void setMaxNUm(int num) throws IOException;

    /**
     * Add card.
     *
     * @param nickname       the nickname
     * @param cardToAdd      the card to add
     * @param cardOnBoard    the card on board
     * @param cornerToAttach the corner to attach
     * @param flip           the flip
     * @throws IOException the io exception
     */
    void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws IOException;

    /**
     * Add starting card.
     *
     * @param nickname the nickname
     * @param flip     the flip
     * @throws IOException the io exception
     */
    void addStartingCard(String nickname, Boolean flip) throws IOException;

    /**
     * Choose player goal.
     *
     * @param nickname the nickname
     * @param choice   the choice
     * @throws IOException the io exception
     */
    void choosePlayerGoal(String nickname, int choice) throws IOException;

    /**
     * Draw resource from deck.
     *
     * @param nickname the nickname
     * @throws IOException the io exception
     */
    void drawResourceFromDeck(String nickname) throws IOException;

    /**
     * Draw gold from deck.
     *
     * @param nickname the nickname
     * @throws IOException the io exception
     */
    void drawGoldFromDeck(String nickname) throws IOException;

    /**
     * Draw from board.
     *
     * @param nickname the nickname
     * @param position the position
     * @throws IOException the io exception
     */
    void drawFromBoard(String nickname, int position) throws IOException;

    /**
     * Send message.
     *
     * @param txt      the txt
     * @param nickname the nickname
     * @throws IOException the io exception
     */
//--------------------------CHAT
    void sendMessage(String txt, String nickname) throws IOException;

    /**
     * Send private message.
     *
     * @param txt              the txt
     * @param nicknameSender   the nickname sender
     * @param nicknameReciever the nickname reciever
     * @throws IOException the io exception
     */
    void sendPrivateMessage(String txt, String nicknameSender, String nicknameReciever) throws IOException;

    /**
     * Gets public chat log.
     *
     * @param requesterName the requester name
     * @throws IOException the io exception
     */
    void getPublicChatLog(String requesterName) throws IOException;

    /**
     * Gets private chat log.
     *
     * @param yourName  your name
     * @param otherName the other name
     * @throws IOException the io exception
     */
    void getPrivateChatLog(String yourName, String otherName) throws IOException;

    /**
     * Pong.
     *
     * @param me me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    void pong(String me) throws IOException, NotBoundException;
}
