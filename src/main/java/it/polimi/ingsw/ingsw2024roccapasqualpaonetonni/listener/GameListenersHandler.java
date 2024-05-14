package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

/*
this class handles the listeners of the game class in the model: the listeners are elements related to each client, when a change
occurs in the model (the controller does something) one of these methods gets called in order to show the
change to each of the clients. These methods call other methods on the actual single listener giving it the change that has occurred

these are not hte methods that directly notify the clients, they are the ones that call those methods on all of the clients.
 */
public class GameListenersHandler extends ListenersHandler implements Serializable {
    public GameListenersHandler(){
        super();
    }

    public void notify_All(Game game) {
        for(GameListener listener: listenersMap.keySet()){
            try {
                int i=0;
                listenersMap.get(listener).sendAll(new GameImmutable(game));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void notify_ping(String client) {
        for (GameListener listener: listenersMap.keySet()) {
            try {
                if (client.equals(listener.getNickname())) {
                    try {
                        listenersMap.get(listener).sendPing();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }}
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_setMaxNumPlayers(int gameId, int max) {
        for(GameListener listener: listenersMap.keySet()){
            try {
                int i=0;
                listenersMap.get(listener).sendMaxNumPlayersSet(gameId,max);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public void notify_createdGame(int gameId) {
        for(GameListener listener: listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendCreatedGame(gameId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_addPlayer(String pNickname, int gameId) {
        for(GameListener listener : listenersMap.keySet()) {
            try {
                if (listener.getNickname().equals(pNickname)) {
                    try {
                        listenersMap.get(listener).sendYouJoinedGame(gameId);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    listenersMap.get(listener).sendAddedNewPlayer(pNickname);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_noAvailableGame(String nickname) {
        for(GameListener listener : listenersMap.keySet()) {
            try {
                if (listener.getNickname().equals(nickname)) {
                    listenersMap.get(listener).sendNoAvailableGame();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_gameFull(Player player) {
        for(GameListener listener : listenersMap.keySet()) {
            try {
                if (listener.getNickname().equals(player.getNickname())) {
                    listenersMap.get(listener).sendFullGame();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_playerAlreadyIn(Player player) {
        for(GameListener listener : listenersMap.keySet()) {
            try {
                if (listener.getNickname().equals(player.getNickname())) {
                    listenersMap.get(listener).sendNameAlreadyInGame();
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_askPlayersReady() {
        for(GameListener listener : listenersMap.keySet()) {
            try {
                listenersMap.get(listener).sendAskPlayersReady();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_removePlayer(String pNickname) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                if(listener.getNickname().equals(pNickname)){
                    listenersMap.get(listener).sendYouWereRemoved(pNickname);
                }
                else {
                    listenersMap.get(listener).sendPlayerRemoved(pNickname);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_reconnectPlayer(String nickname) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                if(listener.getNickname().equals(nickname)){
                    listenersMap.get(listener).youWereReconnected();
                }
                else{
                    listenersMap.get(listener).sendReconnectedPlayer(nickname);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_reconnectionImpossible(String nickname) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                if(listener.getNickname().equals(nickname)){
                    listenersMap.get(listener).sendReconnectionImpossible(nickname);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_disconnectedPlayer(String nickname) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                if(!listener.getNickname().equals(nickname)){
                    listenersMap.get(listener).sendDisconnectedPlayer(nickname);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_disconnectionImpossible(String nickname) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                if(listener.getNickname().equals(nickname)){
                    listenersMap.get(listener).sendDisconnectedPlayer(nickname);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void notify_setStatus(GameStatus status) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendStatusSet(status);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_setLastStatus(GameStatus status) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendStatusSetToLastStatus(status);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_resetLastStatus() {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendLastStatusReset();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_nextTurn(String nickname) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendNextTurn(nickname);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_lastTurn() {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendLastTurn();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_messageSent(Message message) {
        for(GameListener listener: listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendMessage(message);
        } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
    public void notify_privateMessageSent(PrivateMessage message) {
        for(GameListener listener: listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendPrivateMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_publicChatLog(String requesterName, List<Message> allMessages) {
        for(GameListener listener: listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendPublicChatLog(requesterName,allMessages);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_privateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) {
        for(GameListener listener: listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendPrivateChatLog(yourName,otherName,privateChat);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_gameGenericError(String s){
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).genericError(s);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_winners(List<Player> list) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).winners(list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
