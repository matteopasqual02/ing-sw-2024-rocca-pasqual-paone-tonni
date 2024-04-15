package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.util.*;

/*
this class handles the listeners: the listeners are elements related to each client, when a change
occurs in the model (the controller does something) one of these methods gets called in order to show the
change to each of the clients. These methods call other methods on the actual single listener giving it an immutable model
to show the change
 */
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

    public void notify_addedPlayer(GameImmutable model) {
    }

    public void notify_fullGame(GameImmutable model) {
    }

    public void notify_nameAlreadyInGame(GameImmutable model) {
    }

    public void notify_maxPlayers(GameImmutable model) {
    }

    public void notify_nextTurn(GameImmutable model) {
    }

    public void notify_reconnectedPlayer(GameImmutable model) {
    }

    public void notify_disconnectedPlayer(GameImmutable model) {
    }

    public void notify_removedPlayer(GameImmutable model) {
    }

    public void notify_tableCreated(GameImmutable model) {
    }

    public void notify_playersNotReady(GameImmutable model) {
    }

    public void notify_firstPlayerSet(GameImmutable model) {
    }

    public void notify_cardAdded(GameImmutable model) {
    }

    public void notify_startingCardAdded(GameImmutable model) {
    }

    public void notify_goalChosen(GameImmutable model) {
    }

    public void notify_cannotDrawHere(GameImmutable model) {
    }

    public void notify_decksAllEmpty(GameImmutable model) {
    }

    public void notify_resourceDrawn(GameImmutable model) {
    }

    public void notify_changeResourceDeck(GameImmutable model) {
    }

    public void notify_goldDrawn(GameImmutable model) {
    }

    public void notify_changeGoldDeck(GameImmutable model) {
    }

    public void notify_drewFromBoard(GameImmutable model) {
    }

    public void notify_changeBoardDeck(GameImmutable model) {
    }


}
