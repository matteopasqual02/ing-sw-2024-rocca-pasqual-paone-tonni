package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.MainController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    private final MainControllerInterface mainController;
    private static RMIServer server = null;
    private static Registry registry = null;

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
    //the constructor of the RMI server associates the MainController to the server, because we only have 1 main controller
    public RMIServer() throws RemoteException {
        super(0);
        mainController = MainController.getInstance();
    }

    public void test() throws RemoteException {
        ;
    }

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
    public synchronized static Registry getRegistry() throws RemoteException {
        return registry;
    }


    @Override
    public List<GameController> getRunningGames() {
        return null;
    }

    //override section
    @Override
    public GameControllerInterface createGameController(String nickname, int numMaxOfPlayer, GameListener me, NotifierInterface notifier) throws RemoteException {
        GameControllerInterface remoteController = server.mainController.createGameController(nickname, numMaxOfPlayer, me, notifier);
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
    public GameControllerInterface joinFirstAvailableGame(String nickname, GameListener me, NotifierInterface notifier) throws RemoteException {
        GameControllerInterface remoteController = server.mainController.joinFirstAvailableGame(nickname, me, notifier);
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
    public GameControllerInterface joinGameByID(String nickname, int idToConnect, GameListener me, NotifierInterface notifier) throws RemoteException {
        GameControllerInterface remoteController = server.mainController.joinGameByID(nickname, idToConnect, me, notifier);
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
    public GameControllerInterface reconnect(String nickname, int idToReconnect, GameListener me, NotifierInterface notifier) throws RemoteException {
        GameControllerInterface remoteController  = server.mainController.reconnect(nickname, idToReconnect, me, notifier);
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
    public GameControllerInterface leaveGame(String nickname, int idToDisconnect, GameListener me)throws RemoteException {
        GameControllerInterface remoteController  = server.mainController.leaveGame(nickname, idToDisconnect, me);
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
