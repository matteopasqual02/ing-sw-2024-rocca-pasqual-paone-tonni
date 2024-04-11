package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.VirtualViewInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.PrintAsync.printAsync;
import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.PrintAsync.printAsyncNoLine;


public class RMIClient implements VirtualViewInterface {
    private static MainControllerInterface requests;
    private GameControllerInterface gameController = null;
    private static GameListener modelInvokedEvents;
    private String nickname;
    private Registry registry;

    public RMIClient(){
        super();
        connect();
    }

    public void connect(){
        boolean retry = false;
        int attempt =1;
        int i;

        do{
            try{
                registry = LocateRegistry.getRegistry(DefaultNetworkValues.Server_Ip_address, DefaultNetworkValues.Default_RMI_port);
                requests = (MainControllerInterface) registry.lookup(DefaultNetworkValues.Default_servername_RMI);

                printAsync("Client RMI ready");
                retry = false;

            }catch (Exception e){
                if (!retry) {
                    printAsync("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + DefaultNetworkValues.Default_RMI_port + "' with name: '" + DefaultNetworkValues.Default_servername_RMI + "'");

                i = 0;
                while (i < DefaultNetworkValues.seconds_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    printAsyncNoLine(".");
                    i++;
                }
                printAsyncNoLine("\n");

                if (attempt >= DefaultNetworkValues.num_of_attempt) {
                    printAsyncNoLine("Give up!");
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
    public boolean isCurrentPlaying() throws RemoteException {
        return false;
    }

    @Override
    public void setNumberOfPlayers(int num) throws RemoteException {

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

    @Override
    public void checkWinner() throws RemoteException {

    }

    @Override
    public void CreateGameController(String nickname) throws RemoteException {

    }

    @Override
    public void joinFirstAvailableGame(String nickname) throws RemoteException {

    }

    @Override
    public void reconnect(String nickname) throws RemoteException {

    }

    @Override
    public void leaveGame(String nickname) throws RemoteException {

    }
}
