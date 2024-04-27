package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;

public interface GameControllerInterface extends Remote{

    //---------------------------------LISTENERS SECTION
    void addMyselfAsListener(GameListener me);
    void removeMyselfAsListener(GameListener me);

    //---------------------------------GAME CREATION SECTION
    void addPlayer(String nickname) throws RemoteException;
    void setMaxNumberOfPlayer(int num) throws RemoteException;
    void ready(String nickname) throws RemoteException;

    //---------------------------------PLAYER SECTION
    void removePlayer(Player p) throws RemoteException;

    //---------------------------------TABLE AND INIT SECTION
    void addCard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws RemoteException;
    void addStartingCard(Boolean flip) throws RemoteException;
    void choosePlayerGoal(int choice) throws RemoteException;
    void drawResourceFromDeck() throws RemoteException;
    void drawGoldFromDeck() throws RemoteException;
    void drawFromBoard(int position) throws RemoteException;
    void checkWinner() throws RemoteException;

}
