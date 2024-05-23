package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Message add to ping pong.
 */
public class MessageAddToPingPong extends ClientGenericMessage {
    /**
     * The Client.
     */
    private final String client;

    /**
     * Instantiates a new Message add to ping pong.
     *
     * @param client the client
     */
    public MessageAddToPingPong(String client) {
        this.client = client;
        this.isForMainController = false;
    }

    /**
     * Launch message game controller interface.
     *
     * @param mainControllerInterface the main controller interface
     * @param notifier                the notifier
     * @return the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return null;
    }

    /**
     * Launch message.
     *
     * @param gameControllerInterface the game controller interface
     * @throws RemoteException the remote exception
     */
    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        if(gameControllerInterface!=null){
            gameControllerInterface.addToPingPong(client);
        }
    }
}
