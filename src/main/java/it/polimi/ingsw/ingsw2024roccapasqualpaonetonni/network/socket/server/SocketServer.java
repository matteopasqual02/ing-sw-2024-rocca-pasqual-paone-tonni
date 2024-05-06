package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer extends Thread {
    private ServerSocket serverSocket;
    private List<ClientRequestHandler> clientRequestHandlerList;

    public void start(int port){
        try{
            serverSocket = new ServerSocket(port);
            clientRequestHandlerList = new ArrayList<>();
            this.start();
            consolePrinter("[READY] SOCKET SERVER");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[ERROR] STARTING SOCKET SERVER ");
        }
    }

    public void run(){
        try{
            while (!Thread.interrupted()){
                Socket clientSocket = serverSocket.accept();
                ClientRequestHandler clientRequestHandler = new ClientRequestHandler(clientSocket);
                clientRequestHandlerList.add(clientRequestHandler);
                clientRequestHandler.start();
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
