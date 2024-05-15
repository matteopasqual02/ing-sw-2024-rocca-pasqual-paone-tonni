package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages.ServerGenericMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.NotBoundException;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;

/**
 * The type Socket client.
 */
public class SocketClient extends Thread implements ServerInterface, Serializable {
    /**
     * The Client socket.
     */
    private transient Socket clientSocket;
    /**
     * The Input stream.
     */
    private transient ObjectInputStream inputStream;
    /**
     * The Output stream.
     */
    private transient ObjectOutputStream outputStream;
    /**
     * The Nickname.
     */
    private String nickname;
    /**
     * The Client.
     */
    private final Client client;
    /**
     * The Server request handler.
     */
    private GameListener serverRequestHandler;

    //private final SocketNotifier socketNotifier;

    /**
     * Instantiates a new Socket client.
     *
     * @param client the client
     * @throws IOException the io exception
     */
    public SocketClient(Client client) throws IOException {
        this.client = client;
        connect();
        this.start();
    }

    /**
     * Run.
     */
    public void run(){
        ServerGenericMessage message;
        while (true){
            try{
                message = (ServerGenericMessage) inputStream.readObject();
                message.launchMessage(client);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                consolePrinter("[SOCKET] Connection Lost!");
                System.exit(-1);
            }
        }

    }

    /**
     * Connect.
     */
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

    /**
     * Disconnect.
     *
     * @throws IOException the io exception
     */
    private void disconnect() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    /**
     * Message done.
     *
     * @throws IOException the io exception
     */
    private void messageDone() throws IOException {
        synchronized (outputStream) {
            outputStream.flush();
            outputStream.reset();
        }
    }

    /**
     * Gets output stream.
     *
     * @return the output stream
     */
    public ObjectOutputStream getOutputStream() {
        return this.outputStream;
    }

    /**
     * Gets input stream.
     *
     * @return the input stream
     */
    public ObjectInputStream getInputStream() {
        return this.inputStream;
    }

    /**
     * Pong.
     *
     * @param me me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void pong(String me) throws IOException, NotBoundException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessagePong(me));
        }
        messageDone();

    }

    /**
     * Create game.
     *
     * @param name          the name
     * @param maxNumPlayers the max num players
     * @param me            me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void createGame(String name, int maxNumPlayers, GameListener me) throws IOException, NotBoundException {
        synchronized (outputStream) {
            outputStream.writeObject(new MainMessageCreateGame(name, maxNumPlayers));
        }
        messageDone();

        synchronized (outputStream) {
            outputStream.writeObject(new MessageAddToPingPong(name));
        }
        messageDone();

    }

    /**
     * Join first available.
     *
     * @param name the name
     * @param me   me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void joinFirstAvailable(String name, GameListener me) throws IOException, NotBoundException {
        synchronized (outputStream) {
            outputStream.writeObject(new MainMessageJoinFirstAvailable(name, me));
            messageDone();
        }

        synchronized (outputStream) {
            outputStream.writeObject(new MessageAddToPingPong(name));
            messageDone();
        }

    }

    /**
     * Join game by id.
     *
     * @param name   the name
     * @param idGame the id game
     * @param me     me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void joinGameByID(String name, int idGame, GameListener me) throws IOException, NotBoundException {
        synchronized (outputStream) {
            outputStream.writeObject(new MainMessageJoinGameById(name, idGame, me));
            messageDone();
        }

        synchronized (outputStream) {
            outputStream.writeObject(new MessageAddToPingPong(name));
            messageDone();
        }
    }

    /**
     * Reconnect.
     *
     * @param name   the name
     * @param idGame the id game
     * @param me     me
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void reconnect(String name, int idGame, GameListener me) throws IOException, NotBoundException {
        synchronized (outputStream) {
            outputStream.writeObject(new MainMessageReconnect(name, idGame, me));
            messageDone();
        }

        synchronized (outputStream) {
            outputStream.writeObject(new MessageAddToPingPong(name));
            messageDone();
        }
    }

    /**
     * Leave.
     *
     * @param name   the name
     * @param idGame the id game
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void leave(String name, int idGame) throws IOException, NotBoundException {
        synchronized (outputStream) {
            outputStream.writeObject(new MainMessageDisconnect(name, idGame));
            messageDone();
        }
    }

    /**
     * Ready.
     *
     * @param nickname the nickname
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void ready(String nickname) throws IOException, NotBoundException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessagePlayerReady(nickname));
            messageDone();
        }
    }

    /**
     * Add card.
     *
     * @param nickname       the nickname
     * @param cardToAdd      the card to add
     * @param cardOnBoard    the card on board
     * @param cornerToAttach the corner to attach
     * @param flip           the flip
     * @throws IOException the io exception
     */
    @Override
    public void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageAddCard(nickname, cardToAdd, cardOnBoard, cornerToAttach, flip));
            messageDone();
        }
    }

    /**
     * Add starting card.
     *
     * @param nickname the nickname
     * @param flip     the flip
     * @throws IOException the io exception
     */
    @Override
    public void addStartingCard(String nickname, Boolean flip) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageAddStarting(nickname, flip));
            messageDone();
        }
    }

    /**
     * Choose player goal.
     *
     * @param nickname the nickname
     * @param choice   the choice
     * @throws IOException the io exception
     */
    @Override
    public void choosePlayerGoal(String nickname, int choice) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageObjectiveChosen(nickname, choice));
            messageDone();
        }
    }

    /**
     * Draw resource from deck.
     *
     * @param nickname the nickname
     * @throws IOException the io exception
     */
    @Override
    public void drawResourceFromDeck(String nickname) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageDrawResources(nickname));
            messageDone();
        }
    }

    /**
     * Draw gold from deck.
     *
     * @param nickname the nickname
     * @throws IOException the io exception
     */
    @Override
    public void drawGoldFromDeck(String nickname) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageDrawGold(nickname));
            messageDone();
        }
    }

    /**
     * Draw from board.
     *
     * @param nickname the nickname
     * @param position the position
     * @throws IOException the io exception
     */
    @Override
    public void drawFromBoard(String nickname, int position) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageDrawFromBoard(nickname, position));
            messageDone();
        }
    }

    /**
     * Send message.
     *
     * @param txt      the txt
     * @param nickname the nickname
     * @throws IOException the io exception
     */
    @Override
    public void sendMessage(String txt, String nickname) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageSendMessage(txt, nickname));
            messageDone();
        }
    }

    /**
     * Send private message.
     *
     * @param txt              the txt
     * @param nicknameSender   the nickname sender
     * @param nicknameReciever the nickname reciever
     * @throws IOException the io exception
     */
    @Override
    public void sendPrivateMessage(String txt, String nicknameSender, String nicknameReciever) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageSendPrivateMessage(nicknameSender, nicknameReciever, txt));
            messageDone();
        }
    }

    /**
     * Gets public chat log.
     *
     * @param requesterName the requester name
     * @throws IOException the io exception
     */
    @Override
    public void getPublicChatLog(String requesterName) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageGetPublicChatLog(requesterName));
            messageDone();
        }
    }

    /**
     * Gets private chat log.
     *
     * @param yourName  your name
     * @param otherName the other name
     * @throws IOException the io exception
     */
    @Override
    public void getPrivateChatLog(String yourName, String otherName) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageGetPrivateChatLog(yourName, otherName));
            messageDone();
        }
    }

    /**
     * Sets max n um.
     *
     * @param num the num
     * @throws IOException the io exception
     */
    @Override
    public void setMaxNUm(int num) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MessageMaxNum(num));
            messageDone();
        }
    }
}
