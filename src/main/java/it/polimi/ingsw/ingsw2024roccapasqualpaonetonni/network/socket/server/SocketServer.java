package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;

/**
 * The type Socket server.
 */
public class SocketServer extends Thread {
    /**
     * The Server socket.
     */
    private ServerSocket serverSocket;
    /**
     * The Client request handler list.
     */
    private List<ClientRequestHandler> clientRequestHandlerList;

    /**
     * Start.
     *
     * @param port the port
     */
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

    /**
     * Run.
     */
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

    /**
     * Interrupt connection.
     */
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
