package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

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
}
