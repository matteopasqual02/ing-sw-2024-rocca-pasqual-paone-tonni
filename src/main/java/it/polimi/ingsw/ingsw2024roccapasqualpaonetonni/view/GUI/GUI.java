package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ViewUpdate;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GUI extends UnicastRemoteObject implements ViewUpdate {
    private transient GUIApplication application;
    /**
     * this method is used to pass a runnable function to the UI thread that will handle the changes to the gui.
     */
    public GUI(GUIApplication application) throws RemoteException {
        super();
        this.application = application;
    }
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
        runLater(()->application.show_createdGame(gameID));
    }

    @Override
    public void show_youJoinedGame(int gameID) {
        runLater(()->application.show_youJoinedGame(gameID));
    }

    @Override
    public void show_noAvailableGame() {
        runLater(()->application.show_noAvailableGame());
    }

    @Override
    public void show_addedNewPlayer(String pNickname) {

    }

    @Override
    public void show_areYouReady() {

    }

    @Override
    public void myRunningTurnDrawCard() {

    }

    @Override
    public void notMyTurn() {

    }

    @Override
    public void displayChat(String s) {

    }

    @Override
    public void notMyTurnChat() {

    }

    @Override
    public void show_status(String s) {

    }

    @Override
    public void winners(List<Player> list, String nick) {

    }

    @Override
    public void myRunningTurnPlaceCard() {

    }

    @Override
    public void invalidMessage(String s) {

    }

    @Override
    public void myRunningTurnChooseObjective() {

    }

    @Override
    public void myRunningTurnPlaceStarting() {

    }
}
