package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;

public interface GameControllerInterface extends Remote {

    //---------------------------------LISTENERS SECTION
    void addMyselfAsListener(GameListener me, NotifierInterface notifier) throws RemoteException;
    void removeMyselfAsListener(GameListener me) throws RemoteException;

    //---------------------------------GAME CREATION SECTION
    void addPlayer(String nickname) throws RemoteException;
    void setMaxNumberOfPlayer(int num) throws RemoteException;
    void ready(String nickname) throws RemoteException;


    //---------------------------------TABLE AND INIT SECTION
    void addCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip) throws RemoteException;
    void addStartingCard(String nickname, Boolean flip) throws RemoteException;
    void choosePlayerGoal(String nickname, int choice) throws RemoteException;
    void drawResourceFromDeck(String nickname) throws RemoteException;
    void drawGoldFromDeck(String nickname) throws RemoteException;
    void drawFromBoard(String nickname, int position) throws RemoteException;

    void sendMessage(String txt, String nickname) throws RemoteException;
    void sendPrivateMessage(String senderName, String recieverName, String txt) throws RemoteException;
    void getPublicChatLog(String requesterName) throws RemoteException;
    void getPrivateChatLog(String yourName, String otherName) throws RemoteException;

    int getGameId() throws RemoteException;

    void pong(String client) throws RemoteException;
    void addToPingPong(String client) throws RemoteException;
}
