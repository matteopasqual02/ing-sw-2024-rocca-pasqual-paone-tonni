package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIServerStub;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main.MainClient;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main.MainStaticMethod;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client.SocketClient;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI.TUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.events.ScannerGUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.events.ScannerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.events.ScannerTUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

import static org.fusesource.jansi.Ansi.ansi;

public class Client extends UnicastRemoteObject implements GameListener, Runnable {
    private ServerInterface server;
    private ViewUpdate view;
    private int myGameId;
    private String myNickname;
    private GameStatus state = null;
    private Boolean myTurn = false;
    private GameImmutable currentImmutable;
    private ScannerInterface scanner;

    public Client(EnumConnectionType connectionType, EnumViewType viewType) throws IOException {
        this.myGameId = 0;
        this.myNickname = null;
        this.server = null;
        this.currentImmutable=null;

        switch (connectionType){
            case RMI -> {
                server = new RMIServerStub();
                new Thread(this).start();
            }
            case SOCKET -> {
                server = new SocketClient(this);
                new Thread(this).start();
            }
        }
        switch (viewType){
            case GUI ->{
                view = new GUI();
                scanner = new ScannerGUI();
            }
            case TUI -> {
                view = new TUI();
                scanner = new ScannerTUI(this);
            }
        }
    }

