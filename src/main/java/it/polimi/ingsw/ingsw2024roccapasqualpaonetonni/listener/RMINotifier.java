package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;

import java.rmi.RemoteException;

public class RMINotifier implements NotifierInterface{
    private final GameListener listener;

    public RMINotifier(GameListener g){
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
    public void sendYouJoinedGame(int gameId, String pNickname) {
        try {
            listener.youJoinedGame(gameId, pNickname);
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

    }

    @Override
    public void sendCardAdded(PlayerBoard board, Player p) {

    }

    @Override
    public void sendChoseInvalidPlace(Player p) {

    }

    @Override
    public void sendConditionsNotMet(Player p) {

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

    }

    @Override
    public void sendGoldDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void sendDrewFromBoard(PlayingCard card, Player p) {

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
}
