package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ConnectionType;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ServerInterface;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/*
GameListener is an interface with all the methods that are used to comunicare with the RMIClient in order to show update of changes.
The methods here are only the ones that set somethings, not those who get something, because we only want to notify the client if something has actually changed
 */
public interface GameListener extends Remote {
    ServerInterface getServerStub() throws RemoteException;
    ConnectionType getConnectionType() throws RemoteException;
    String getNickname() throws RemoteException;

    //--------------------------GAME CREATION PHASE
    void maxNumPlayersSet(int max) throws RemoteException;
    void createdGame(int gameId) throws RemoteException;
    void youJoinedGame(int gameId, String pNickname) throws RemoteException;
    void addedNewPlayer(String pNickname) throws RemoteException;
    void noAvailableGame() throws RemoteException;
    void fullGame() throws RemoteException;
    void nameAlreadyInGame() throws RemoteException;
    void areYouReady() throws RemoteException;


    void playerReady(Player p) throws RemoteException;
    void playerRemoved(String p) throws RemoteException;
    void nextTurn(Player p) throws RemoteException;
    void lastTurn() throws RemoteException;
    void reconnectedPlayer(String nickname) throws RemoteException;
    void reconnectionImpossible(String nickname) throws RemoteException;
    void disconnectedPlayer(String nickname) throws RemoteException;
    void disconnectionImpossible(String nickname) throws RemoteException;
    void statusSet(GameStatus status) throws RemoteException;
    void statusSetToLastStatus(GameStatus status) throws RemoteException;
    void lastStatusReset() throws RemoteException;
    void playerIsReady(Player p) throws RemoteException;
    //void tableCreated(GameImmutable model);
    //void playersNotReady(GameImmutable model);
    void firstPlayerSet(Player first) throws RemoteException;
    void drawableDeckSet(DrawableDeck d) throws RemoteException;
    void boardDeckSet(BoardDeck bd) throws RemoteException;
    void newMessage(Message message) throws RemoteException;

    void chatUpdate(List<Message> allMessages) throws RemoteException;

    void newPrivateMessage(PrivateMessage message) throws RemoteException;

    /*
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
     */
}
