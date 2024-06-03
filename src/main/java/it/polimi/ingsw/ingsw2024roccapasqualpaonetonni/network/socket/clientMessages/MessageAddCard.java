package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.rmi.RemoteException;

/**
 * The type Message add card.
 */
public class MessageAddCard extends ClientGenericMessage{
    /**
     * The Flip.
     */
    private final Boolean flip;
    /**
     * The Card to add.
     */
    private final PlayingCard cardToAdd;
    /**
     * The Card on board.
     */
    private final PlayingCard cardOnBoard;
    /**
     * The Corner.
     */
    private final int corner;
    private final double coord0;
    private final double coord1;

    /**
     * Instantiates a new Message add card.
     *
     * @param nickname    the nickname
     * @param cardToAdd   the card to add
     * @param cardOnBoard the card on board
     * @param corner      the corner
     * @param flip        the flip
     */
    public MessageAddCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int corner, Double coord0, Double coord1, Boolean flip){
        this.flip=flip;
        this.cardOnBoard=cardOnBoard;
        this.cardToAdd=cardToAdd;
        this.corner=corner;
        this.isForMainController = false;
        this.nickname = nickname;
        this.coord0=coord0;
        this.coord1=coord1;
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
        gameControllerInterface.addCard(nickname,cardToAdd,cardOnBoard,corner,coord0,coord1,flip);
    }
}
