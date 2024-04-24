package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;

public class RMINotifier implements NotifierInterface{
    private final GameListener listener;

    public RMINotifier(GameListener g){
        listener = g;
    }
    @Override
    public void sendMaxNumPlayersSet(int gameId, int max) {
        listener.maxNumPlayersSet(gameId,max);
    }

    @Override
    public void sendAddedPlayer(Player newPlayer) {
        listener.addedPlayer(newPlayer);

    }

    @Override
    public void sendPlayerReady(Player p) {
        listener.playerReady(p);
    }

    @Override
    public void sendFullGame() {
        listener.fullGame();

    }

    @Override
    public void sendNameAlreadyInGame() {
        listener.nameAlreadyInGame();

    }

    @Override
    public void sendPlayerRemoved(Player p) {
        listener.playerRemoved(p);


    }

    @Override
    public void sendNextTurn(Player p) {
        listener.nextTurn(p);


    }

    @Override
    public void sendLastTurn() {
        listener.lastTurn();


    }

    @Override
    public void sendReconnectedPlayer(String nickname) {
        listener.reconnectedPlayer(nickname);

    }

    @Override
    public void sendReconnectionImpossible(String nickname) {
        listener.reconnectionImpossible(nickname);


    }

    @Override
    public void sendDisconnectedPlayer(String nickname) {
        listener.disconnectedPlayer(nickname);


    }

    @Override
    public void sendDisconnectionImpossible(String nickname) {
        listener.disconnectionImpossible(nickname);


    }

    @Override
    public void sendStatusSet(GameStatus status) {
        listener.statusSet(status);

    }

    @Override
    public void sendStatusSetToLastStatus(GameStatus status) {
        listener.statusSetToLastStatus(status);


    }

    @Override
    public void sendLastStatusReset() {
        listener.lastStatusReset();


    }

    @Override
    public void sendPlayerIsReady(Player p) {
        listener.playerIsReady(p);


    }

    @Override
    public void sendFirstPlayerSet(Player first) {
        listener.firstPlayerSet(first);


    }

    @Override
    public void sendDrawableDeckSet(DrawableDeck d) {
        listener.drawableDeckSet(d);


    }

    @Override
    public void sendBoardDeckSet(BoardDeck bd) {
        listener.boardDeckSet(bd);


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
}
