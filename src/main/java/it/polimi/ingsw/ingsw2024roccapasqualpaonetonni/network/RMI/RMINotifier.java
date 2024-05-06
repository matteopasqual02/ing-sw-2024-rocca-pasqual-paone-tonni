package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMINotifier extends UnicastRemoteObject implements NotifierInterface {
    private final GameListener listener;

    public RMINotifier(GameListener g) throws RemoteException {
        listener = g;
    }

    //--------------------------GAME CREATION PHASE
    @Override
    public void sendMaxNumPlayersSet(int gameId, int max) {
        try {
            listener.maxNumPlayersSet(max);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendCreatedGame(int gameId) {
        try {
            listener.createdGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendYouJoinedGame(int gameId) {
        try {
            listener.youJoinedGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendAddedNewPlayer(String pNickname) {
        try {
            listener.addedNewPlayer(pNickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendNoAvailableGame() {
        try {
            listener.noAvailableGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendAskPlayersReady() {
        try {
            listener.areYouReady();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendFullGame() {
        try {
            listener.fullGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendNameAlreadyInGame() {
        try {
            listener.nameAlreadyInGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendPlayerRemoved(String pNickname) {
        try {
            listener.playerRemoved(pNickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendNextTurn(Player p) {
        try {
            listener.nextTurn(p);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendLastTurn() {
        try {
            listener.lastTurn();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendReconnectedPlayer(String nickname) {
        try {
            listener.reconnectedPlayer(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendReconnectionImpossible(String nickname) {
        try {
            listener.reconnectionImpossible(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendDisconnectedPlayer(String nickname) {
        try {
            listener.disconnectedPlayer(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendDisconnectionImpossible(String nickname) {
        try {
            listener.disconnectionImpossible(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendStatusSet(GameStatus status) {
        try {
            listener.statusSet(status);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendStatusSetToLastStatus(GameStatus status) {
        try {
            listener.statusSetToLastStatus(status);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendLastStatusReset() {
        try {
            listener.lastStatusReset();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendPlayerIsReady(Player p) {
        try {
            listener.playerIsReady(p);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendFirstPlayerSet(Player first) {
        try {
            listener.firstPlayerSet(first);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendDrawableDeckSet(DrawableDeck d) {
        try {
            listener.drawableDeckSet(d);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendBoardDeckSet(BoardDeck bd) {
        try {
            listener.boardDeckSet(bd);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendStartAdded(PlayerBoard board, Player p) {
        try {
            listener.startAdded(board,p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendCardAdded(PlayerBoard board, Player p) {
        try {
            listener.cardAdded(board, p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendChoseInvalidPlace(Player p) {
        try {
            listener.choseInvalidPlace(p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendConditionsNotMet(Player p) {
        try {
            listener.conditionsNotMet(p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendStartingCardDrew(StartingCard start, Player p) {

    }

    @Override
    public void sendDrewPersonalGoals(ObjectiveCard[] goals, Player p) {

    }

    @Override
    public void sendPersonalGoalChosen(ObjectiveCard goal, Player p) {

    }

    @Override
    public void sendCardNotInHand(PlayingCard card, Player p) {

    }

    @Override
    public void sendResourceDrawn(PlayingCard card, Player p) {
        try {
            listener.resourceDrawn(card,p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendGoldDrawn(PlayingCard card, Player p) {
        try {
            listener.goldDrawn(card, p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendDrewFromBoard(PlayingCard card, Player p) {
        try {
            listener.drewFromBoard(card, p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPlayerIsConnected(Player p) {

    }

    @Override
    public void sendPointsIncreased(int points, Player p) {

    }

    @Override
    public void sendSeedCountUpdated(int[] seedCount, Player p) {

    }

    @Override
    public void sendCardRemovedFromHand(PlayingCard card, Player p) {

    }

    @Override
    public void sendPlayerReady(Player p) {

    }

    @Override
    public void sendYouWereRemoved(String pNickname) {

    }

    @Override
    public void youWereReconnected() {

    }

    @Override
    public void sendYouAreFirst() {

    }

    @Override
    public void sendItsYourTurn() {

    }

    @Override
    public void sendUpdatedChat(List<Message> allMessages) throws RemoteException {
        try {
            listener.chatUpdate(allMessages);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        try {
            listener.newMessage(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPrivateMessage(PrivateMessage message) throws IOException {
        try {
            if(listener.getNickname().equals(message.getReceiver())){
                listener.newPrivateMessage(message);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPublicChatLog(String requesterName, List<Message> allMessages) throws IOException {
        try {
            if(listener.getNickname().equals(requesterName)){
                listener.publicChatLog(allMessages);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPrivateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) throws IOException {
        try {
            if(listener.getNickname().equals(yourName)){
                listener.privateChatLog(otherName,privateChat);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
