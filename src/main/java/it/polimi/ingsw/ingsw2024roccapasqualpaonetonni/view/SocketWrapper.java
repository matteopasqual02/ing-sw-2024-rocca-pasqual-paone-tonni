package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client.SocketClient;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages.SerializableMaxNumPlayers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SocketWrapper {
    /*viene chiamata da listenersHandler quando la connessione è di tipo socket. Prende
    in ingresso dati dall'input stream e li ritrasforma in oggetti normali che puo mettere come parametri alle normali funzioni del GameListener,
    che richiama per essere coerente con RMI
     */
    private GameListener listener;
    public SocketWrapper(GameListener lis){
        listener = lis;
    }

    public void recieveMaxNumPlayersSet() throws IOException, ClassNotFoundException {
        //prendi il dato dallo stream
        SocketClient socketClient = (SocketClient) listener.getServerStub();
        ObjectInputStream inputStream = socketClient.getInputStream();
        SerializableMaxNumPlayers maxSerialized = (SerializableMaxNumPlayers) inputStream.readObject();
        listener.maxNumPlayersSet(maxSerialized.getInt());
    }
    //tutte le funzioni cosi
}
