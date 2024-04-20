package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.util.*;

/*
this abstract class defines the thing that are common for each for the ListenersHandler classes. It defines the methods to add a listener to the list and to get it back
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
