package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import javafx.util.Pair;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;

/*
this class handles the listeners of the player class in the model: the listeners are elements related to each client, when a change
occurs in the model (the controller does something) one of these methods gets called in order to show the
change to each of the clients. These methods call other methods on the actual single listener giving it the change occurred

these are not the methods that directly notify the clients, they are the ones that call those methods on all of the clients.
 */
public class PlayerListenersHandler extends ListenersHandler implements Serializable {

    public PlayerListenersHandler(){
        super();
    }
    public void resetPlayerListeners(HashMap<String, NotifierInterface> gameListenersMap){
        //listenersMap=null;
        for(String name : gameListenersMap.keySet()){
            try {
                addListener(name, gameListenersMap.get(name));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_chooseGoal(Player p) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendPersonalGoalChosen(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_drawGoldFromDeck(Player p,DrawableDeck d) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendGoldDrawn(p,d);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_drawResourceFromDeck(Player p,DrawableDeck d) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendResourceDrawn(p,d);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_drawFromBoard(Player p, BoardDeck b, DrawableDeck d) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendDrewFromBoard(p,b,d);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_addStarting(Player p) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendStartAdded(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_addToBoard(Player p) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendCardAdded(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_playerGenericError(String s){
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).genericError(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
