package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;

public class SocketWrapper {
    /*viene chiamata da listenersHandler quando la connessione è di tipo socket. Prende
    in ingresso dati dall'input stream e li ritrasforma in oggetti normali che puo mettere come parametri alle normali funzioni del GameListener,
    che richiama per essere coerente con RMI
     */
    private GameListener listener;
    public SocketWrapper(GameListener lis){
        listener = lis;
    }

    public void recieveMaxNumPlayersSet(){
        //prendi il dato dallo stream
        //listener.maxNumPlayersSet(int max);
    }
    //tutte le funzioni cosi
}
