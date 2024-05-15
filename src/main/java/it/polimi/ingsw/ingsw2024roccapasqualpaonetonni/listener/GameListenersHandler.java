package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

/**
 * The type Game listeners handler.
 */
/*
this class handles the listeners of the game class in the model: the listeners are elements related to each client, when a change
occurs in the model (the controller does something) one of these methods gets called in order to show the
change to each of the clients. These methods call other methods on the actual single listener giving it the change that has occurred

these are not hte methods that directly notify the clients, they are the ones that call those methods on all of the clients.
 */
public class GameListenersHandler extends ListenersHandler implements Serializable {
    /**
     * Instantiates a new Game listeners handler.
     */
    public GameListenersHandler(){
        super();
    }

    /**
     * Notify all.
     *
     * @param game the game
     */
    public void notify_All(Game game) {
        for(String name: listenersMap.keySet()){
            try {
                int i=0;
                listenersMap.get(name).sendAll(new GameImmutable(game));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Notify ping.
     *
     * @param client the client
     * @throws Exception the exception
     */
    public void notify_ping(String client) throws Exception{
        for (String name: listenersMap.keySet()) {
            if (client.equals(name)) {
                listenersMap.get(name).sendPing();
            }
        }
    }

    /**
     * Notify set max num players.
     *
     * @param gameId the game id
     * @param max    the max
     */
    public void notify_setMaxNumPlayers(int gameId, int max) {
        for(String name: listenersMap.keySet()){
            try {
                int i=0;
                listenersMap.get(name).sendMaxNumPlayersSet(gameId,max);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Notify created game.
     *
     * @param gameId the game id
     */
    public void notify_createdGame(int gameId) {
        for(String name: listenersMap.keySet()){
            try {
                listenersMap.get(name).sendCreatedGame(gameId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify add player.
     *
     * @param pNickname the p nickname
     * @param gameId    the game id
     */
    public void notify_addPlayer(String pNickname, int gameId) {
        for(String name : listenersMap.keySet()) {
            try {
                if (name.equals(pNickname)) {
                    try {
                        listenersMap.get(name).sendYouJoinedGame(gameId);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    listenersMap.get(name).sendAddedNewPlayer(pNickname);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify no available game.
     *
     * @param nickname the nickname
     */
    public void notify_noAvailableGame(String nickname) {
        for(String name : listenersMap.keySet()) {
            try {
                if (name.equals(nickname)) {
                    listenersMap.get(name).sendNoAvailableGame();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify game full.
     *
     * @param player the player
     */
    public void notify_gameFull(Player player) {
        for(String name : listenersMap.keySet()) {
            try {
                if (name.equals(player.getNickname())) {
                    listenersMap.get(name).sendFullGame();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify player already in.
     *
     * @param player the player
     */
    public void notify_playerAlreadyIn(Player player) {
        for(String name : listenersMap.keySet()) {
            try {
                if (name.equals(player.getNickname())) {
                    listenersMap.get(name).sendNameAlreadyInGame();
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify ask players ready.
     */
    public void notify_askPlayersReady() {
        for(String name : listenersMap.keySet()) {
            try {
                listenersMap.get(name).sendAskPlayersReady();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify remove player.
     *
     * @param pNickname the p nickname
     */
    public void notify_removePlayer(String pNickname) {
        for(String name : listenersMap.keySet()){
            try {
                if(name.equals(pNickname)){
                    listenersMap.get(name).sendYouWereRemoved(pNickname);
                }
                else {
                    listenersMap.get(name).sendPlayerRemoved(pNickname);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify reconnect player.
     *
     * @param nickname the nickname
     */
    public void notify_reconnectPlayer(String nickname) {
        for(String name : listenersMap.keySet()){
            try {
                if(name.equals(nickname)){
                    listenersMap.get(name).youWereReconnected();
                }
                else{
                    listenersMap.get(name).sendReconnectedPlayer(nickname);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify reconnection impossible.
     *
     * @param nickname the nickname
     */
    public void notify_reconnectionImpossible(String nickname) {
        for(String name : listenersMap.keySet()){
            try {
                if(name.equals(nickname)){
                    listenersMap.get(name).sendReconnectionImpossible(nickname);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify disconnected player.
     *
     * @param nickname the nickname
     */
    public void notify_disconnectedPlayer(String nickname) {
        for(String name : listenersMap.keySet()){
            try {
                if(!name.equals(nickname)){
                    listenersMap.get(name).sendDisconnectedPlayer(nickname);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify disconnection impossible.
     *
     * @param nickname the nickname
     */
    public void notify_disconnectionImpossible(String nickname) {
        for(String name : listenersMap.keySet()){
            try {
                if(name.equals(nickname)){
                    listenersMap.get(name).sendDisconnectedPlayer(nickname);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Notify set status.
     *
     * @param status the status
     */
    public void notify_setStatus(GameStatus status) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendStatusSet(status);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify set last status.
     *
     * @param status the status
     */
    public void notify_setLastStatus(GameStatus status) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendStatusSetToLastStatus(status);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify reset last status.
     */
    public void notify_resetLastStatus() {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendLastStatusReset();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify next turn.
     *
     * @param nickname the nickname
     */
    public void notify_nextTurn(String nickname) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendNextTurn(nickname);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify last turn.
     */
    public void notify_lastTurn() {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).sendLastTurn();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify message sent.
     *
     * @param message the message
     */
    public void notify_messageSent(Message message) {
        for(String name: listenersMap.keySet()){
            try {
                listenersMap.get(name).sendMessage(message);
        } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}

    /**
     * Notify private message sent.
     *
     * @param message the message
     */
    public void notify_privateMessageSent(PrivateMessage message) {
        for(String name: listenersMap.keySet()){
            try {
                listenersMap.get(name).sendPrivateMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify public chat log.
     *
     * @param requesterName the requester name
     * @param allMessages   the all messages
     */
    public void notify_publicChatLog(String requesterName, List<Message> allMessages) {
        for(String name: listenersMap.keySet()){
            try {
                listenersMap.get(name).sendPublicChatLog(requesterName,allMessages);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify private chat log.
     *
     * @param yourName    the your name
     * @param otherName   the other name
     * @param privateChat the private chat
     */
    public void notify_privateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) {
        for(String name: listenersMap.keySet()){
            try {
                listenersMap.get(name).sendPrivateChatLog(yourName,otherName,privateChat);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify game generic error.
     *
     * @param s the s
     */
    public void notify_gameGenericError(String s){
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).genericError(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Notify winners.
     *
     * @param list the list
     */
    public void notify_winners(List<Player> list) {
        for(String name : listenersMap.keySet()){
            try {
                listenersMap.get(name).winners(list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
