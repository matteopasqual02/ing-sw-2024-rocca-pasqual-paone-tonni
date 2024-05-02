package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;

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
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_addPlayer(String pNickname, int gameId) {
        for(GameListener listener : listenersMap.keySet()) {
            try {
                if (listener.getNickname().equals(pNickname)) {
                    listenersMap.get(listener).sendYouJoinedGame(gameId, pNickname);
                }
                else {
                    listenersMap.get(listener).sendAddedNewPlayer(pNickname);
                }
            } catch (RemoteException e) {
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
            } catch (RemoteException e) {
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
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_playerAlredyIn(Player player) {
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
            } catch (RemoteException e) {
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
            } catch (RemoteException e) {
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
    public void notify_setFirstPlayer(Player first) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                if(listener.getNickname().equals(first.getNickname())){
                    listenersMap.get(listener).sendYouAreFirst();
                }
                else{
                    listenersMap.get(listener).sendFirstPlayerSet(first);
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
            } catch (RemoteException e) {
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
    public void notify_nextPlayer(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                if(listener.getNickname().equals(p.getNickname())){
                    listenersMap.get(listener).sendItsYourTurn();
                }
                else{
                    listenersMap.get(listener).sendNextTurn(p);
                }
            } catch (RemoteException e) {
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
    public void notify_playerIsReadyToStart(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendPlayerIsReady(p);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_setGameDrawableDeck(DrawableDeck d) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendDrawableDeckSet(d);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void notify_setGameBoardDeck(BoardDeck bd) {
        for(GameListener listener : listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendBoardDeckSet(bd);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void notify_gameStarted(GameListener listener) {
    }

    public void notify_messageSent(String txt, String nickname) {
        for(GameListener listener: listenersMap.keySet()){
            try {
                listenersMap.get(listener).sendMessage(txt,nickname);
        } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
}
