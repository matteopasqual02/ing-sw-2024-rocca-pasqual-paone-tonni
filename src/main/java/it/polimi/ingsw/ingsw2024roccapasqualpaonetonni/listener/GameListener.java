package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

/*
GameListener is an interface with all the methods that are used to comunicare with the RMIClient in order to show update of changes.
The methods here are only the ones that set somethings, not those who get something, because we only want to notify the client if something has actually changed
 */
public interface GameListener{
    void playerJoined(GameImmutable model);
    void fullGame(GameImmutable model);

    void nameAlreadyInGame(GameImmutable model);

    void playersAreMaxGame(GameImmutable model);

    void nextTurn(GameImmutable model);

    void reconnectedPlayer(GameImmutable model);

    void disconnectedPlayer(GameImmutable model);

    void removedPlayer(GameImmutable model);

    void tableCreated(GameImmutable model);

    void playersNotReady(GameImmutable model);

    void firstPlayerSet(GameImmutable model);

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
}
