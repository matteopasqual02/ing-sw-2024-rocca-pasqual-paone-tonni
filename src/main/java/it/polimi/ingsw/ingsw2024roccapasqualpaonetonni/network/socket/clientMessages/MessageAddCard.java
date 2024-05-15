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

    /**
     * Instantiates a new Message add card.
     *
     * @param nickname    the nickname
     * @param cardToAdd   the card to add
     * @param cardOnBoard the card on board
     * @param corner      the corner
     * @param flip        the flip
     */
    public MessageAddCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int corner, Boolean flip){
        this.flip=flip;
        this.cardOnBoard=cardOnBoard;
        this.cardToAdd=cardToAdd;
        this.corner=corner;
        this.isForMainController = false;
        this.nickname = nickname;
    }


    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface, NotifierInterface notifier) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.addCard(nickname,cardToAdd,cardOnBoard,corner,flip);
    }
}
