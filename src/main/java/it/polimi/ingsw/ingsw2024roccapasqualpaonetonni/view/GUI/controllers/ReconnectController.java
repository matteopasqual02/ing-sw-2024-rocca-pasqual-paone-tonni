package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ExecutorService;

/**
 * The type Reconnect controller.
 */
public class ReconnectController extends GenericController {
    /**
     * The Button.
     */
    @FXML
    public Button button;
    /**
     * The String 1.
     */
    @FXML
    private TextField string1;
    /**
     * The String 2.
     */
    @FXML
    private TextField string2;
    /**
     * The Executor.
     */
    private ExecutorService executor;
    /**
     * The Client.
     */
    private Client client;
    /**
     * The Game id.
     */
    private int gameID;

    /**
     * Set parameters.
     *
     * @param executor    the executor
     * @param client      the client
     * @param application the application
     */
    @Override
    public void setParameters(ExecutorService executor, Client client, GUIApplication application){
        this.executor = executor;
        this.client = client;
    }

    /**
     * Handle button click.
     */
    @FXML
    public void handleButtonClick(){

        String name = string1.getText();
        try{
            gameID = Integer.parseInt(string2.getText());
        }catch (NumberFormatException e){

        }
        executor.submit(()->{
            try {
                client.receiveInput("/reconnect "+ name + " "+ gameID);
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
