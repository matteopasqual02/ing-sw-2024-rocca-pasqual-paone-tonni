package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;

public interface ServerInterface extends Remote{

    //public boolean isCurrentPlaying() throws RemoteException;
    public void setNumberOfPlayers(int num) throws RemoteException;
    public int getID() throws RemoteException;
    public void addCard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws RemoteException;
    public void addStartingCard(Boolean flip) throws IOException;
    public void choosePlayerGoal(int choice) throws RemoteException;
    public void drawResourceFromDeck() throws RemoteException;
    public void drawGoldFromDeck() throws RemoteException;
    public void drawFromBoard(int position) throws RemoteException;
    //public void checkWinner() throws RemoteException;
    public Boolean createGame(String name, int maxNumPlayers, GameListener me) throws IOException, NotBoundException;
    public Boolean joinFirstAvailable(String name, GameListener me) throws IOException, NotBoundException;
    public Boolean joinGameByID(String name, int idGame, GameListener me) throws IOException, NotBoundException;
    public Boolean reconnect(String name, int idGame, GameListener me) throws IOException, NotBoundException;
    public Boolean leave(String name, int idGame, GameListener me) throws IOException, NotBoundException;

}
