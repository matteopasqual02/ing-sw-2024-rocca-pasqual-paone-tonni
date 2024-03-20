package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import java.util.*;

public class MainController {
    private static MainController instance = null;

    private List<GameController> runningGames;

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

    public void addGame(){
        runningGames.add(new GameController());
    }

    //...
}
