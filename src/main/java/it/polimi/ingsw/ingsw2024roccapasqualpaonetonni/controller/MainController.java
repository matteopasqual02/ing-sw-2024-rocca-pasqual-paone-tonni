package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;

import java.util.*;

public class MainController {
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

    public synchronized GameControllerInterface createGameController(String nickname){
        GameController g=new GameController();
        g.addPlayer(nickname);
        runningGames.add(g);
        return (GameControllerInterface) g;
    }

    private synchronized List<GameController> getRunningGames(){return runningGames;}
    public synchronized GameControllerInterface joinFirstAvailableGame(String nickname){
        List<GameController> gameList = getRunningGames();

        for (GameController i : gameList){
            if(i.getAllPlayer().size()<i.getNumberOfPlayer()){
                i.addPlayer(nickname);
                return (GameControllerInterface) i;
            }
        }

        return (GameControllerInterface) createGameController(nickname);
    }



}
