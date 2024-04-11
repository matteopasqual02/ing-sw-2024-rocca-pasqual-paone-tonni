package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

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

    private synchronized List<GameController> getRunningGames(){return runningGames;}

    @Override
    public synchronized GameControllerInterface createGameController(String nickname) {
        GameController g=new GameController(getRunningGames().size()+1);
        g.addPlayer(nickname);
        runningGames.add(g);
        return (GameControllerInterface) g;
    }

    @Override
    public synchronized GameControllerInterface joinFirstAvailableGame(String nickname){
        List<GameController> gameList = getRunningGames();

        for (GameController i : gameList){
            int playersEqualIn = i.getAllPlayer().stream().filter(p -> p.getNickname().equals(nickname)).toList().size();
            if(i.getAllPlayer().size()<i.getNumberOfPlayer() && playersEqualIn==0){
                i.addPlayer(nickname);
                return (GameControllerInterface) i;
            }
        }

        return (GameControllerInterface) createGameController(nickname);
    }

    @Override
    public GameControllerInterface reconnect(String nickname, int idToReconnect) {
        Player p;
        List<GameController> ris = runningGames.stream().filter(x -> (x.getID() == idToReconnect)).toList();
        if(!ris.isEmpty()){
            p = ris.getFirst().getAllPlayer().stream().filter(pp -> pp.getNickname().equals(nickname)).findFirst().orElse(null);
            if(p==null){
                //cannot reconnect
            }
            else {
                ris.getFirst().reconnectPlayer(nickname);
            }
        }
        return null;
    }

    @Override
    public GameControllerInterface leaveGame(String nickname, int idToDisconnect) {
        Player p;
        List<GameController> ris = runningGames.stream().filter(x -> (x.getID() == idToDisconnect)).toList();
        if(!ris.isEmpty()){
            p = ris.getFirst().getAllPlayer().stream().filter(pp -> pp.getNickname().equals(nickname)).findFirst().orElse(null);
            if(p==null){
                //cannot reconnect
            }
            else {
                ris.getFirst().disconnectPlayer(nickname);
            }
        }
        return null;
    }

    //public synchronized GameControllerInterface reconnect(String nickname);
    //public synchronized GameControllerInterface leaveGame(String nickname);



}
