package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

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

    public GameController CreateGameController(){
        GameController g=new GameController();
        runningGames.add(g);
        return g;
    }

    public List<GameController> getRunningGames(){return runningGames;}


}
