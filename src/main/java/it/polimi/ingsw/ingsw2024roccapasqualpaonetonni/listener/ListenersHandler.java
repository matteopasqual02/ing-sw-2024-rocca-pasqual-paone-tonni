package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;

import java.util.*;

public class ListenersHandler {
    private final List<GameListener> listeners;

    public ListenersHandler(){
        listeners = new ArrayList<>();
    }

    public synchronized void addListener(GameListener g){
        listeners.add(g);
    }

    public synchronized List<GameListener> getListener(){
        return listeners;
    }

    public void notify_addedPlayer(Game model) {
    }

    public void notify_fullGame(Game model) {
    }

    public void notify_nameAlreadyInGame(Game model) {
    }

    public void notify_maxPlayers(Game model) {
    }

    public void notify_nextTurn(Game model) {
    }

    public void notify_reconnectedPlayer(Game model) {
    }

    public void notify_disconnectedPlayer(Game model) {
    }

    public void notify_removedPlayer(Game model) {
    }

    public void notify_tableCreated(Game model) {
    }

    public void notify_playersNotReady(Game model) {
    }

    public void notify_firstPlayerSet(Game model) {
    }

    public void notify_cardAdded(Game model) {
    }

    public void notify_startingCardAdded(Game model) {
    }

    public void notify_goalChosen(Game model) {
    }

    public void notify_cannotDrawHere(Game model) {
    }

    public void notify_decksAllEmpty(Game model) {
    }

    public void notify_resourceDrawn(Game model) {
    }

    public void notify_changeResourceDeck(Game model) {
    }

    public void notify_goldDrawn(Game model) {
    }

    public void notify_changeGoldDeck(Game model) {
    }

    public void notify_drewFromBoard(Game model) {
    }

    public void notify_changeBoardDeck(Game model) {
    }

    public void notify_gotGame(Game model) {
    }

    public void notify_gotPoints(Game model) {
    }

    public void notify_gotBoardDeck(Game model) {
    }
}
