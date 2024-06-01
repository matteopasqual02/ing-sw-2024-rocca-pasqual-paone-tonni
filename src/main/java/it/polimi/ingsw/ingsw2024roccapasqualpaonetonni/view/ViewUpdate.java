package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.util.List;

/**
 * The interface View update.
 */
public interface ViewUpdate {
    /**
     * Join lobby.
     */
    void joinLobby();

    /**
     * Show all.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
    void show_All(GameImmutable gameImmutable, String nickname, EnumUpdates type, boolean myTurn);

    /**
     * Show max num players set.
     *
     * @param max the max
     */
    void show_maxNumPlayersSet(int max);

    /**
     * Show created game.
     *
     * @param gameID the game id
     */
    void show_createdGame(int gameID);

    /**
     * Show you joined game.
     *
     * @param gameID the game id
     */
    void show_youJoinedGame(int gameID);

    /**
     * Show no available game.
     */
    void show_noAvailableGame();

    /**
     * Show added new player.
     *
     * @param pNickname the p nickname
     */
    void show_addedNewPlayer(String pNickname);

    /**
     * Show are you ready.
     */
    void show_areYouReady();

    /**
     * Invalid message.
     *
     * @param s the s
     */
    void invalidMessage(String s, boolean myTurn);

    /**
     * My running turn choose objective.
     */
    void myRunningTurnChooseObjective();

    /**
     * My running turn place starting.
     */
    void myRunningTurnPlaceStarting();

    /**
     * My running turn draw card.
     */
    void myRunningTurnDrawCard();

    /**
     * My running turn place card.
     */
    void myRunningTurnPlaceCard();

    /**
     * Not my turn.
     */
    void notMyTurn();

    /**
     * Display chat.
     *
     * @param s the string message
     */
    void displayChat(String s);

    /**
     * Display chat, informing about the type of the message
     *
     * @param s the string message
     * @param type the type of the message
     */
    void displayChat(String s, String type);

    /**
     * Not my turn chat.
     */
    void notMyTurnChat();

    /**
     * Show status.
     *
     * @param s the s
     */
    void show_status(String s);

    /**
     * Show status last.
     *
     * @param string the string
     */
    void show_statusLast(String string);

    /**
     * Winners.
     *
     * @param list the list
     * @param nick the nick
     */
    void winners(List<Player> list, String nick);

    /**
     * Show generic.
     *
     * @param msg the nickname
     */
    void show_generic(String msg);


}

