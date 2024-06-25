package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Main controller.
 */
public class MainController implements MainControllerInterface {
    /**
     * The constant instance.
     */
    private static MainController instance = null;

    /**
     * The Running games.
     */
    private final List<GameController> runningGames;

    /**
     * Instantiates a new Main controller.
     */
    private MainController() {
        runningGames = new ArrayList<>();
    }

    /**
     * Gets instance. This use singleton pattern
     *
     * @return the instance
     */
    public synchronized static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    /**
     * Get running games list.
     *
     * @return the list
     */
    @Override
    public synchronized List<GameController> getRunningGames(){
        return runningGames;
    }

    /**
     * Create game controller game controller interface.
     *
     * @param nickname       the nickname
     * @param numMaxOfPlayer the num max of player
     * @param notifier       the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized GameControllerInterface createGameController(String nickname, int numMaxOfPlayer, NotifierInterface notifier) throws RemoteException {
        GameController g = new GameController(getRunningGames().size()+1);
        g.setMaxNumberOfPlayer(numMaxOfPlayer);
        g.addMyselfAsListener(nickname, notifier);
        g.addPlayer(nickname);
        runningGames.add(g);
        return g;
    }

    /**
     * Join first available game game controller interface.
     *
     * @param nickname the nickname
     * @param notifier the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized GameControllerInterface joinFirstAvailableGame(String nickname, NotifierInterface notifier) throws RemoteException{
        List<GameController> gameList = getRunningGames();
        for (GameController i : gameList){
            int playersEqualIn = i.getAllPlayer().stream().filter(p -> p.getNickname().equals(nickname)).toList().size();
            int playersEqualInDisconnected = i.getAllDisconnectedPlayer().keySet().stream().filter(p -> p.getNickname().equals(nickname)).toList().size();
            int playersSize = i.getAllPlayer().size();
            int maxSize = i.getMaxNumberOfPlayer();
            if(playersSize < maxSize && playersEqualIn == 0 && playersEqualInDisconnected == 0){
                i.addMyselfAsListener(nickname, notifier);
                i.addPlayer(nickname);
                return i;
            }
        }
        return null;
    }

    /**
     * Join game by id game controller interface.
     *
     * @param nickname    the nickname
     * @param idToConnect the id to connect
     * @param notifier    the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized GameControllerInterface joinGameByID(String nickname, int idToConnect, NotifierInterface notifier) throws RemoteException{
        List<GameController> gameList = getRunningGames();

        for (GameController i : gameList){
            int playersEqualIn = i.getAllPlayer().stream().filter(p -> p.getNickname().equals(nickname)).toList().size();
            int playersEqualInDisconnected = i.getAllDisconnectedPlayer().keySet().stream().filter(p -> p.getNickname().equals(nickname)).toList().size();
            int playersSize = i.getAllPlayer().size();
            int maxSize = i.getMaxNumberOfPlayer();
            boolean gameIdEqual = (i.getGameID() == idToConnect);
            if(playersSize < maxSize && playersEqualIn==0 && gameIdEqual && playersEqualInDisconnected == 0){
                i.addMyselfAsListener(nickname, notifier);
                i.addPlayer(nickname);
                return  i;
            }
        }
        return null;
    }


    /**
     * Reconnect game controller interface.
     *
     * @param nickname      the nickname
     * @param idToReconnect the id to reconnect
     * @param notifier      the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized GameControllerInterface reconnect(String nickname, int idToReconnect, NotifierInterface notifier) throws RemoteException {
        Player player;
        List<GameController> ris = runningGames.stream().filter(gc -> (gc.getGameID() == idToReconnect)).toList();
        if(!ris.isEmpty()){
            player = ris.getFirst().getAllDisconnectedPlayer().keySet().stream().filter(p -> p.getNickname().equals(nickname)).findFirst().orElse(null);
            if(player!=null){
                ris.getFirst().addMyselfAsListener(nickname, notifier);
                ris.getFirst().reconnectPlayer(nickname);
                return ris.getFirst();
            }
        }
        return null;
    }

    /**
     * Leave game controller interface.
     *
     * @param nickname       the nickname
     * @param idToDisconnect the id to disconnect
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public synchronized GameControllerInterface leaveGame(String nickname, int idToDisconnect) throws RemoteException {
        Player p;
        List<GameController> ris = runningGames.stream().filter(x -> (x.getGameID() == idToDisconnect)).toList();
        if(!ris.isEmpty()){
            p = ris.getFirst().getAllPlayer().stream().filter(pp -> pp.getNickname().equals(nickname)).findFirst().orElse(null);
            if(p!=null){
                ris.getFirst().killMe(nickname);
                //ris.getFirst().removeMyselfAsListener(nickname);
                ris.getFirst().disconnectPlayer(nickname,true,ris.getFirst().getCurrentPlayer());
                return ris.getFirst();
            }
        }
        return null;
    }

    /**
     * Remove game.
     *
     * @param g the g
     */
    public void removeGame(GameController g) {
        runningGames.remove(g);
    }

    /**
     * Clear singleton.
     */
    @Override
    public synchronized void clearSingleton() {
        runningGames.clear();
    }

}
