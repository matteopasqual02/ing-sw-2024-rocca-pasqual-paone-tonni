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
public abstract class ListenersHandler {
    protected List<GameListener> listeners;

    public ListenersHandler(){
        listeners = new ArrayList<>();
    }

    public synchronized void addListener(GameListener g){
        listeners.add(g);
    }

    public synchronized List<GameListener> getListener(){
        return listeners;
    }
}
