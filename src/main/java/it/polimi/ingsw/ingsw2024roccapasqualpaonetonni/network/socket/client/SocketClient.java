package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages.ServerGenericMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.NotBoundException;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;

public class SocketClient extends Thread implements ServerInterface, Serializable {
    private transient Socket clientSocket;
    private transient ObjectInputStream inputStream;
    private transient ObjectOutputStream outputStream;
    private String nickname;
    private final Client client;
    private GameListener serverRequestHandler;

    //private final SocketNotifier socketNotifier;

    public SocketClient(Client client) throws IOException {
        this.client = client;
        connect();
        this.start();
    }

    public void run(){
        ServerGenericMessage message;
        while (true){
            try{
                message = (ServerGenericMessage) inputStream.readObject();
                message.launchMessage(client);
            } catch (IOException | ClassNotFoundException e) {
                consolePrinter("[SOCKET] Connection Lost!");
                System.exit(-1);
            }
        }

    }

    private void connect(){
        boolean retry = false;
        int attempt = 1;
        int i;

        do{
            try{
                clientSocket = new Socket(DefaultNetworkValues.Server_Ip_address,DefaultNetworkValues.Default_SOCKET_port);
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                inputStream = new ObjectInputStream(clientSocket.getInputStream());
                retry = false;

            }catch (Exception e){
                if (!retry) {
                    ConsolePrinter.consolePrinter("[ERROR] CONNECTING TO SOCKET SERVER" );
                }
                consolePrinter("[#" + attempt + "]Waiting to reconnect to SOCKET Server on port: '" + DefaultNetworkValues.Default_SOCKET_port + "' with name: '" + DefaultNetworkValues.Default_servername_RMI + "'");

                i = 0;
                while (i < DefaultNetworkValues.seconds_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    consolePrinter(". ");
                    i++;
                }
                consolePrinter("\n");

                if (attempt >= DefaultNetworkValues.num_of_attempt) {
                    consolePrinter("[SOCKET] Give up!");
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }
        } while(retry);
    }
    private void disconnect() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }
    private void messageDone() throws IOException {
        outputStream.flush();
        outputStream.reset();
    }

    public ObjectOutputStream getOutputStream() {
        return this.outputStream;
    }

    public ObjectInputStream getInputStream() {
        return this.inputStream;
    }

    @Override
    public void createGame(String name, int maxNumPlayers, GameListener me) throws IOException, NotBoundException {
        outputStream.writeObject(new MainMessageCreateGame(name,maxNumPlayers, me));
        messageDone();
    }

    @Override
    public void joinFirstAvailable(String name, GameListener me) throws IOException, NotBoundException {
        outputStream.writeObject(new MainMessageJoinFirstAvailable(name, me));
        messageDone();
    }

    @Override
    public void joinGameByID(String name, int idGame, GameListener me) throws IOException, NotBoundException {
        outputStream.writeObject(new MainMessageJoinGameById(name, idGame, me));
        messageDone();
    }

    @Override
    public void reconnect(String name, int idGame, GameListener me) throws IOException, NotBoundException {
        outputStream.writeObject(new MainMessageReconnect(name, idGame, me));
        messageDone();
    }

    @Override
    public void leave(String name, int idGame, GameListener me) throws IOException, NotBoundException {
        outputStream.writeObject(new MainMessageReconnect(name, idGame, me));
        messageDone();
    }

    @Override
    public void ready(String nickname) throws IOException, NotBoundException {
        outputStream.writeObject(new MessagePlayerReady(nickname));
        messageDone();
    }

    @Override
    public void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws IOException {
        outputStream.writeObject(new MessageAddCard(nickname,cardToAdd,cardOnBoard,cornerToAttach,flip));
        messageDone();
    }

    @Override
    public void addStartingCard(String nickname, Boolean flip) throws IOException {
        outputStream.writeObject(new MessageAddStarting(nickname, flip));
        messageDone();
    }

    @Override
    public void choosePlayerGoal(String nickname, int choice) throws IOException {
        outputStream.writeObject(new MessageObjectiveChosen(nickname,choice));
        messageDone();
    }

    @Override
    public void drawResourceFromDeck(String nickname) throws IOException {
        outputStream.writeObject(new MessageDrawResources(nickname));
        messageDone();
    }

    @Override
    public void drawGoldFromDeck(String nickname) throws IOException {
        outputStream.writeObject(new MessageDrawGold(nickname));
        messageDone();
    }

    @Override
    public void drawFromBoard(String nickname, int position) throws IOException {
        outputStream.writeObject(new MessageDrawFromBoard(nickname, position));
        messageDone();
    }

    @Override
    public void sendMessage(String txt, String nickname) throws IOException {
        outputStream.writeObject(new MessageSendMessage(txt,nickname));
        messageDone();
    }

    @Override
    public void sendPrivateMessage(String txt, String nicknameSender, String nicknameReciever) throws IOException {
        outputStream.writeObject(new MessageSendPrivateMessage(nicknameSender,nicknameReciever,txt));
        messageDone();
    }

    @Override
    public void getPublicChatLog(String requesterName) throws IOException {
        outputStream.writeObject(new MessageGetPublicChatLog(requesterName));
        messageDone();
    }

    @Override
    public void getPrivateChatLog(String yourName, String otherName) throws IOException {
        outputStream.writeObject(new MessageGetPrivateChatLog(yourName,otherName));
        messageDone();
    }

    @Override
    public void setMaxNUm(int num) throws IOException {
        outputStream.writeObject(new MessageMaxNum(num));
        messageDone();
    }
}
