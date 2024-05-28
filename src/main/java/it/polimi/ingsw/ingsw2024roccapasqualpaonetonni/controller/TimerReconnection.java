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
    private Boolean running;
    private final Game model;
    private int time;

    /**
     * Instantiates a new Timer reconnection.
     *
     * @param model the model
     */
    public TimerReconnection(Game model){
        time=0;
        running=false;
        this.model=model;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run(){
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(running){
                if(time%100==0){
                    ConsolePrinter.consolePrinter("Timer Started in game "+model.getGameId()+": "+time/100+"/"+DefaultControllerValues.timeReconnection/100);
                }
                time++;
                if(time>= DefaultControllerValues.timeReconnection){
                    model.setStatus(GameStatus.ENDED);
                    List<Player> players = new ArrayList<>(model.getPlayers());
                    model.getGameListenersHandler().notify_gameGenericError("Out of time");
                    model.getGameListenersHandler().notify_winners(players);
                    break;
                }
            }
        }

    }

    /**
     * Start my timer.
     */
    public void startMyTimer() {
        model.getGameListenersHandler().notify_gameGenericError("Timer Started: "+DefaultControllerValues.timeReconnection+" seconds");
        time=0;
        running = true;
    }

    /**
     * Stop my timer.
     */
    public void stopMyTimer() {
        model.getGameListenersHandler().notify_gameGenericError("timer stopped");
        running=false;
        time=0;
    }
}
