package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.View;
import javafx.scene.control.skin.TableHeaderRow;

public class GameController implements Runnable{
    private Game model;
    private View view;

    public GameController() {
        model = new Game();
        new Thread(this).start();
    }

    public void run() {
        while (!Thread.interrupted()) {

        }
    }
}
