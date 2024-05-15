package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.util.List;

public interface ViewUpdate {
    void joinLobby();
    void show_All(GameImmutable gameImmutable, String nickname);

    void show_maxNumPlayersSet(int max);
    void show_createdGame(int gameID);
    void show_youJoinedGame(int gameID);
    void show_noAvailableGame();
    void show_addedNewPlayer(String pNickname);
    void show_areYouReady();

    void invalidMessage(String s);

    void myRunningTurnChooseObjective();
    void myRunningTurnPlaceStarting();
    void myRunningTurnDrawCard();
    void myRunningTurnPlaceCard();
    void notMyTurn();

    void displayChat(String s);

    void notMyTurnChat();

    void show_status(String s);

    void winners(List<Player> list, String nick);
}

