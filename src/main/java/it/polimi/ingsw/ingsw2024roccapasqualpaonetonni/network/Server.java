package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;

import java.io.IOException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ClientInterface {
    //private final List<GameController> gamesList;
    public Server() throws IOException {

    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello World!";
    }

    public static void main(String[] args) {
        try {
            // create stub
            Server obj = new Server();
            ClientInterface stub = (ClientInterface) UnicastRemoteObject.exportObject(obj, 0);

            // bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Client", stub);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
