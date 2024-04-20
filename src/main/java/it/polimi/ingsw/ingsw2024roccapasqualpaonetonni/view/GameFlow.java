package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIClient;

public class GameFlow implements GameListener {
    VirtualViewInterface client;
    public GameFlow(ConnectionType conSel){
        switch (conSel){
            /*devo in base al caso far si che le azioni fattibili siano quelle di client o di socket
            cosi ho un solo oggetto di azioni che possono essere azioni socket o azioni rmi a seconda della scelta di connessione*/
            case RMI -> client = new RMIClient();
            //case SOCKET ->
        }
    }

    //in questi metodi che arrivano del listener a seconda di se sono in rmi o socket (controllo da fare li dentro) dovro
    //cambiare il modo in cui faccio vedere gli update alla tui
    @Override
    public void maxNumPlayersSet(int gameId, int max) {

    }

    @Override
    public void addedPlayer(Player newPlayer) {

    }

    @Override
    public void playerReady(Player p) {

    }

    @Override
    public void fullGame() {

    }

    @Override
    public void nameAlreadyInGame() {

    }

    @Override
    public void playerRemoved(Player p) {

    }

    @Override
    public void nextTurn(Player p) {

    }

    @Override
    public void lastTurn() {

    }

    @Override
    public void reconnectedPlayer(String nickname) {

    }

    @Override
    public void reconnectionImpossible(String nickname) {

    }

    @Override
    public void disconnectedPlayer(String nickname) {

    }

    @Override
    public void disconnectionImpossible(String nickname) {

    }

    @Override
    public void statusSet(GameStatus status) {

    }

    @Override
    public void statusSetToLastStatus(GameStatus status) {

    }

    @Override
    public void lastStatusReset() {

    }

    @Override
    public void playerIsReady(Player p) {

    }

    @Override
    public void firstPlayerSet(Player first) {

    }

    @Override
    public void drawableDeckSet(DrawableDeck d) {

    }

    @Override
    public void boardDeckSet(BoardDeck bd) {

    }

    @Override
    public void startAdded(PlayerBoard board, Player p) {

    }

    @Override
    public void cardAdded(PlayerBoard board, Player p) {

    }

    @Override
    public void choseInvalidPlace(Player p) {

    }

    @Override
    public void conditionsNotMet(Player p) {

    }

    @Override
    public void startingCardDrew(StartingCard start, Player p) {

    }

    @Override
    public void drewPersonalGoals(ObjectiveCard[] goals, Player p) {

    }

    @Override
    public void personalGoalChosen(ObjectiveCard goal, Player p) {

    }

    @Override
    public void cardNotInHand(PlayingCard card, Player p) {

    }

    @Override
    public void resourceDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void goldDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void drewFromBoard(PlayingCard card, Player p) {

    }

    @Override
    public void playerIsConnected(Player p) {

    }

    @Override
    public void pointsIncreased(int points, Player p) {

    }

    @Override
    public void seedCountUpdated(int[] seedCount, Player p) {

    }

    @Override
    public void cardRemovedFromHand(PlayingCard card, Player p) {

    }
}
