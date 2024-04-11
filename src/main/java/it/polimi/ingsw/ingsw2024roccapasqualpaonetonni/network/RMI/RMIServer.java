package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.MainController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultValues;

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
            registry = LocateRegistry.createRegistry(DefaultValues.Default_port_RMI);
            getRegistry().rebind(DefaultValues.Default_servername_RMI,server);
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
        return null;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(String nickname) {
        return null;
    }

    @Override
    public GameControllerInterface reconnect(String nickname) {
        return null;
    }

    @Override
    public GameControllerInterface leaveGame(String nickname) {
        return null;
    }
}
