package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ConnectionType;

import java.util.*;

/*
this abstract class defines the thing that are common for each for the ListenersHandler classes. It defines the methods to add a listener to the list and to get it back
 */
public abstract class ListenersHandler {
    protected HashMap<GameListener,NotifierInterface> listenersMap;
    //protected List<GameListener> listeners;
    //protected List<NotifierInterface> notifiers;
    private ConnectionType connection;

    public ListenersHandler(){

        listenersMap = new HashMap<GameListener,NotifierInterface>();
        //listeners = new ArrayList<>();
        //notifiers = new ArrayList<>();
        connection = null;
    }

    //if the listeners connection is RMI we make a RMINotifier, if it's Socket we make a SocketNotifier
    //the notifier is created with the listener, so that it knows who to send things to
    //the notifiers are used to make a distinction between sending objects with RMI and with Socket
    public synchronized void addListener(GameListener g){

        //listeners.add(g);
        connection = g.getConnectionType();
        switch (connection){
            case RMI->{
                NotifierInterface n = new RMINotifier(g);
                //notifiers.add(n);
                listenersMap.put(g,n);
            }
            case SOCKET->{
                NotifierInterface n = new SocketNotifier(g);
                //notifiers.add(n);
                listenersMap.put(g,n);
            }
        }
    }
    public synchronized void removeListener(GameListener g){
        listenersMap.remove(g);
        //listeners.remove(g);
    }

    /*public synchronized List<GameListener> getListener(){
        return listenersMap.
        //return listeners;
    }*/
}
