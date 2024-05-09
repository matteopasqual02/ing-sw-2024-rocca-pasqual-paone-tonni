package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

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

    public void resetPlayerListeners(HashMap<GameListener, NotifierInterface> gameListenersMap){
        //listenersMap=null;
        for(GameListener lis : gameListenersMap.keySet()){
            try {
                addListener(lis,gameListenersMap.get(lis));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_chooseGoal(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendPersonalGoalChosen(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_drawGoldFromDeck(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendGoldDrawn(p);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_drawResourceFromDeck(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendResourceDrawn(p);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_drawFromBoard(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendDrewFromBoard(p);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_addStarting( Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendStartAdded(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_cardNotInHand(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendCardNotInHand(p);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_addToBoard(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendCardAdded(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_invalidPlace(Player p){
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendChoseInvalidPlace(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_conditionsNotMet(Player p){
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendConditionsNotMet(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_removeFromHand(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendCardRemovedFromHand(p);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
