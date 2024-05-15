package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.events;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;

public class ScannerTUI extends Thread implements ScannerInterface{
    private final Client client;
    BufferedReader bufferedReader;

    public ScannerTUI(Client client){
        this.client = client;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.start();
    }
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
