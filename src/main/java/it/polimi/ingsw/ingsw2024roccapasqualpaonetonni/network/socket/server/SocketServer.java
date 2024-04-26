package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server.ClientRequestHandler;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer extends Thread {
    private ServerSocket serverSocket;
    private List<ClientRequestHandler> clientRequestHandlerList;

    public void start(int socketPort){
        try{
            serverSocket = new ServerSocket(socketPort);
            clientRequestHandlerList = new ArrayList<>();
            this.start();
            consolePrinter("[SOCKET] Server Ready");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING SOCKET SERVER ");
        }

    }

    public void run(){
        try{
            while (!Thread.interrupted()){
                clientRequestHandlerList.add(new ClientRequestHandler(serverSocket.accept()));
                clientRequestHandlerList.getLast().start();
                consolePrinter("[SOCKET] new connection");
            }
        } catch (IOException e) {
            System.err.println("[ERROR] SOCKET CONNECTION ERROR");
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void interruptConnection(){
        if(clientRequestHandlerList != null){
            for(ClientRequestHandler clientRequestHandler : clientRequestHandlerList){
                clientRequestHandler.interruptThread();
            }
        }
        this.interrupt();
    }

}
