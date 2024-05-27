package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultControllerValues;

import java.util.ArrayList;
import java.util.List;

public class MyTimer extends Thread{
    private Boolean running;
    private final Game model;
    private int time;

    public MyTimer(Game model){
        time=0;
        running=false;
        this.model=model;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run(){
        while (true){
            if(running){
                model.getGameListenersHandler().notify_gameGenericError("Timer Started: "+time+" seconds");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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

    public void startMyTimer() {
        model.getGameListenersHandler().notify_gameGenericError("Timer Started: "+DefaultControllerValues.timeReconnection+" seconds");
        time=0;
        running = true;
    }

    public void stopMyTimer() {
        model.getGameListenersHandler().notify_gameGenericError("timer stopped");
        running=false;
        time=0;
    }
}
