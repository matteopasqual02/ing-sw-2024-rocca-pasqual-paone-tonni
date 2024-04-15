package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.util.*;

/*
this class handles the listeners: the listeners are elements related to each client, when a change
occurs in the model (the controller does something) one of these methods gets called in order to show the
change to each of the clients. These methods call other methods on the actual single listener giving it an immutable model
to show the change

these are not hte methods that directly notify the clients, they are the ones that call those methods on all of the clients. We could have done that in the controller
but this way it's less confusing, or else everything would be in the controller
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

    public void notify_addedPlayer(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.playerJoined(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_fullGame(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.fullGame(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_nameAlreadyInGame(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.nameAlreadyInGame(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_maxPlayers(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.playersAreMaxGame(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_nextTurn(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.nextTurn(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_reconnectedPlayer(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.reconnectedPlayer(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_disconnectedPlayer(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.disconnectedPlayer(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_removedPlayer(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.removedPlayer(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_tableCreated(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.tableCreated(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_playersNotReady(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.playersNotReady(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_firstPlayerSet(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.firstPlayerSet(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_cardAdded(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.cardAdded(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_startingCardAdded(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.startingCardAdded(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_goalChosen(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.goalChosen(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_cannotDrawHere(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.cannotDrawHere(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_decksAllEmpty(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.decksAllEmpty(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_resourceDrawn(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.resourceDrawn(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_changeResourceDeck(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.changeResourceDeck(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_goldDrawn(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.goldDrawn(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_changeGoldDeck(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.changeGoldDeck(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_drewFromBoard(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.drewFromBoard(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }

    public void notify_changeBoardDeck(Game model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()){
            GameListener listener = i.next();
            listener.changeBoardDeck(new GameImmutable(model));
            //ci sara da gestire qui il caso della disconnessione durante la notifica
        }
    }
}
