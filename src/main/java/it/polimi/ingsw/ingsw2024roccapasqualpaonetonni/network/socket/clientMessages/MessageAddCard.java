package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.MainControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;

import java.rmi.RemoteException;

public class MessageAddCard extends ClientGenericMessage{
    private final Boolean flip;
    private final PlayingCard cardToAdd;
    private final PlayingCard cardOnBoard;
    private final int corner;

    public MessageAddCard(String nickname, PlayingCard cardToAdd, PlayingCard cardOnBoard, int corner, Boolean flip){
        this.flip=flip;
        this.cardOnBoard=cardOnBoard;
        this.cardToAdd=cardToAdd;
        this.corner=corner;
        this.isForMainController = false;
        this.nickname = nickname;
    }


    @Override
    public GameControllerInterface launchMessage(MainControllerInterface mainControllerInterface) throws RemoteException {
        return null;
    }

    @Override
    public void launchMessage(GameControllerInterface gameControllerInterface) throws RemoteException {
        gameControllerInterface.addCard(nickname,cardToAdd,cardOnBoard,corner,flip);
    }
}
