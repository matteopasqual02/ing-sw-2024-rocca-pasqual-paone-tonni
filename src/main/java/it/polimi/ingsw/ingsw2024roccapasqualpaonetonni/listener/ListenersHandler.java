package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.rmi.RemoteException;
import java.util.*;

/*
this abstract class defines the thing that are common for each for the ListenersHandler classes. It defines the methods to add a listener to the list and to get it back
 */
public abstract class ListenersHandler {
    protected HashMap<GameListener, NotifierInterface> listenersMap;
    public ListenersHandler(){
        listenersMap = new HashMap<>();
    }

    //if the listeners connection is RMI we make a RMINotifier, if it's Socket we make a SocketNotifier
    //the notifier is created with the listener, so that it knows who to send things to
    //the notifiers are used to make a distinction between sending objects with RMI and with Socket
    public synchronized void addListener(GameListener g, NotifierInterface notifier) throws RemoteException {
        listenersMap.put(g, notifier);
    }

    public synchronized void removeListener(GameListener g){
        listenersMap.remove(g);
        //listeners.remove(g);
    }

    public synchronized HashMap<GameListener, NotifierInterface> getListener(){
        //return new ArrayList<>(listenersMap.keySet());
        return listenersMap;
    }
}
