package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultControllerValues;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Timer reconnection.
 */
public class TimerReconnection extends Thread{
    private final Game model;
    private int time;

    /**
     * Instantiates a new Timer reconnection.
     *
     * @param model the model
     */
    public TimerReconnection(Game model){
        time=0;
        this.model=model;
    }

    /**
     * Run Timer reconnection.
     */
    @SuppressWarnings("BusyWait")
    @Override
    public void run(){
        boolean interrupt=false;
        while (!this.isInterrupted() && !interrupt){
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                ConsolePrinter.consolePrinter("Time stopped");
                interrupt=true;
            }
            if(time%100==0){
                ConsolePrinter.consolePrinter("Timer Started in game "+model.getGameId()+": "+time/100+"/"+DefaultControllerValues.timeReconnection/100);
            }
            time++;
            if(time>= DefaultControllerValues.timeReconnection){
                model.setStatus(GameStatus.ENDED);
                List<Player> players = new ArrayList<>(model.getPlayers());
                if (players.getFirst().getCurrentPoints()==0){
                    players.getFirst().increasePoints(1);
                }
                for(Player p : model.getPlayersDisconnected()){
                    p.resetPoints();
                    players.add(p);
                }
                model.getGameListenersHandler().notify_gameGenericError("Out of time");
                model.getGameListenersHandler().notify_winners(players);
                break;
            }

        }

        ConsolePrinter.consolePrinter("Time stopped");

    }
}
