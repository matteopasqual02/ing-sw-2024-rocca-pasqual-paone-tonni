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

    /**
     * Show all.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
    @Override
    public void show_All(GameImmutable gameImmutable, String nickname) {runLater((()->application.show_all(gameImmutable,nickname)));
    }

    /**
     * Show max num players set.
     *
     * @param max the max
     */
    @Override
    public void show_maxNumPlayersSet(int max) {

    }

    /**
     * Show created game.
     *
     * @param gameID the game id
     */
    @Override
    public void show_createdGame(int gameID) {
        runLater(()->application.show_createdGame(gameID));
    }

    /**
     * Show you joined game.
     *
     * @param gameID the game id
     */
    @Override
    public void show_youJoinedGame(int gameID) {
        runLater(()->application.show_youJoinedGame(gameID));
    }

    /**
     * Show no available game.
     */
    @Override
    public void show_noAvailableGame() {
        runLater(()->application.show_noAvailableGame());
    }

    /**
     * Show added new player.
     *
     * @param pNickname the p nickname
     */
    @Override
    public void show_addedNewPlayer(String pNickname) {
        runLater(()->application.show_addedNewPlayer(pNickname));
    }

    /**
     * Show are you ready.
     */
    @Override
    public void show_areYouReady() {
        runLater(()->application.show_areYouReady());
    }

    /**
     * My running turn draw card.
     */
    @Override
    public void myRunningTurnDrawCard() {

    }

    /**
     * Not my turn.
     */
    @Override
    public void notMyTurn() {

    }

    /**
     * Display chat.
     *
     * @param s the s
     */
    @Override
    public void displayChat(String s) {
        runLater(() -> application.displayChat(s));
    }

    @Override
    public void displayChat(String s, String type) {
        runLater(() -> application.displayChat(s, type));
    }

    /**
     * Not my turn chat.
     */
    @Override
    public void notMyTurnChat() {

    }

    /**
     * Show status.
     *
     * @param s the s
     */
    @Override
    public void show_status(String s) {

    }

    @Override
    public void show_statusLast(String string) {

    }

    /**
     * Winners.
     *
     * @param list the list
     * @param nick the nick
     */
    @Override
    public void winners(List<Player> list, String nick) {

    }

    @Override
    public void show_generic(String msg) {

    }

    /**
     * My running turn place card.
     */
    @Override
    public void myRunningTurnPlaceCard() {

    }

    /**
     * Invalid message.
     *
     * @param s the s
     */
    @Override
    public void invalidMessage(String s) {

    }

    /**
     * My running turn choose objective.
     */
    @Override
    public void myRunningTurnChooseObjective() {

    }

    /**
     * My running turn place starting.
     */
    @Override
    public void myRunningTurnPlaceStarting() {
        runLater(()->application.myRunningTurnPlaceStarting());
    }
}
