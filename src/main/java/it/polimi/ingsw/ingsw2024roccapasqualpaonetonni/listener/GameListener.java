package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ConnectionType;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ServerInterface;

/*
GameListener is an interface with all the methods that are used to comunicare with the RMIClient in order to show update of changes.
The methods here are only the ones that set somethings, not those who get something, because we only want to notify the client if something has actually changed
 */
public interface GameListener{
    ServerInterface getServerStub();
    ConnectionType getConnectionType();

    //--------------------------GAME CREATION PHASE
    void maxNumPlayersSet(int gameId, int max);
    void createdGame(int gameId);
    void youJoinedGame(int gameId, String pNickname);
    void addedNewPlayer(String pNickname);
    void noAvailableGame();
    void areYouReady();


    void playerReady(Player p);
    void fullGame();
    void nameAlreadyInGame();
    void playerRemoved(Player p);
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
    //void tableCreated(GameImmutable model);
    //void playersNotReady(GameImmutable model);
    void firstPlayerSet(Player first);
    void drawableDeckSet(DrawableDeck d);
    void boardDeckSet(BoardDeck bd);

    void startAdded(PlayerBoard board, Player p);
    void cardAdded(PlayerBoard board, Player p);
    void choseInvalidPlace(Player p);
    void conditionsNotMet(Player p);
    void startingCardDrew(StartingCard start, Player p);
    void drewPersonalGoals(ObjectiveCard[] goals, Player p);
    void personalGoalChosen(ObjectiveCard goal, Player p);
    void cardNotInHand(PlayingCard card,Player p);
    void resourceDrawn(PlayingCard card, Player p);
    void goldDrawn(PlayingCard card, Player p);
    void drewFromBoard(PlayingCard card, Player p);
    void playerIsConnected(Player p);
    void pointsIncreased(int points,Player p);
    void seedCountUpdated(int[] seedCount,Player p);
    void cardRemovedFromHand(PlayingCard card, Player p);
}
