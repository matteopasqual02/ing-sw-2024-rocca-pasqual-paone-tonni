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
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main.MainStaticMethod;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client.SocketClient;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI.TUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.events.ScannerGUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.events.ScannerTUI;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
    private transient final PingPongThreadClient pongThread = new PingPongThreadClient();

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
                new ScannerGUI();
            }
            case TUI -> {
                view = new TUI();
                new ScannerTUI(this);
            }
        }
        MainStaticMethod.clearCMD();
        view.joinLobby();

        //this.pongThread.start();
    }



    @Override
    public void run() {

    }

    public synchronized void receiveInput(String input) throws IOException, NotBoundException {
        String[] parole = input.split(" ");

        switch (parole[0]) {
            case "/new" -> {
                if(state==null){
                    try{
                        int maxNumPlayers= Integer.parseInt(parole[1]);
                        myNickname = parole[2];
                        server.createGame(myNickname, maxNumPlayers, this);
                    }
                    catch (IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage();
                    }

                }
                else {
                    view.invalidMessage();
                }
            }
            case "/join" -> {
                if(state==null){
                    try{
                        myNickname = parole[1];
                        server.joinFirstAvailable(myNickname, this);
                    }
                    catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }

                }
                else {
                    view.invalidMessage();
                }
            }
            case "/joinById" -> {
                if(state==null){
                    try{
                        myNickname = parole[1];
                        int gameId= Integer.parseInt(parole[2]);
                        server.joinGameByID(myNickname,gameId,this);
                    }
                    catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/reconnect" -> {
                if(state==null){
                    try{
                        myNickname = parole[1];
                        int gameId= Integer.parseInt(parole[2]);
                        server.reconnect(myNickname,gameId,this);
                    }
                    catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }

                }
                else {
                    view.invalidMessage();
                }
            }
            case "Y","y" -> {
                if(state==null){
                    server.ready(myNickname);
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/addStarting" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    try{
                        server.addStartingCard(myNickname, Objects.equals(parole[1], "true"));
                    }
                    catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }

                }
                else {
                    view.invalidMessage();
                }
            }
            case "/choseGoal" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    try {
                        int pos = Integer.parseInt(parole[1]);
                        server.choosePlayerGoal(myNickname,pos-1);
                    }
                    catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/addCard" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    try{
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
                    catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/drawGold" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    server.drawGoldFromDeck(myNickname);
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/drawResources" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    server.drawResourceFromDeck(myNickname);
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/drawBoard" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    try{
                        int pos = Integer.parseInt(parole[1]);
                        server.drawFromBoard(myNickname,pos);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/chat" -> {
                if(state!=GameStatus.WAITING_RECONNECTION){
                    try{
                        server.sendMessage(myNickname,parole[1]);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/chatPrivate" -> {
                if(state!=GameStatus.WAITING_RECONNECTION){
                    try{
                        server.sendPrivateMessage(parole[2],myNickname,parole[1]);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/seeChat" -> {
                if(state!=GameStatus.WAITING_RECONNECTION){
                    server.getPublicChatLog(myNickname);
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/seeChatPrivate" -> {
                if(state!=GameStatus.WAITING_RECONNECTION){
                    try{
                        server.getPrivateChatLog(myNickname, parole[1]);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage();
                    }
                }
                else {
                    view.invalidMessage();
                }
            }
            case "/leave" -> {
                if(state!=GameStatus.WAITING_RECONNECTION){
                    server.leave(myNickname,myGameId,this);
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
        view.joinLobby();
    }
    @Override
    public void addedNewPlayer(String pNickname) {
        view.show_addedNewPlayer(pNickname);
    }
    @Override
    public void areYouReady() {
        view.show_areYouReady();
    }

    @Override
    public void allGame(GameImmutable gameImmutable) {
        currentImmutable=gameImmutable;
        view.show_All(gameImmutable,myNickname);
        if(myTurn){
            view.myRunningTurn();
        }
        else{
            view.notMyTurn();
        }
    }
    @Override
    public void nextTurn(String nickname) {
        myTurn = myNickname.equals(nickname);
    }
    @Override
    public void startAdded(Player p) {
        currentImmutable.refreshPlayer(p);
        view.show_All(currentImmutable,myNickname);
        if(myTurn){
            view.myRunningTurn();
        }
        else{
            view.notMyTurn();
        }
    }

    @Override
    public void cardAdded(Player p) {
        currentImmutable.refreshPlayer(p);
        view.show_All(currentImmutable,myNickname);
        if(myTurn){
            view.myRunningTurn();
        }
        else{
            view.notMyTurn();
        }
    }
    @Override
    public void personalGoalChosen(Player p) {
        currentImmutable.refreshPlayer(p);
        view.show_All(currentImmutable,myNickname);
        if(myTurn){
            view.myRunningTurn();
        }
        else{
            view.notMyTurn();
        }
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
        myTurn = myNickname.equals(nickname);
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
     * this method receives a private message and prints the sender and the content on both players' displays
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
    public void choseInvalidPlace(Player p) {

    }

    @Override
    public void conditionsNotMet(Player p) {

    }

    @Override
    public void cardNotInHand( Player p) {

    }

    @Override
    public void resourceDrawn(Player p) {

    }

    @Override
    public void goldDrawn(Player p) {

    }

    @Override
    public void drewFromBoard( Player p) {

    }

    @Override
    public void cardRemovedFromHand(Player p) throws RemoteException {

    }

    private class PingPongThreadClient extends Thread {
        private boolean pinged = false;
        private final Object  lock = new Object();

        public void pinged() {
            synchronized (lock) {
                pinged = true;
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(20000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!pinged) {
                    ConsolePrinter.consolePrinter("server dead");
                }
                else {
                    synchronized (lock) {
                        pinged = false;
                    }
                }
            }
        }
    }

    @Override
    public void ping() {
        try {
            server.pong(this.myNickname);
            //ConsolePrinter.consolePrinter("pinged");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
