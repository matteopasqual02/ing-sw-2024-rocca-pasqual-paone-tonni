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
        gameController.addMyselfAsListener(me);
    }

    @Override
    public void joinFirstAvailable(String name, GameListener me) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
        requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);
        gameController = requests.joinFirstAvailableGame(name);
        if (gameController != null) {
            gameController.addMyselfAsListener(me);
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
            gameController.addMyselfAsListener(me);
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
        gameController.addMyselfAsListener(me);

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
