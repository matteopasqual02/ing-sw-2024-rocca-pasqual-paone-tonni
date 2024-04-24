package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.SocketWrapper;

public class SocketNotifier implements NotifierInterface{
    private GameListener listener;
    private SocketWrapper reciever;

    public SocketNotifier(GameListener g){

        listener = g;
        reciever = new SocketWrapper(g);
    }

    @Override
    public void sendMaxNumPlayersSet(int gameId, int max) {
        /*make a serializable object to put in the method
        SerializableInt changes;
        serialize the object to send
         */
        reciever.recieveMaxNumPlayersSet();

    }

    @Override
    public void sendAddedPlayer(Player newPlayer) {

    }

    @Override
    public void sendPlayerReady(Player p) {

    }

    @Override
    public void sendFullGame() {

    }

    @Override
    public void sendNameAlreadyInGame() {

    }

    @Override
    public void sendPlayerRemoved(Player p) {

    }

    @Override
    public void sendNextTurn(Player p) {

    }

    @Override
    public void sendLastTurn() {

    }

    @Override
    public void sendReconnectedPlayer(String nickname) {

    }

    @Override
    public void sendReconnectionImpossible(String nickname) {

    }

    @Override
    public void sendDisconnectedPlayer(String nickname) {

    }

    @Override
    public void sendDisconnectionImpossible(String nickname) {

    }

    @Override
    public void sendStatusSet(GameStatus status) {

    }

    @Override
    public void sendStatusSetToLastStatus(GameStatus status) {

    }

    @Override
    public void sendLastStatusReset() {

    }

    @Override
    public void sendPlayerIsReady(Player p) {

    }

    @Override
    public void sendFirstPlayerSet(Player first) {

    }

    @Override
    public void sendDrawableDeckSet(DrawableDeck d) {

    }

    @Override
    public void sendBoardDeckSet(BoardDeck bd) {

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
