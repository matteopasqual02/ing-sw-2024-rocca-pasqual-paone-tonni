package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.VirtualViewInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;
import static org.fusesource.jansi.Ansi.ansi;


public class RMIClient implements VirtualViewInterface {
    private static MainControllerInterface requests;
    private GameControllerInterface gameController = null;
    //private static GameListener modelInvokedEvents;
    private String nickname;
    private Registry registry;

    public RMIClient(){
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
                    ConsolePrinter.consolePrinter("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
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
                    consolePrinter("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }

        }while(retry);
    }

    @Override
    public void createGame(String name,int maxNumPlayers,GameListener me) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        gameController = requests.createGameController(name,maxNumPlayers);
        gameController.addMyselfAsListener(me);
        gameController.setMaxNumberOfPlayer(maxNumPlayers);
        nickname = name;
    }
    @Override
    public Boolean joinFirstAvailable(String name, GameListener me) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        gameController = requests.joinFirstAvailableGame(name);
        if(gameController==null){
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("No games available, you need to create a new game"));
            return false;
        }
        gameController.addMyselfAsListener(me);
        nickname = name;
        return true;
    }

    public Boolean joinGameByID(String name, int idGame, GameListener me) throws RemoteException, NotBoundException {

        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        gameController = requests.joinGameByID(name, idGame);
        if(gameController==null){
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("This game is not available"));
            return false;
        }
        gameController.addMyselfAsListener(me);
        nickname = name;
        return true;
    }
   @Override
    public void reconnect(String nick, int idGame) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        gameController = requests.reconnect(nick, idGame);

        nickname = nick;

    }
    @Override
    public void leave(String nick, int idGame, GameListener me) throws IOException, NotBoundException {

        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);

        requests.leaveGame(nick, idGame);
        gameController.removeMyselfAsListener(me);
        gameController = null;
        nickname = null;
    }
    //from here on the methods should be to show the update directly to the client, or the methods that the client can call it depends on what we choose


    @Override
    public void setNumberOfPlayers(int num) throws RemoteException {
        gameController.setMaxNumberOfPlayer(num);
    }

    @Override
    public int getID() throws RemoteException {
        return gameController.getID();
    }

    @Override
    public void nextTurn() {

    }

    @Override
    public void createTable() throws RemoteException {

    }

    @Override
    public void addCard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws RemoteException {

    }

    @Override
    public void addStartingCard(Boolean flip) throws RemoteException {

    }

    @Override
    public void choosePlayerGoal(int choice) throws RemoteException {

    }

    @Override
    public void drawResourceFromDeck() throws RemoteException {

    }

    @Override
    public void drawGoldFromDeck() throws RemoteException {

    }

    @Override
    public void drawFromBoard(int position) throws RemoteException {

    }

}
