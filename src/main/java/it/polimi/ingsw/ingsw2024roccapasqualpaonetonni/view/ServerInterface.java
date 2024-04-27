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
    public void reconnect(String name, int idGame, GameListener me) throws IOException, NotBoundException;
    public void leave(String nick, int idGame, GameListener me) throws IOException, NotBoundException;

    //--------------------------READY PHASE
    public void ready(String nickname) throws IOException, NotBoundException;

    //--------------------------GAME FLOW PHASE
    public void addCard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws IOException;
    public void addStartingCard(Boolean flip) throws IOException;
    public void choosePlayerGoal(int choice) throws IOException;
    public void drawResourceFromDeck() throws IOException;
    public void drawGoldFromDeck() throws IOException;
    public void drawFromBoard(int position) throws IOException;

}
