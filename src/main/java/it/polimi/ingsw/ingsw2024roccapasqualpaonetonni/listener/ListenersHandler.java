package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.util.*;

/*
this abstract class defines the thing that are common for each for the ListenersHandler classes. It defines the methods to add a listener to the list and to get it back
 */
public abstract class ListenersHandler {
    protected Map<String, NotifierInterface> listenersMap;
    public ListenersHandler(){
        listenersMap = new HashMap<>();
    }

    //if the listeners connection is RMI we make a RMINotifier, if it's Socket we make a SocketNotifier
    //the notifier is created with the listener, so that it knows who to send things to
    //the notifiers are used to make a distinction between sending objects with RMI and with Socket
    public synchronized void addListener(String nickname, NotifierInterface notifier) throws RemoteException {
        listenersMap.put(nickname, notifier);
    }

    public synchronized void removeListener(String nickname) {
        for (String name : listenersMap.keySet()) {
            if (name.equals(nickname)) {
                listenersMap.remove(nickname);
            }
        }
    }

    public synchronized HashMap<String, NotifierInterface> getListener(){
        return (HashMap<String, NotifierInterface>) listenersMap;
    }
}
