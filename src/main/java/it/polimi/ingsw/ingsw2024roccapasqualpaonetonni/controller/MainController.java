package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;

import java.rmi.RemoteException;
import java.util.*;

public class MainController implements MainControllerInterface {
    private static MainController instance = null;

    private final List<GameController> runningGames;

    private MainController() {
        runningGames = new ArrayList<>();
    }

    //singleton
    public synchronized static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    @Override
    public synchronized List<GameController> getRunningGames(){
        return runningGames;
    }

    @Override
    public synchronized GameControllerInterface createGameController(String nickname, int numMaxOfPlayer)throws RemoteException {
        GameController g=new GameController(getRunningGames().size()+1);
        g.setMaxNumberOfPlayer(numMaxOfPlayer);
        g.addPlayer(nickname);
        runningGames.add(g);
        return g;
    }

    @Override
    public synchronized GameControllerInterface joinFirstAvailableGame(String nickname)throws RemoteException{
        List<GameController> gameList = getRunningGames();

        for (GameController i : gameList){
            int playersEqualIn = i.getAllPlayer().stream().filter(p -> p.getNickname().equals(nickname)).toList().size();
            int playersSize = i.getAllPlayer().size();
            int maxSize = i.getMaxNumberOfPlayer();
            if(playersSize < maxSize && playersEqualIn==0){
                i.addPlayer(nickname);
                return  i;
            }
        }

        return null;
    }

    /*probabilmente questa è sbagliata e da rifare
    @Override
    public synchronized GameControllerInterface joinGame(String nickname, int color, int gameId, GameListener me)throws RemoteException{
        List<GameController> gameList = getRunningGames().stream().filter(x -> (x.getID() == gameId)).toList();
        Player p = new Player(nickname,color);

        if (gameList.size() == 1) {
            if(!gameList.get(0).getGameStatus().equals(GameStatus.ENDED) && gameList.get(0).getAllPlayer().stream().filter(x->x.getNickname().equals(nickname)).toList().size()==1) {
                try {
                    gameList.get(0).addMyselfAsListener(me);
                    gameList.get(0).addPlayer(nickname);
                    return gameList.get(0);
                } catch (GameAlreadyFullException | PlayerAlreadyInException e) {
                    gameList.get(0).removeMyselfAsListener(me);
                }
            }else{
                //lis.gameIdNotExists(idGame);
            }
        } else {
            //This is the only call not inside the model
            //lis.gameIdNotExists(idGame);
        }
        return null;
    }*/

    @Override
    public GameControllerInterface reconnect(String nickname, int idToReconnect) throws RemoteException{
        Player player;
        List<GameController> ris = runningGames.stream().filter(gc -> (gc.getID() == idToReconnect)).toList();
        boolean gameWaiting = ris.getFirst().getGameStatus().equals(GameStatus.WAITING_RECONNECTION);
        if(!ris.isEmpty() && gameWaiting){
            player = ris.getFirst().getAllPlayer().stream().filter(p -> p.getNickname().equals(nickname)).findFirst().orElse(null);
            if(player!=null){
                ris.getFirst().reconnectPlayer(nickname);
                return ris.getFirst();
            }
        }
        return null;
    }

    @Override
    public GameControllerInterface leaveGame(String nickname, int idToDisconnect) throws RemoteException {
        Player p;
        List<GameController> ris = runningGames.stream().filter(x -> (x.getID() == idToDisconnect)).toList();
        if(!ris.isEmpty()){
            p = ris.getFirst().getAllPlayer().stream().filter(pp -> pp.getNickname().equals(nickname)).findFirst().orElse(null);
            if(p!=null){
                ris.getFirst().disconnectPlayer(nickname);
                return ris.getFirst();
            }
        }
        return null;
    }

}
