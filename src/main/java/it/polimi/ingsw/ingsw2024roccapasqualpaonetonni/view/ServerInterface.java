package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;

public interface ServerInterface extends Remote{

    //--------------------------GAME CREATION PHASE
    public void createGame(String name, int maxNumPlayers, GameListener me) throws IOException, NotBoundException;
    public void joinFirstAvailable(String name, GameListener me) throws IOException, NotBoundException;
    public void joinGameByID(String name, int idGame, GameListener me) throws IOException, NotBoundException;
    public void ready(String nickname) throws IOException, NotBoundException;


    //public boolean isCurrentPlaying() throws RemoteException;
    public void nextTurn();
    public void createTable() throws RemoteException;
    public void addCard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws RemoteException;
    public void addStartingCard(Boolean flip) throws RemoteException;
    public void choosePlayerGoal(int choice) throws RemoteException;
    public void drawResourceFromDeck() throws RemoteException;
    public void drawGoldFromDeck() throws RemoteException;
    public void drawFromBoard(int position) throws RemoteException;
    //public void checkWinner() throws RemoteException;
    public void reconnect(String nick, int idGame) throws RemoteException, NotBoundException;
    public void leave(String nick, int idGame, GameListener me) throws IOException, NotBoundException;

}
