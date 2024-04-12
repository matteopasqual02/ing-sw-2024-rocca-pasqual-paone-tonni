package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;
import java.rmi.Remote;
import java.rmi.RemoteException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;

public interface VirtualViewInterface extends Remote{

    public boolean isCurrentPlaying() throws RemoteException;
    public void setNumberOfPlayers(int num) throws RemoteException;
    public void createTable() throws RemoteException;
    public void addCard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws RemoteException;
    public void addStartingCard(Boolean flip) throws RemoteException;
    public void choosePlayerGoal(int choice) throws RemoteException;
    public void drawResourceFromDeck() throws RemoteException;
    public void drawGoldFromDeck() throws RemoteException;
    public void drawFromBoard(int position) throws RemoteException;
    public void checkWinner() throws RemoteException;
    public void CreateGameController(String nickname) throws RemoteException;
    public void joinFirstAvailableGame(String nickname) throws RemoteException;
    public void reconnect(String nickname) throws RemoteException;
    public void leaveGame(String nickname) throws RemoteException;

}
