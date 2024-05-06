package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface NotifierInterface extends Remote {
    void sendAll(GameImmutable gameImmutable)throws IOException, RemoteException;

    //--------------------------GAME CREATION PHASE
    void sendMaxNumPlayersSet(int gameId,int max) throws IOException, ClassNotFoundException, RemoteException;
    void sendCreatedGame(int gameId) throws IOException, RemoteException;
    void sendYouJoinedGame(int gameId) throws IOException, RemoteException;
    void sendAddedNewPlayer(String pNickname) throws IOException, RemoteException;
    void sendNoAvailableGame() throws RemoteException;
    void sendAskPlayersReady() throws RemoteException, IOException;
    void sendFullGame() throws RemoteException;

    void sendNameAlreadyInGame() throws RemoteException;

    void sendPlayerRemoved(String pNickname) throws RemoteException;

    void sendNextTurn(Player p) throws RemoteException;

    void sendLastTurn() throws RemoteException;

    void sendReconnectedPlayer(String nickname) throws RemoteException;

    void sendReconnectionImpossible(String nickname) throws RemoteException;

    void sendDisconnectedPlayer(String nickname) throws RemoteException;

    void sendDisconnectionImpossible(String nickname) throws RemoteException;

    void sendStatusSet(GameStatus status) throws RemoteException;

    void sendStatusSetToLastStatus(GameStatus status) throws RemoteException;

    void sendLastStatusReset() throws RemoteException;

    void sendPlayerIsReady(Player p) throws RemoteException;

    void sendFirstPlayerSet(Player first) throws RemoteException;

    void sendDrawableDeckSet(DrawableDeck d) throws RemoteException ;

    void sendBoardDeckSet(BoardDeck bd) throws RemoteException;

    void sendStartAdded(PlayerBoard board, Player p) throws IOException;

    void sendCardAdded(PlayerBoard board, Player p) throws IOException;

    void sendChoseInvalidPlace(Player p) throws IOException;

    void sendConditionsNotMet(Player p) throws IOException;

    void sendStartingCardDrew(StartingCard start, Player p) throws RemoteException;

    void sendDrewPersonalGoals(ObjectiveCard[] goals, Player p) throws RemoteException;

    void sendPersonalGoalChosen(ObjectiveCard goal, Player p) throws RemoteException;

    void sendCardNotInHand(PlayingCard card, Player p) throws RemoteException;

    void sendResourceDrawn(PlayingCard card, Player p) throws IOException;

    void sendGoldDrawn(PlayingCard card, Player p) throws IOException;

    void sendDrewFromBoard(PlayingCard card, Player p) throws IOException;

    void sendPlayerIsConnected(Player p) throws RemoteException;

    void sendPointsIncreased(int points,Player p) throws RemoteException;

    void sendSeedCountUpdated(int[] seedCount,Player p) throws RemoteException;

    void sendCardRemovedFromHand(PlayingCard card, Player p) throws RemoteException;

    void sendPlayerReady(Player p) throws RemoteException;

    void sendYouWereRemoved(String pNickname) throws IOException;

    void youWereReconnected() throws RemoteException;

    void sendYouAreFirst() throws RemoteException;

    void sendItsYourTurn() throws RemoteException;

    void sendUpdatedChat(List<Message> allMessages) throws RemoteException;

    void sendMessage(Message message) throws IOException;

    void sendPrivateMessage(PrivateMessage message) throws IOException;

    void sendPublicChatLog(String requesterName, List<Message> allMessages) throws IOException;

    void sendPrivateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) throws IOException;


}
