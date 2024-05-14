package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIServerStub;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main.MainStaticMethod;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client.SocketClient;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI.TUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.events.ScannerGUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.events.ScannerTUI;
import javafx.application.Application;

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
    private transient final PingPongThreadClient pongThread = new PingPongThreadClient();

    public Client(EnumConnectionType connectionType) throws IOException {
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

        view = new TUI();
        new ScannerTUI(this);
        MainStaticMethod.clearCMD();
        view.joinLobby();

        //this.pongThread.start();
    }

    public Client(GUIApplication application, EnumConnectionType connectionType) throws IOException {
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
        view = new GUI(application);
        new ScannerGUI();
        MainStaticMethod.clearCMD();
        view.joinLobby();

        //this.pongThread.start();
    }


    @Override
    public void run() {

    }

    public synchronized void receiveInput(String input) throws IOException, NotBoundException {
        if(input == null){
            view.invalidMessage("empty input");
            return;
        }
        String[] parole = input.split(" ");

        switch (parole[0]) {
            case "/new" -> {
                if(state==null && parole.length==3){
                    try{
                        int maxNumPlayers= Integer.parseInt(parole[1]);
                        if (maxNumPlayers<2 || maxNumPlayers>4){
                            view.invalidMessage("Invalid number of players");
                            return;
                        }
                        myNickname = parole[2];
                        server.createGame(myNickname, maxNumPlayers, this);
                    }
                    catch (IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid number format");
                    }

                }
                else {
                    view.invalidMessage("Command not complete");
                }
            }
            case "/join" -> {
                if(state==null && parole.length==2){
                    try{
                        myNickname = parole[1];
                        server.joinFirstAvailable(myNickname, this);
                    }
                    catch(IndexOutOfBoundsException e){
                        view.invalidMessage("Invalid format");
                    }

                }
                else {
                    view.invalidMessage("Command not complete");
                }
            }
            case "/joinById","/joinbyid" -> {
                if(state==null && parole.length==3){
                    try{
                        myNickname = parole[1];
                        int gameId= Integer.parseInt(parole[2]);
                        server.joinGameByID(myNickname,gameId,this);
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid number format");
                    }
                }
                else {
                    view.invalidMessage("Command not complete");
                }
            }
            case "/reconnect" -> {
                if(state==null && parole.length==3){
                    try{
                        myNickname = parole[1];
                        int gameId= Integer.parseInt(parole[2]);
                        server.reconnect(myNickname,gameId,this);
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid number format");
                    }

                }
                else {
                    view.invalidMessage("Command not complete");
                }
            }
            case "Y","y" -> {
                if(state==null){
                    view.notMyTurnChat();
                    server.ready(myNickname);
                }
                else {
                    view.invalidMessage("You cannot be ready now (or you are already ready)");
                }
            }
            case "/addStarting","/addstarting" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    try{
                        if(parole.length==1){
                            server.addStartingCard(myNickname, false);
                            return;
                        }
                        server.addStartingCard(myNickname, Objects.equals(parole[1], "true"));
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Wrong format");
                    }

                }
                else {
                    view.invalidMessage("Not my turn (or game is waiting)");
                }
            }
            case "/choseGoal","/chosegoal" -> {
                if(state==GameStatus.RUNNING && parole[1]!=null && myTurn!=null && myTurn && parole.length==2){
                    Player me = currentImmutable.getPlayers().stream().filter(player -> myNickname.equals(player.getNickname())).toList().getFirst();
                    if(me.getGoal()!=null){
                        view.invalidMessage("Goal already chosen");
                    }
                    try {
                        int pos = Integer.parseInt(parole[1]);
                        server.choosePlayerGoal(myNickname,pos-1);
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid Format");
                    }
                }
                else {
                    view.invalidMessage("Command not complete");
                }
            }
            case "/addCard", "/addcard" -> {
                if((state==GameStatus.RUNNING || state==GameStatus.WAITING_LAST_TURN || state==GameStatus.LAST_TURN)
                        && myTurn!=null && parole.length>=4){
                    try{
                        Player me = currentImmutable.getPlayers().stream().filter(player -> myNickname.equals(player.getNickname())).toList().getFirst();
                        if(me==null){return;}
                        int card1 = Integer.parseInt(parole[1]);
                        PlayingCard c1 = me.getHand().get(card1);
                        int card2 = Integer.parseInt(parole[2]);
                        PlayingCard[][] board = me.getBoard().getBoardMatrix();
                        int pos = Integer.parseInt(parole[3]);

                        for (PlayingCard[] playingCards : board) {
                            for (PlayingCard playingCard : playingCards) {
                                if (playingCard != null && playingCard.getIdCard() == card2){
                                    if(parole.length==4){
                                        server.addCard(myNickname, c1, playingCard, pos , false);
                                        return;
                                    }
                                    server.addCard(myNickname, c1, playingCard, pos , Objects.equals(parole[4], "true"));
                                    return;
                                }
                            }
                        }
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid number format");
                    }
                }
                else {
                    view.invalidMessage("Command not complete or not your turn");
                }
            }
            case "/drawGold","/drawgold" -> {
                if((state==GameStatus.RUNNING || state==GameStatus.WAITING_LAST_TURN ) && myTurn!=null && myTurn){
                    server.drawGoldFromDeck(myNickname);
                }
                else {
                    view.invalidMessage("Not your Turn or last phase");
                }
            }
            case "/drawResources","/drawresources"  -> {
                if((state==GameStatus.RUNNING || state==GameStatus.WAITING_LAST_TURN )&& myTurn!=null && myTurn){
                    server.drawResourceFromDeck(myNickname);
                }
                else {
                    view.invalidMessage("Not your Turn or last phase");
                }
            }
            case "/drawBoard","/drawboard" -> {
                if(state==GameStatus.RUNNING && myTurn!=null && parole.length==2){
                    try{
                        int pos = Integer.parseInt(parole[1]);
                        server.drawFromBoard(myNickname,pos);
                    }catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid number format");
                    }
                }
                else {
                    view.invalidMessage("Not your Turn or last phase or command not complete");
                }
            }
            case "/chat" -> {
                if(state!=GameStatus.WAITING_RECONNECTION){
                    try{
                        StringBuilder builder = new StringBuilder();
                        for(int i=1; i<parole.length; i++){
                            builder.append(parole[i]).append(" ");
                        }
                        String msg = builder.toString();
                        server.sendMessage(msg,myNickname);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage("Invalid format");
                    }
                }
                else {
                    view.invalidMessage("The game is waiting");
                }
            }
            case "/chatPrivate","/chatprivate" -> {
                if(state!=GameStatus.WAITING_RECONNECTION && parole.length>=2){
                    try{
                        StringBuilder builder = new StringBuilder();
                        for(int i=2; i<parole.length; i++){
                            builder.append(parole[i]).append(" ");
                        }
                        String msg = builder.toString();
                        server.sendPrivateMessage(msg,myNickname,parole[1]);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage("Invalid format");
                    }
                }
                else {
                    view.invalidMessage("The game is waiting or receiver missing");
                }
            }
            case "/seeChat", "/seechat" -> {
                if(state!=GameStatus.WAITING_RECONNECTION){
                    server.getPublicChatLog(myNickname);
                }
                else {
                    view.invalidMessage("The game is waiting");
                }
            }
            case "/seeChatPrivate" ,"/seechatprivate"-> {
                if(state!=GameStatus.WAITING_RECONNECTION && parole.length==2){
                    try{
                        server.getPrivateChatLog(myNickname, parole[1]);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage("Invalid format");
                    }
                }
                else {
                    view.invalidMessage("The game is waiting or private chatter missing");
                }
            }
            case "/leave" -> {
                server.leave(myNickname,myGameId,this);
                view.joinLobby();
            }
            case null, default -> view.invalidMessage("invalid command");

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
        myTurn = myNickname.equals(currentImmutable.getPlayers().peek().getNickname());
        if(myTurn){
            view.myRunningTurnPlaceStarting();
        }
        else{
            view.notMyTurn();
        }
    }
    @Override
    public void nextTurn(String nickname) {
        myTurn = myNickname.equals(nickname);
        if(currentImmutable==null){return;}
        if(myTurn){
            if(currentImmutable.getPlayers().stream().filter(player -> nickname.equals(player.getNickname())).toList().getFirst().getGoal()==null){
                view.myRunningTurnPlaceStarting();
            }
            else {
                view.myRunningTurnPlaceCard();
            }
        }
        else {
            view.notMyTurn();
        }
    }
    @Override
    public void startAdded(Player p) {
        currentImmutable.refreshPlayer(p);
        view.show_All(currentImmutable,myNickname);
        if(myTurn){
            view.myRunningTurnChooseObjective();
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
            view.myRunningTurnDrawCard();
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
            view.myRunningTurnPlaceCard();
        }
        else{
            view.notMyTurn();
        }
    }
    @Override
    public void statusSet(GameStatus status) {
        state=status;
    }
    @Override
    public void resourceDrawn(Player p, DrawableDeck d) {
        if(currentImmutable!=null){
            currentImmutable.refreshPlayer(p);
            currentImmutable.setDrawableDeck(d);
            view.show_All(currentImmutable,myNickname);
        }

    }
    @Override
    public void goldDrawn(Player p, DrawableDeck d) {
        if(currentImmutable!=null){
            currentImmutable.refreshPlayer(p);
            currentImmutable.setDrawableDeck(d);
            view.show_All(currentImmutable,myNickname);
        }
    }
    @Override
    public void drewFromBoard(Player p, BoardDeck b, DrawableDeck d) {
        if(currentImmutable!=null){
            currentImmutable.refreshPlayer(p);
            currentImmutable.setDrawableDeck(d);
            currentImmutable.setBoardDeck(b);
            view.show_All(currentImmutable,myNickname);
        }
    }
    @Override
    public void genericError(String s) throws RemoteException {
        view.invalidMessage(s);
    }

    @Override
    public void winners(List<Player> list) {
        ConsolePrinter.consolePrinter(list.stream().map(Player::getNickname).toString());
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
    public void statusSetToLastStatus(GameStatus status) {

    }
    @Override
    public void lastStatusReset() {

    }

    //--------------------------CHAT
    @Override
    public void newMessage(Message m) throws RemoteException {
        String message = String.format("[%s] %s",m.getSender(),m.getText());
        view.displayChat(message);
    }
    @Override
    public void newPrivateMessage(PrivateMessage m) throws RemoteException {
        String message = String.format("[%s] privately sent you: %s",m.getSender(),m.getText());
        view.displayChat(message);
    }
    @Override
    public void publicChatLog(List<Message> allMessages) throws RemoteException {
        if(allMessages!=null){
            StringBuilder chat = new StringBuilder();
            for(Message m: allMessages){
                chat.append(String.format("[%s] %s\n",m.getSender(),m.getText()));
            }
            view.displayChat(chat.toString());
        }
        else {
            view.displayChat("There is no public chat");
        }
    }
    @Override
    public void privateChatLog(String otherName, List<PrivateMessage> privateChat) throws RemoteException {
        if(privateChat!=null){
            StringBuilder chat = new StringBuilder();
            for(PrivateMessage m: privateChat){
                chat.append(String.format("[%s] %s\n",m.getSender(),m.getText()));
            }
            view.displayChat(chat.toString());
        }
        else {
            view.displayChat("There is no private chat with: " + otherName);
        }
    }

    //--------------------------PIN PONG
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
