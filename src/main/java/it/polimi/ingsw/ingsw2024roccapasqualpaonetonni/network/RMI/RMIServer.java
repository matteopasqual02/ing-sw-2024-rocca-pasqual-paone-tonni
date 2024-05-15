package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.MainController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


/**
 * The type Rmi server.
 */
public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    private final MainControllerInterface mainController;
    private static RMIServer server = null;
    private static Registry registry = null;

    /**
     * Bind rmi server.
     *
     * @return the rmi server
     */
//server section
    public static RMIServer bind(){
        try{
            server = new RMIServer();
            //bind stub in registry
            registry = LocateRegistry.createRegistry(DefaultNetworkValues.Default_RMI_port);
            getRegistry().rebind(DefaultNetworkValues.Default_servername_RMI,server);
            ConsolePrinter.consolePrinter("[READY] RMI SERVER");

        }catch (RemoteException e){
            e.printStackTrace();
            System.err.println("[ERROR] RMI SERVER START: \n\tRMI exception: " + e);
        }
        return getInstance();
    }

    /**
     * Instantiates a new Rmi server.
     *
     * @throws RemoteException the remote exception
     */
//the constructor of the RMI server associates the MainController to the server, because we only have 1 main controller
    public RMIServer() throws RemoteException {
        super(0);
        mainController = MainController.getInstance();
    }

    /**
     * Test.
     *
     * @throws RemoteException the remote exception
     */
    public void test() throws RemoteException {
        ;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public synchronized static RMIServer getInstance() {
        if(server == null) {
            try {
                server = new RMIServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return server;
    }

    /**
     * Gets registry.
     *
     * @return the registry
     * @throws RemoteException the remote exception
     */
    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }


    @Override
    public List<GameController> getRunningGames() {
        return null;
    }

    //override section
    @Override
    public GameControllerInterface createGameController(String nickname, int numMaxOfPlayer, NotifierInterface notifier) throws RemoteException {
        GameControllerInterface remoteController = server.mainController.createGameController(nickname, numMaxOfPlayer, notifier);
        GameControllerInterface remoteControllerUniCasted = null;
        try{
            //remoteControllerUniCasted needs to be an exportable object
            remoteControllerUniCasted = (GameControllerInterface) UnicastRemoteObject.exportObject(remoteController,0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        ConsolePrinter.consolePrinter("[RMI] \"" + nickname + "\" has created and joined a new game");
        return remoteControllerUniCasted;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(String nickname, NotifierInterface notifier) throws RemoteException {
        GameControllerInterface remoteController = server.mainController.joinFirstAvailableGame(nickname, notifier);
        GameControllerInterface remoteControllerUniCasted = null;

        if(remoteController != null){
            ConsolePrinter.consolePrinter("[RMI] \"" + nickname + "\" has joined game " + remoteController.getGameId());
            try{
                remoteControllerUniCasted = (GameControllerInterface) UnicastRemoteObject.exportObject(remoteController,0);
            }catch (RemoteException e){
                return remoteController;
            }
        }
        else {
            //this is the case in which there are no previous games, I return null so that the client knows it has to create a game
            return null;
        }

        return remoteControllerUniCasted;
    }

    @Override
    public GameControllerInterface joinGameByID(String nickname, int idToConnect, NotifierInterface notifier) throws RemoteException {
        GameControllerInterface remoteController = server.mainController.joinGameByID(nickname, idToConnect, notifier);
        GameControllerInterface remoteControllerUniCasted  = null;
        if(remoteController != null){
            ConsolePrinter.consolePrinter("[RMI] " + nickname + " has joined the game chosen");
            try{
                remoteControllerUniCasted  = (GameControllerInterface) UnicastRemoteObject.exportObject(remoteController ,0);
            }catch (RemoteException e){
                return remoteController;
            }
        }
        else {
            //this is the case in which there are no ID games, I return null so that the client knows it has to create a game
            return null;
        }

        return remoteControllerUniCasted ;
    }

    @Override
    public GameControllerInterface reconnect(String nickname, int idToReconnect, NotifierInterface notifier) throws RemoteException {
        GameControllerInterface remoteController  = server.mainController.reconnect(nickname, idToReconnect, notifier);
        GameControllerInterface remoteControllerUniCasted  = null;


        ConsolePrinter.consolePrinter("[RMI] " + nickname + " has re-joined the game" + idToReconnect);
        try{
            remoteControllerUniCasted  = (GameControllerInterface) UnicastRemoteObject.exportObject(remoteController ,0);
        }catch (RemoteException e){
            return remoteController;
        }

        return remoteControllerUniCasted;
    }

    @Override
    public GameControllerInterface leaveGame(String nickname, int idToDisconnect)throws RemoteException {
        GameControllerInterface remoteController  = server.mainController.leaveGame(nickname, idToDisconnect);
        GameControllerInterface remoteControllerUniCasted  = null;

        ConsolePrinter.consolePrinter("[RMI] " + nickname + " has leaved the game");
        try{
            remoteControllerUniCasted  = (GameControllerInterface) UnicastRemoteObject.exportObject(remoteController ,0);
        }catch (RemoteException e){
            return remoteController;
        }

        return remoteControllerUniCasted;
    }

    @Override
    public void clearSingleton() throws RemoteException{
        server.mainController.clearSingleton();
    }


}
