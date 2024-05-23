package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The interface Main controller interface.
 */
public interface MainControllerInterface extends Remote{
    /**
     * Gets running games.
     *
     * @return the running games
     * @throws RemoteException the remote exception
     */
    List<GameController> getRunningGames() throws RemoteException;

    /**
     * Create game controller game controller interface.
     *
     * @param nickname       the nickname
     * @param numMaxOfPlayer the num max of player
     * @param notifier       the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    GameControllerInterface createGameController(String nickname, int numMaxOfPlayer, NotifierInterface notifier) throws RemoteException;

    /**
     * Join first available game game controller interface.
     *
     * @param nickname the nickname
     * @param notifier the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    GameControllerInterface joinFirstAvailableGame(String nickname, NotifierInterface notifier) throws RemoteException;

    /**
     * Join game by id game controller interface.
     *
     * @param nickname    the nickname
     * @param idToConnect the id to connect
     * @param notifier    the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    GameControllerInterface joinGameByID(String nickname, int idToConnect, NotifierInterface notifier) throws RemoteException;

    /**
     * Reconnect game controller interface.
     *
     * @param nickname      the nickname
     * @param idToReconnect the id to reconnect
     * @param notifier      the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    GameControllerInterface reconnect(String nickname, int idToReconnect, NotifierInterface notifier) throws RemoteException;

    /**
     * Leave game controller interface.
     *
     * @param nickname       the nickname
     * @param idToDisconnect the id to disconnect
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    GameControllerInterface leaveGame(String nickname, int idToDisconnect) throws RemoteException;

    /**
     * Clear singleton.
     *
     * @throws RemoteException the remote exception
     */
    void clearSingleton() throws RemoteException;
}
