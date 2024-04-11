package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.MainController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.PrintAsync.printAsync;

public class RMIServer extends UnicastRemoteObject implements MainControllerInterface {

    private final MainControllerInterface mainController;
    private static RMIServer server = null;
    private static Registry registry = null;

    //server section
    public static RMIServer bind(){
        try{
            server = new RMIServer();
            //bind stub in registry
            registry = LocateRegistry.createRegistry(DefaultNetworkValues.Default_port_RMI);
            getRegistry().rebind(DefaultNetworkValues.Default_servername_RMI,server);
            printAsync("[READY] RMI SERVER");

        }catch (RemoteException e){
            e.printStackTrace();
            System.err.println("[ERROR] RMI SERVER START: \n\tRMI exception: " + e);
        }
        return getInstance();
    }
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


    //override section
    @Override
    public GameControllerInterface createGameController(String nickname) {
        GameControllerInterface result = server.mainController.createGameController(nickname);

        try{
            UnicastRemoteObject.exportObject(result,0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        printAsync("[RMI] " + nickname + " has created and joined a new game");
        return result;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(String nickname) {
        GameControllerInterface result = server.mainController.joinFirstAvailableGame(nickname);

        try{
            UnicastRemoteObject.exportObject(result,0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        printAsync("[RMI] " + nickname + " has joined the game");
        return result;
    }

    @Override
    public GameControllerInterface reconnect(String nickname, int idToReconnect) {
        GameControllerInterface result = server.mainController.reconnect(nickname,idToReconnect);

        try{
            UnicastRemoteObject.exportObject(result,0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        printAsync("[RMI] " + nickname + " has re-joined the game" + idToReconnect);
        return result;
    }

    @Override
    public GameControllerInterface leaveGame(String nickname, int idToDisconnect) {
        GameControllerInterface result = server.mainController.leaveGame(nickname,idToDisconnect);

        try{
            UnicastRemoteObject.exportObject(result,0);
        }catch (RemoteException e){
            e.printStackTrace();
        }
        printAsync("[RMI] " + nickname + " has leaved the game");
        return result;
    }
}
