package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ServerInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;
import static org.fusesource.jansi.Ansi.ansi;


public class RMIServerStub implements ServerInterface {
    private static MainControllerInterface requests;
    private GameControllerInterface gameController = null;
    private Registry registry;
    private RMINotifier notifier;

    public RMIServerStub(){
        super();
        connect();
    }

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

    @Override
    public void createGame(String name, int maxNumPlayers, GameListener me) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        gameController = requests.createGameController(name, maxNumPlayers);
        notifier = new RMINotifier(me);
        gameController.addMyselfAsListener(me,notifier);
    }

    @Override
    public void joinFirstAvailable(String name, GameListener me) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        gameController = requests.joinFirstAvailableGame(name);
        if (gameController != null) {
            notifier = new RMINotifier(me);
            gameController.addMyselfAsListener(me,notifier);
        }
        else {
            me.noAvailableGame();
        }
    }

    @Override
    public void joinGameByID(String name, int idGame, GameListener me) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        gameController = requests.joinGameByID(name, idGame);
        if(gameController != null){
            notifier = new RMINotifier(me);
            gameController.addMyselfAsListener(me,notifier);
        }
    }

    @Override
    public void ready(String nickname) throws RemoteException, NotBoundException {
        gameController.ready(nickname);
    }

   @Override
   public void reconnect(String name, int idGame, GameListener me) throws IOException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        gameController = requests.reconnect(name, idGame);
       notifier = new RMINotifier(me);
       gameController.addMyselfAsListener(me,notifier);

   }
    @Override
    public void leave(String nickname, int idGame, GameListener me) throws IOException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);

        requests.leaveGame(nickname, idGame);
        gameController.removeMyselfAsListener(me);
        gameController = null;
    }

    //from here on the methods should be to show the update directly to the client, or the methods that
    // the client can call it depends on what we choose

    @Override
    public void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws RemoteException {
        gameController.addCard(nickname,cardToAdd,cardOnBoard,cornerToAttach,flip);
    }

    @Override
    public void addStartingCard(String nickname, Boolean flip) throws RemoteException {
        gameController.addStartingCard(nickname,flip);
    }

    @Override
    public void choosePlayerGoal(String nickname, int choice) throws RemoteException {
        gameController.choosePlayerGoal(nickname, choice);
    }

    @Override
    public void drawResourceFromDeck(String nickname) throws RemoteException {
        gameController.drawResourceFromDeck(nickname);
    }

    @Override
    public void drawGoldFromDeck(String nickname) throws RemoteException {
        gameController.drawGoldFromDeck(nickname);
    }

    @Override
    public void drawFromBoard(String nickname, int position) throws RemoteException {
        gameController.drawFromBoard(nickname, position);
    }

    @Override
    public void sendMessage(String txt, String nickname) throws RemoteException {
        gameController.sendMessage(txt,nickname);
    }

    @Override
    public void sendPrivateMessage(String txt, String nicknameSender, String nicknameReciever) throws RemoteException {
        gameController.sendPrivateMessage(nicknameSender,nicknameReciever,txt);
    }

    @Override
    public void getPublicChatLog(String requesterName) throws IOException {
        gameController.getPublicChatLog(requesterName);
    }

    @Override
    public void getPrivateChatLog(String yourName, String otherName) throws IOException{
        gameController.getPrivateChatLog(yourName,otherName);
    }

    @Override
    public void setMaxNUm(int num) throws IOException {
        gameController.setMaxNumberOfPlayer(num);
    }

}