    @Override
    public void run() {
        MainStaticMethod.clearCMD();
        view.joinLobby(this);
        while(!Thread.interrupted()){
            if(state !=null){
                switch (state) {
                    case PREPARATION ->{
                        view.preparation();
                    }
                    case RUNNING -> {
                        if(myTurn){
                            view.myRunningTurn(this);
                        }
                        else{
                            //view.notMyTurn(this);
                        }
                    }
                    case WAITING_LAST_TURN -> {

                    }
                    case LAST_TURN -> {

                    }
                    case ENDED -> {

                    }
                    case WAITING_RECONNECTION -> {

                    }
                }
            }
        }

    }
    public void recieveInput(String input) throws IOException {
        String[] parole = input.split(" ");

        switch (parole[0]) {
            case "/addStarting" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    server.addStartingCard(myNickname, Objects.equals(parole[1], "true"));
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/addCard" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    int card1 = Integer.parseInt(parole[1]);
                    PlayingCard c1 = currentImmutable.getPlayers().peek().getHand().get(card1);
                    int card2 = Integer.parseInt(parole[2]);
                    PlayingCard[][] board = currentImmutable.getPlayers().peek().getBoard().getBoard();
                    int pos = Integer.parseInt(parole[3]);

                    for (PlayingCard[] playingCards : board) {
                        for (PlayingCard playingCard : playingCards) {
                            if (playingCard != null && playingCard.getIdCard() == card2) {
                                server.addCard(myNickname, c1, playingCard, pos , Objects.equals(parole[1], "true"));
                                break;
                            }
                        }
                    }
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/drawGold" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){

                }
                else {
                    view.invalidMessage();
                }
            }
            case null, default -> view.invalidMessage();

        }


    }

    //------------------------------------- SET GET ------------------------------------------------------------------------------

    public void setMyNickname(String myNickname) {
        this.myNickname = myNickname;
    }
    @Override
    public String getNickname() throws RemoteException {
        return myNickname;
    }

    public ServerInterface getServerInterface(){
        return server;
    }

    //-------------------------------------OVERRIDE SECTION -----------------------------------------------------------------------

    @Override
    public void maxNumPlayersSet(int max) {
        view.show_maxNumPlayersSet(max);
    }
    @Override
    public void createdGame(int gameId) {
        myGameId = gameId;
        view.show_createdGame(gameId);
    }
    @Override
    public void youJoinedGame(int gameId) {
        myGameId = gameId;
        view.show_youJoinedGame(gameId);
    }
    @Override
    public void noAvailableGame() {
        view.show_noAvailableGame();
        view.joinLobby(this);
    }
    @Override
    public void addedNewPlayer(String pNickname) {
        view.show_addedNewPlayer(pNickname);
    }
    @Override
    public void areYouReady() {
        view.show_areYouReady();
        try {
            server.ready(myNickname);
        } catch (NotBoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void allGame(GameImmutable gameImmutable) {
        currentImmutable=gameImmutable;
        view.show_All(gameImmutable,myNickname);
    }

    @Override
    public void playerReady(Player p) {

    }

    @Override
    public void fullGame() {

    }

    @Override
    public void nameAlreadyInGame() {

    }

    @Override
    public void playerRemoved(String p) {

    }

    @Override
    public void nextTurn(String nickname) {
        if(myNickname.equals(nickname)){
            myTurn = true;
        }
        else{
            myTurn = false;
        }
    }

    @Override
    public void lastTurn() {

    }

    @Override
    public void reconnectedPlayer(String nickname) {

    }

    @Override
    public void reconnectionImpossible(String nickname) {

    }

    @Override
    public void disconnectedPlayer(String nickname) {

    }

    @Override
    public void disconnectionImpossible(String nickname) {

    }

    @Override
    public void statusSet(GameStatus status) {
        state=status;
    }

    @Override
    public void statusSetToLastStatus(GameStatus status) {

    }

    @Override
    public void lastStatusReset() {

    }

    @Override
    public void playerIsReady(Player p) {

    }

    @Override
    public void firstPlayerSet(String nickname) {
        if(myNickname.equals(nickname)){
            myTurn = true;
        }
        else{
            myTurn = false;
        }
    }

    @Override
    public void drawableDeckSet(DrawableDeck d) {

    }

    @Override
    public void boardDeckSet(BoardDeck bd) {

    }

    @Override
    public void newMessage(Message m) throws RemoteException {
        String message = String.format("[%s] %s",m.getSender(),m.getText());
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void chatUpdate(List<Message> allMessages) throws RemoteException {

    }

    /**
     * this method recieves a private message and prints the sender and the content on both players' displays
     * @param m
     * @throws RemoteException
     */
    @Override
    public void newPrivateMessage(PrivateMessage m) throws RemoteException {
        String message = String.format("[%s] privately sent you: %s",m.getSender(),m.getText());
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void publicChatLog(List<Message> allMessages) throws RemoteException {
        if(allMessages!=null){
            StringBuilder chat = new StringBuilder();
            for(Message m: allMessages){
                chat.append(String.format("[%s] %s\n",m.getSender(),m.getText()));
            }
            ConsolePrinter.consolePrinter(chat);
        }
        else {
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("There is no public chat"));
        }
    }

    @Override
    public void privateChatLog(String otherName, List<PrivateMessage> privateChat) throws RemoteException {
        if(privateChat!=null){
            StringBuilder chat = new StringBuilder();
            for(PrivateMessage m: privateChat){
                chat.append(String.format("[%s] %s\n",m.getSender(),m.getText()));
            }
            ConsolePrinter.consolePrinter(chat);
        }
        else {
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("You have no private chat with this player"));
        }
    }


    @Override
    public void startAdded(PlayerBoard board, Player p) {

    }

    @Override
    public void cardAdded(PlayerBoard board, Player p) {

    }

    @Override
    public void choseInvalidPlace(Player p) {

    }

    @Override
    public void conditionsNotMet(Player p) {

    }

    @Override
    public void startingCardDrew(StartingCard start, Player p) {

    }

    @Override
    public void drewPersonalGoals(ObjectiveCard[] goals, Player p) {

    }

    @Override
    public void personalGoalChosen(ObjectiveCard goal, Player p) {

    }

    @Override
    public void cardNotInHand(PlayingCard card, Player p) {

    }

    @Override
    public void resourceDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void goldDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void drewFromBoard(PlayingCard card, Player p) {

    }

    @Override
    public void playerIsConnected(Player p) {

    }

    @Override
    public void pointsIncreased(int points, Player p) {

    }

    @Override
    public void seedCountUpdated(int[] seedCount, Player p) {

    }

    @Override
    public void cardRemovedFromHand(PlayingCard card, Player p) {

    }

}
