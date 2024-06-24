package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;


/**
 * The type Rmi server stub.
 */
public class RMIServerStub implements ServerInterface {
    /**
     * The constant requests.
     */
    private static MainControllerInterface requests;
    /**
     * The Game controller.
     */
    private GameControllerInterface gameController = null;
    /**
     * The Registry.
     */
    private Registry registry;

    /**
     * Instantiates a new Rmi server stub.
     */
    public RMIServerStub(){
        super();
        connect();
    }

    /**
     * Connect.
     */
//the client main calls this to connect to a server
    private void connect(){
        boolean retry = false;
        int attempt = 1;
        int i;

        do{
            try{
                registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
                requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);

                ConsolePrinter.consolePrinter("Client RMI ready");
                retry = false;

            }catch (Exception e){
                if (!retry) {
                    ConsolePrinter.consolePrinter("[ERROR] CONNECTING TO RMI SERVER");
                }
                consolePrinter("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + DefaultNetworkValues.Default_RMI_port + "' with name: '" + DefaultNetworkValues.Default_servername_RMI + "'");

                i = 0;
                while (i < DefaultNetworkValues.seconds_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    consolePrinter(".");
                    i++;
                }
                consolePrinter("\n");

                if (attempt >= DefaultNetworkValues.num_of_attempt) {
                    consolePrinter("[RMI] Give up!");
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }

        }while(retry);
    }

    /**
     * Pong.
     *
     * @param me me
     */
    @Override
    public void pong(String me) {
        try {
            gameController.pong(me);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create game.
     *
     * @param name          the name
     * @param maxNumPlayers the max num players
     * @param me            me
     * @throws RemoteException   the remote exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void createGame(String name, int maxNumPlayers, GameListener me) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        RMINotifier notifier = new RMINotifier(me);
        gameController = requests.createGameController(name, maxNumPlayers, notifier);
        if (gameController == null) {
            me.noAvailableGame();
            return;
        }
        gameController.addToPingPong(name);
    }

    /**
     * Join first available.
     *
     * @param name the name
     * @param me   me
     * @throws RemoteException   the remote exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void joinFirstAvailable(String name, GameListener me) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        RMINotifier notifier = new RMINotifier(me);
        gameController = requests.joinFirstAvailableGame(name, notifier);
        if (gameController == null) {
            me.noAvailableGame();
            return;
        }
        gameController.addToPingPong(name);
    }

    /**
     * Join game by id.
     *
     * @param name   the name
     * @param idGame the id game
     * @param me     me
     * @throws RemoteException   the remote exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void joinGameByID(String name, int idGame, GameListener me) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        RMINotifier notifier = new RMINotifier(me);
        gameController = requests.joinGameByID(name, idGame, notifier);
        if (gameController == null) {
            me.noAvailableGame();
            return;
        }
        gameController.addToPingPong(name);
    }

    /**
     * Ready.
     *
     * @param nickname the nickname
     * @throws RemoteException   the remote exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void ready(String nickname) throws RemoteException, NotBoundException {
        gameController.ready(nickname);
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
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        RMINotifier notifier = new RMINotifier(me);
        gameController = requests.reconnect(name, idGame, notifier);
        if (gameController == null) {
            me.noAvailableGame();
            return;
        }
        gameController.addToPingPong(name);
    }

    /**
     * Leave.
     *
     * @param nickname the nickname
     * @param idGame   the id game
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    @Override
    public void leave(String nickname, int idGame) throws IOException, NotBoundException {
        //registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        //requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        requests.leaveGame(nickname, idGame);
        gameController = null;
        System.exit(0);
    }

    //from here on the methods should be to show the update directly to the client, or the methods that
    // the client can call it depends on what we choose

    /**
     * Add card.
     *
     * @param nickname       the nickname
     * @param cardToAdd      the card to add
     * @param cardOnBoard    the card on board
     * @param cornerToAttach the corner to attach
     * @param flip           the flip
     * @throws RemoteException the remote exception
     */
    @Override
    public void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws RemoteException {
        gameController.addCard(nickname,cardToAdd,cardOnBoard,cornerToAttach,flip);
    }

    /**
     * Add starting card.
     *
     * @param nickname the nickname
     * @param flip     the flip
     * @throws RemoteException the remote exception
     */
    @Override
    public void addStartingCard(String nickname, Boolean flip) throws RemoteException {
        gameController.addStartingCard(nickname,flip);
    }

    /**
     * Choose player goal.
     *
     * @param nickname the nickname
     * @param choice   the choice
     * @throws RemoteException the remote exception
     */
    @Override
    public void choosePlayerGoal(String nickname, int choice) throws RemoteException {
        gameController.choosePlayerGoal(nickname, choice);
    }

    /**
     * Draw resource from deck.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    @Override
    public void drawResourceFromDeck(String nickname) throws RemoteException {
        gameController.drawResourceFromDeck(nickname);
    }

    /**
     * Draw gold from deck.
     *
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    @Override
    public void drawGoldFromDeck(String nickname) throws RemoteException {
        gameController.drawGoldFromDeck(nickname);
    }

    /**
     * Draw from board.
     *
     * @param nickname the nickname
     * @param position the position
     * @throws RemoteException the remote exception
     */
    @Override
    public void drawFromBoard(String nickname, int position) throws RemoteException {
        gameController.drawFromBoard(nickname, position);
    }

    /**
     * Send message.
     *
     * @param txt      the txt
     * @param nickname the nickname
     * @throws RemoteException the remote exception
     */
    @Override
    public void sendMessage(String txt, String nickname) throws RemoteException {
        gameController.sendMessage(txt,nickname);
    }

    /**
     * Send private message.
     *
     * @param txt              the txt
     * @param nicknameSender   the nickname sender
     * @param nicknameReciever the nickname reciever
     * @throws RemoteException the remote exception
     */
    @Override
    public void sendPrivateMessage(String txt, String nicknameSender, String nicknameReciever) throws RemoteException {
        gameController.sendPrivateMessage(nicknameSender,nicknameReciever,txt);
    }

    /**
     * Gets public chat log.
     *
     * @param requesterName the requester name
     * @throws IOException the io exception
     */
    @Override
    public void getPublicChatLog(String requesterName) throws IOException {
        gameController.getPublicChatLog(requesterName);
    }

    /**
     * Gets private chat log.
     *
     * @param yourName  your name
     * @param otherName the other name
     * @throws IOException the io exception
     */
    @Override
    public void getPrivateChatLog(String yourName, String otherName) throws IOException{
        gameController.getPrivateChatLog(yourName,otherName);
    }

    /**
     * Sets max n um.
     *
     * @param num the num
     * @throws IOException the io exception
     */
    @Override
    public void setMaxNUm(int num) throws IOException {
        gameController.setMaxNumberOfPlayer(num);
    }

}
