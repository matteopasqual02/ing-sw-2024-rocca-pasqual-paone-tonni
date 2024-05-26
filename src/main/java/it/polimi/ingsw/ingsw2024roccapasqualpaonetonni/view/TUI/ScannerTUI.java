package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;

/**
 * The type Scanner tui.
 */
public class ScannerTUI extends Thread {
    /**
     * The Client.
     */
    private final Client client;
    /**
     * The Buffered reader.
     */
    private final BufferedReader bufferedReader;

    /**
     * Instantiates a new Scanner tui.
     *
     * @param client the client
     */
    public ScannerTUI(Client client){
        this.client = client;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.start();
    }

    /**
     * Run.
     */
    public void run(){
        while(!Thread.interrupted()){
            try {
                String input = bufferedReader.readLine();
                client.receiveInput(input);
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
