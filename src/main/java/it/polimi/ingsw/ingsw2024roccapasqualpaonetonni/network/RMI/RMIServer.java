package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.MainController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;

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
    public GameControllerInterface createGameController(String nickname, int numMaxOfPlayer) throws RemoteException {
        GameControllerInterface result = server.mainController.createGameController(nickname,numMaxOfPlayer);

        try{
            //result needs to be an exportable object
            UnicastRemoteObject.exportObject(result,0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        ConsolePrinter.consolePrinter("[RMI] " + nickname + " has created and joined a new game");
        return result;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(String nickname)throws RemoteException {
        GameControllerInterface result = server.mainController.joinFirstAvailableGame(nickname);

        try{
            UnicastRemoteObject.exportObject(result,0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        ConsolePrinter.consolePrinter("[RMI] " + nickname + " has joined the game");
        return result;
    }

    @Override
    public GameControllerInterface reconnect(String nickname, int idToReconnect)throws RemoteException {
        GameControllerInterface result = server.mainController.reconnect(nickname,idToReconnect);

        try{
            UnicastRemoteObject.exportObject(result,0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        ConsolePrinter.consolePrinter("[RMI] " + nickname + " has re-joined the game" + idToReconnect);
        return result;
    }

    @Override
    public GameControllerInterface leaveGame(String nickname, int idToDisconnect)throws RemoteException {
        GameControllerInterface result = server.mainController.leaveGame(nickname,idToDisconnect);

        try{
            UnicastRemoteObject.exportObject(result,0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        ConsolePrinter.consolePrinter("[RMI] " + nickname + " has leaved the game");
        return result;
    }

    @Override
    public void clearSingleton() {}
}
