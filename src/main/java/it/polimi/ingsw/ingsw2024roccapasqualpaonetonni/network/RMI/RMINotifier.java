package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.BoardDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
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
    /**
     * The Listener.
     */
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

    /**
     * Send all.
     *
     * @param gameImmutable the game immutable
     */
    @Override
    public void sendAll(GameImmutable gameImmutable) {
        try{
            listener.allGame(gameImmutable);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send ping.
     */
    @Override
    public void sendPing() {
        try {
            listener.ping();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generic error.
     *
     * @param s the s
     */
    @Override
    public void genericError(String s) {
        try {
            listener.genericError(s);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Winners.
     *
     * @param list the list
     */
    @Override
    public void winners(List<Player> list) {
        try {
            listener.winners(list);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendKill(String nickname) {
        try {
            listener.sendKill(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send max num players set.
     *
     * @param gameId the game id
     * @param max    the max
     */
//--------------------------GAME CREATION PHASE
    @Override
    public void sendMaxNumPlayersSet(int gameId, int max) {
        try {
            listener.maxNumPlayersSet(max);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send created game.
     *
     * @param gameId the game id
     */
    @Override
    public void sendCreatedGame(int gameId) {
        try {
            listener.createdGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send you joined game.
     *
     * @param gameId the game id
     */
    @Override
    public void sendYouJoinedGame(int gameId) {
        try {
            listener.youJoinedGame(gameId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send added new player.
     *
     * @param pNickname the p nickname
     */
    @Override
    public void sendAddedNewPlayer(String pNickname) {
        try {
            listener.addedNewPlayer(pNickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send no available game.
     */
    @Override
    public void sendNoAvailableGame() {
        try {
            listener.noAvailableGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send ask players ready.
     */
    @Override
    public void sendAskPlayersReady() {
        try {
            listener.areYouReady();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Send next turn.
     *
     * @param nickname the nickname
     */
    @Override
    public void sendNextTurn(String nickname) {
        try {
            listener.nextTurn(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }


    /**
     * Send reconnected player.
     *
     * @param nickname the nickname
     */
    @Override
    public void sendReconnectedPlayer(String nickname) {
        try {
            listener.reconnectedPlayer(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Send reconnection impossible.
     *
     * @param nickname the nickname
     */
    @Override
    public void sendReconnectionImpossible(String nickname) {
        try {
            listener.reconnectionImpossible(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Send disconnected player.
     *
     * @param nickname the nickname
     */
    @Override
    public void sendDisconnectedPlayer(String nickname) {
        try {
            listener.disconnectedPlayer(nickname);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }


    /**
     * Send status set.
     *
     * @param status the status
     */
    @Override
    public void sendStatusSet(GameStatus status) {
        try {
            listener.statusSet(status);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Send status set to last status.
     *
     * @param status the status
     */
    @Override
    public void sendStatusSetToLastStatus(GameStatus status) {
        try {
            listener.statusSetToLastStatus(status);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Send last status reset.
     */
    @Override
    public void sendLastStatusReset() {
        try {
            listener.lastStatusReset();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Send start added.
     *
     * @param p the p
     */
    @Override
    public void sendStartAdded(Player p) {
        try {
            listener.startAdded(p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send card added.
     *
     * @param p the p
     */
    @Override
    public void sendCardAdded(Player p,int cardID) {
        try {
            listener.cardAdded(p,cardID);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send personal goal chosen.
     *
     * @param p the p
     */
    @Override
    public void sendPersonalGoalChosen(Player p) {
        try {
            listener.personalGoalChosen(p);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send resource drawn.
     *
     * @param p the p
     * @param d the d
     */
    @Override
    public void sendResourceDrawn(Player p, DrawableDeck d) {
        try {
            listener.resourceDrawn(p,d);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send gold drawn.
     *
     * @param p the p
     * @param d the d
     */
    @Override
    public void sendGoldDrawn(Player p, DrawableDeck d) {
        try {
            listener.goldDrawn(p,d);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send drew from board.
     *
     * @param p the p
     * @param b the b
     * @param d the d
     */
    @Override
    public void sendDrewFromBoard(Player p, BoardDeck b, DrawableDeck d) {
        try {
            listener.drewFromBoard(p,b,d);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Send message.
     *
     * @param message the message
     * @throws RemoteException the remote exception
     */
    @Override
    public void sendMessage(Message message) throws RemoteException {
        try {
            listener.newMessage(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send private message.
     *
     * @param message the message
     * @throws IOException the io exception
     */
    @Override
    public void sendPrivateMessage(PrivateMessage message) throws IOException {
        try {
            if(listener.getNickname().equals(message.getReceiver()) || listener.getNickname().equals(message.getSender())){
                listener.newPrivateMessage(message);
                ConsolePrinter.consolePrinter(listener.getNickname());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send public chat log.
     *
     * @param requesterName the requester name
     * @param allMessages   the all messages
     * @throws IOException the io exception
     */
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

    /**
     * Send private chat log.
     *
     * @param yourName    your name
     * @param otherName   the other name
     * @param privateChat the private chat
     * @throws IOException the io exception
     */
    @Override
    public void sendPrivateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) throws IOException {
        try {
            if(listener.getNickname().equals(yourName)){
                listener.privateChatLog(otherName, privateChat);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
