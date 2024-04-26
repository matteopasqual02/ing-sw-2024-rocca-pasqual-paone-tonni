package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;

import java.rmi.RemoteException;
import java.util.*;

public class MainController implements MainControllerInterface {
    private static MainController instance = null;

    private final List<GameController> runningGames;

    private MainController() {
        runningGames = new ArrayList<>();
    }

    //singleton
    public synchronized static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    @Override
    public synchronized List<GameController> getRunningGames(){
        return runningGames;
    }

    @Override
    public synchronized GameControllerInterface createGameController(String nickname, int numMaxOfPlayer)throws RemoteException {
        GameController g=new GameController(getRunningGames().size()+1);
        g.setMaxNumberOfPlayer(numMaxOfPlayer);
        g.addPlayer(nickname);
        runningGames.add(g);
        return g;
    }

    @Override
    public synchronized GameControllerInterface joinFirstAvailableGame(String nickname)throws RemoteException{
        List<GameController> gameList = getRunningGames();

        for (GameController i : gameList){
            int playersEqualIn = i.getAllPlayer().stream().filter(p -> p.getNickname().equals(nickname)).toList().size();
            int playersSize = i.getAllPlayer().size();
            int maxSize = i.getMaxNumberOfPlayer();
            if(playersSize < maxSize && playersEqualIn==0){
                i.addPlayer(nickname);
                return  i;
            }
        }

        return null;
    }

    @Override
    public GameControllerInterface joinGameByID(String nickname, int idToConnect){
        List<GameController> gameList = getRunningGames();

        for (GameController i : gameList){
            int playersEqualIn = i.getAllPlayer().stream().filter(p -> p.getNickname().equals(nickname)).toList().size();
            int playersSize = i.getAllPlayer().size();
            int maxSize = i.getMaxNumberOfPlayer();
            boolean gameIdEqual = (i.getID() == idToConnect);
            if(playersSize < maxSize && playersEqualIn==0 && gameIdEqual){
                i.addPlayer(nickname);
                return  i;
            }
        }

        return null;
    }


    @Override
    public GameControllerInterface reconnect(String nickname, int idToReconnect) throws RemoteException{
        Player player;
        List<GameController> ris = runningGames.stream().filter(gc -> (gc.getID() == idToReconnect)).toList();
        boolean gameWaiting = ris.getFirst().getGameStatus().equals(GameStatus.WAITING_RECONNECTION);
        if(!ris.isEmpty() && gameWaiting){
            player = ris.getFirst().getAllPlayer().stream().filter(p -> p.getNickname().equals(nickname)).findFirst().orElse(null);
            if(player!=null){
                ris.getFirst().reconnectPlayer(nickname);
                return ris.getFirst();
            }
        }
        return null;
    }

    @Override
    public GameControllerInterface leaveGame(String nickname, int idToDisconnect) throws RemoteException {
        Player p;
        List<GameController> ris = runningGames.stream().filter(x -> (x.getID() == idToDisconnect)).toList();
        if(!ris.isEmpty()){
            p = ris.getFirst().getAllPlayer().stream().filter(pp -> pp.getNickname().equals(nickname)).findFirst().orElse(null);
            if(p!=null){
                ris.getFirst().disconnectPlayer(nickname);
                return ris.getFirst();
            }
        }
        return null;
    }

    @Override
    public void clearSingleton() {
        runningGames.clear();

    }

}
