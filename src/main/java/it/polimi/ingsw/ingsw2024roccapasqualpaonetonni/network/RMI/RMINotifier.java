package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.BoardDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * The type Rmi notifier.
 */
public class RMINotifier extends UnicastRemoteObject implements NotifierInterface {
    private final GameListener listener;

    /**
     * Instantiates a new Rmi notifier.
     *
     * @param g the g
     * @throws RemoteException the remote exception
     */
    public RMINotifier(GameListener g) throws RemoteException {
        listener = g;
    }

    @Override
    public void sendAll(GameImmutable gameImmutable) {
        try{
            listener.allGame(gameImmutable);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendPing() {
        try {
            listener.ping();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void genericError(String s) {
        try {
            listener.genericError(s);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void winners(List<Player> list) {
        try {
            listener.winners(list);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //--------------------------GAME CREATION PHASE
    @Override
    public void sendMaxNumPlayersSet(int gameId, int max) {
        try {
            listener.maxNumPlayersSet(max);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendCreatedGame(int gameId) {
        try {
            listener.createdGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendYouJoinedGame(int gameId) {
        try {
            listener.youJoinedGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendAddedNewPlayer(String pNickname) {
        try {
            listener.addedNewPlayer(pNickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendNoAvailableGame() {
        try {
            listener.noAvailableGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendAskPlayersReady() {
        try {
            listener.areYouReady();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendFullGame() {
        try {
            listener.fullGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void sendNameAlreadyInGame() {
        try {
            listener.nameAlreadyInGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendPlayerRemoved(String pNickname) {
        try {
            listener.playerRemoved(pNickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendNextTurn(String nickname) {
        try {
            listener.nextTurn(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendLastTurn() {
        try {
            listener.lastTurn();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendReconnectedPlayer(String nickname) {
        try {
            listener.reconnectedPlayer(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendReconnectionImpossible(String nickname) {
        try {
            listener.reconnectionImpossible(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendDisconnectedPlayer(String nickname) {
        try {
            listener.disconnectedPlayer(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public void sendStatusSet(GameStatus status) {
        try {
            listener.statusSet(status);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendStatusSetToLastStatus(GameStatus status) {
        try {
            listener.statusSetToLastStatus(status);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendLastStatusReset() {
        try {
            listener.lastStatusReset();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendStartAdded(Player p) {
        try {
            listener.startAdded(p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendCardAdded(Player p) {
        try {
            listener.cardAdded(p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPersonalGoalChosen(Player p) {
        try {
            listener.personalGoalChosen(p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendResourceDrawn(Player p, DrawableDeck d) {
        try {
            listener.resourceDrawn(p,d);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendGoldDrawn(Player p, DrawableDeck d) {
        try {
            listener.goldDrawn(p,d);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendDrewFromBoard(Player p, BoardDeck b, DrawableDeck d) {
        try {
            listener.drewFromBoard(p,b,d);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void sendYouWereRemoved(String pNickname) {

    }

    @Override
    public void youWereReconnected() {

    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        try {
            listener.newMessage(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPrivateMessage(PrivateMessage message) throws IOException {
        try {
            if(listener.getNickname().equals(message.getReceiver())){
                listener.newPrivateMessage(message);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPublicChatLog(String requesterName, List<Message> allMessages) throws IOException {
        try {
            if(listener.getNickname().equals(requesterName)){
                listener.publicChatLog(allMessages);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPrivateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) throws IOException {
        try {
            if(listener.getNickname().equals(yourName)){
                listener.privateChatLog(otherName,privateChat);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
