/*package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client.SocketClient;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages.MessageMaxNum;

import java.io.IOException;
import java.io.ObjectInputStream;

public class SocketWrapper {
    /*viene chiamata da listenersHandler quando la connessione è di tipo socket. Prende
    in ingresso dati dall'input stream e li ritrasforma in oggetti normali che puo mettere come parametri alle normali funzioni del GameListener,
    che richiama per essere coerente con RMI
     *//*
    private GameListener listener;
    ObjectInputStream inputStream;

    public SocketWrapper(GameListener lis){
        listener = lis;
        SocketClient socketClient = (SocketClient) listener.getServerStub();
        inputStream = socketClient.getInputStream();
    }

    public void recieveMaxNumPlayersSet() throws IOException, ClassNotFoundException {
        //prendi il dato dallo stream
        MessageMaxNum maxSerialized = (MessageMaxNum) inputStream.readObject();
        listener.maxNumPlayersSet(maxSerialized.getInt());
    }
    //tutte le funzioni cosi
}
*/