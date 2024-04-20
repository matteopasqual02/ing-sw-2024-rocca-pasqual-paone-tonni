package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;


/*
GameListener is an interface with all the methods that are used to comunicare with the RMIClient in order to show update of changes.
The methods here are only the ones that set somethings, not those who get something, because we only want to notify the client if something has actually changed
 */
public interface GameListener{
    void maxNumPlayersSet(int gameId,int max);
    void addedPlayer(Player newPlayer);
    void playerReady(Player p);
    void fullGame();

    void nameAlreadyInGame();
    void playerRemoved(Player p);

    void playersAreMaxGame(GameImmutable model);

    void nextTurn(Player p);
    void lastTurn();

    void reconnectedPlayer(String nickname);
    void reconnectionImpossible(String nickname);

    void disconnectedPlayer(String nickname);
    void disconnectionImpossible(String nickname);
    void statusSet(GameStatus status);
    void statusSetToLastStatus(GameStatus status);
    void lastStatusReset();
    void playerIsReady(Player p);
    void tableCreated(GameImmutable model);

    void playersNotReady(GameImmutable model);

    void firstPlayerSet(Player first);
    void drawableDeckSet(DrawableDeck d);
    void boardDeckSet(BoardDeck bd);

    void cardAdded(GameImmutable model);

    void startingCardAdded(GameImmutable model);

    void goalChosen(GameImmutable model);

    void cannotDrawHere(GameImmutable model);

    void decksAllEmpty(GameImmutable model);

    void resourceDrawn(GameImmutable model);

    void changeResourceDeck(GameImmutable model);

    void goldDrawn(GameImmutable model);

    void changeGoldDeck(GameImmutable model);

    void drewFromBoard(GameImmutable model);

    void changeBoardDeck(GameImmutable model);

    void playerJoined(GameImmutable gameImmutable);
}
