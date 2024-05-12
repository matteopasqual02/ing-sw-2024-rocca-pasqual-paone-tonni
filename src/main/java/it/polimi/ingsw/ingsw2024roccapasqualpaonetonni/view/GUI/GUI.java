package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ViewUpdate;
import javafx.application.Platform;

public class GUI implements ViewUpdate {
    private GUIApplication application;
    /**
     * this method is used to pass a runnable function to the UI thread that will handle the changes to the gui.
     */
    public void runLater(Runnable runnable){
        Platform.runLater(runnable);
    }
    @Override
    public void joinLobby() {
        runLater(()->application.joinLobby());
    }

    @Override
    public void show_All(GameImmutable gameImmutable, String nickname) {

    }

    @Override
    public void show_maxNumPlayersSet(int max) {

    }

    @Override
    public void show_createdGame(int gameID) {

    }

    @Override
    public void show_youJoinedGame(int gameID) {

    }

    @Override
    public void show_noAvailableGame() {

    }

    @Override
    public void show_addedNewPlayer(String pNickname) {

    }

    @Override
    public void show_areYouReady() {

    }

    @Override
    public void preparation() {

    }

    @Override
    public void myRunningTurn() {

    }

    @Override
    public void notMyTurn() {

    }

    @Override
    public void invalidMessage() {

    }

    @Override
    public void invalidMessage(String s) {

    }
}
