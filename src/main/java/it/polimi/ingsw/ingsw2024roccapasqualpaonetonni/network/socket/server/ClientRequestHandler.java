package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.MainController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages.ClientGenericMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;

public class ClientRequestHandler extends Thread {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private GameControllerInterface gameControllerInterface;
    private final BlockingQueue<ClientGenericMessage> processingQueue = new LinkedBlockingQueue<>();


    public ClientRequestHandler(Socket socket) throws IOException {
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());

        //link the server's output to client
    }

    public void interruptThread() {
        this.interrupt();
    }

    @Override
    public void run(){
        var messageThread = new Thread(this::takeExecute);
        messageThread.start();

        try{
            ClientGenericMessage message;
            while(!this.isInterrupted()){
                try{
                    message = (ClientGenericMessage) inputStream.readObject();
                    processingQueue.add(message);

                }catch (IOException | ClassNotFoundException e) {
                    consolePrinter("[SOCKET] no more with the client");
                }

            }

        }finally {
            messageThread.interrupt();
        }

    }

    private void takeExecute(){
        ClientGenericMessage message;

        try{
            while(!this.isInterrupted()){
                message = processingQueue.take();

                if(message.isForMainController()){
                    gameControllerInterface = message.launchMessage(MainController.getInstance());
                }
                else{
                    message.launchMessage(gameControllerInterface);
                }
            }
        }
        catch (InterruptedException ignored){

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
