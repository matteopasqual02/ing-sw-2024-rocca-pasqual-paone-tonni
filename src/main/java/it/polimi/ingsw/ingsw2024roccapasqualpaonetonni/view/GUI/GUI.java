package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumUpdates;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ViewUpdate;
import javafx.application.Application;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class GUI extends UnicastRemoteObject implements ViewUpdate {
    private final transient GUIApplication application;
    /**
     * this method is used to pass a runnable function to the UI thread that will handle the changes to the gui.
     */
    public GUI(Client client) throws RemoteException {
        super();
        new Thread(() -> Application.launch(GUIApplication.class)).start();

        while (GUIApplication.getInstance() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.application = GUIApplication.getInstance();
        application.setClient(client);
    }
    public void runLater(Runnable runnable){
        Platform.runLater(runnable);
    }

    @Override
    public void joinLobby() {
        runLater(application::joinLobby);
    }

    /**
     * Show all.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
    @Override
    public void show_All(GameImmutable gameImmutable, String nickname, EnumUpdates type, boolean myTurn, String playerChangedNickname) {
        runLater((()->{
            switch (type){
                case ALL -> application.show_all(gameImmutable,nickname,myTurn);
                case START -> application.show_startCard(gameImmutable,nickname,myTurn,playerChangedNickname);
                case BOARD -> application.show_board(gameImmutable,nickname,myTurn,playerChangedNickname);
                case OBJECTIVE -> application.show_objective(gameImmutable,nickname,myTurn);
                case BOARDDECK -> application.show_boardDeck(gameImmutable, nickname, myTurn,playerChangedNickname);
            }

        }));
    }

    @Override
    public void show_board(int cardID, String playerChangedNickname) {
        //runLater(()->application.show_otherPlayerBoard(cardID,playerChangedNickname));
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
        runLater(application::show_noAvailableGame);
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
        runLater(application::show_areYouReady);
    }

    /**
     * My running turn draw card.
     */
    @Override
    public void myRunningTurnDrawCard() {
        runLater(application::myRunningTurnDrawCard);
    }

    /**
     * Not my turn.
     */
    @Override
    public void notMyTurn() {
        runLater(application::notMyTurn);
    }

    /**
     * My turn.
     */
    @Override
    public void myTurn() {
        runLater(application::myTurn);
    }

    /**
     *
     * @param gameImmutable
     * @param nickname of the owner of the board to update
     */
    @Override
    public void updateOtherBoard(GameImmutable gameImmutable, String nickname) {
        //ConsolePrinter.consolePrinter("GUI updating board for " + nickname);
        runLater(() -> application.updateOtherBoard(gameImmutable, nickname));
    }

    /**
     *
     * @param gameImmutable
     * @param nickname nickname of who changed its hand
     */
    @Override
    public void updateBackHand(GameImmutable gameImmutable, String nickname) {
        runLater(() -> application.updateBackHand(gameImmutable, nickname));
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
        runLater(application::chatBeforeStart);
    }

    /**
     * Show status.
     *
     * @param s the s
     */
    @Override
    public void show_status(String s) {
        runLater(()->application.show_status(s));
    }

    @Override
    public void show_statusLast(String string) {
        runLater(()->application.show_statusLast(string));
    }

    /**
     * Winners.
     *
     * @param list the list
     * @param nick the nick
     */
    @Override
    public void winners(List<Player> list, String nick) {
        runLater(()->application.winner(list));
    }

    @Override
    public void show_generic(String msg) {
        runLater(()->application.show_generic(msg));
    }

    /**
     * My running turn place card.
     */
    @Override
    public void myRunningTurnPlaceCard() {
        runLater(application::myRunningTurnPlaceCard);
    }

    /**
     * Invalid message.
     *
     * @param s the s
     */
    @Override
    public void invalidMessage(String s, boolean myTurn) {
        runLater(()->application.invalidAction(s, myTurn));
    }

    /**
     * My running turn choose objective.
     */
    @Override
    public void myRunningTurnChooseObjective() {
        runLater(application::myRunningTurnChoseObjective);
    }

    /**
     * My running turn place starting.
     */
    @Override
    public void myRunningTurnPlaceStarting() {
        runLater(application::myRunningTurnPlaceStarting);
    }
}
