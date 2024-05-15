package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Listeners handler.
 */
/*
this abstract class defines the thing that are common for each for the ListenersHandler classes. It defines the methods to add a listener to the list and to get it back
 */
public abstract class ListenersHandler {
    /**
     * The Listeners map.
     */
    protected Map<String, NotifierInterface> listenersMap;

    /**
     * Instantiates a new Listeners handler.
     */
    public ListenersHandler(){
        listenersMap = new HashMap<>();
    }

    /**
     * Add listener.
     *
     * @param nickname the nickname
     * @param notifier the notifier
     * @throws RemoteException the remote exception
     */
//if the listeners connection is RMI we make a RMINotifier, if it's Socket we make a SocketNotifier
    //the notifier is created with the listener, so that it knows who to send things to
    //the notifiers are used to make a distinction between sending objects with RMI and with Socket
    public synchronized void addListener(String nickname, NotifierInterface notifier) throws RemoteException {
        listenersMap.put(nickname, notifier);
    }

    /**
     * Remove listener.
     *
     * @param nickname the nickname
     */
    public synchronized void removeListener(String nickname) {
        for (String name : listenersMap.keySet()) {
            if (name.equals(nickname)) {
                listenersMap.remove(nickname);
            }
        }
    }

    /**
     * Get listener hash map.
     *
     * @return the hash map
     */
    public synchronized HashMap<String, NotifierInterface> getListener(){
        return (HashMap<String, NotifierInterface>) listenersMap;
    }
}
