package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumConnectionType;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumViewType;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUIApplication extends Application {
    private Client client;
    /**
     * we use a ThreadPoolExecutor to execute bacground tasks that call alow actions on the server
     */
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Parent createContent(){
        return new StackPane(new Text("Hello world!"));
    }
    @Override
    public void start(Stage stage) throws Exception {
        client = new Client(this,Objects.requireNonNull(EnumConnectionType.valueOf(getParameters().getRaw().get(0))));
        Button button = new Button("New game");
        StackPane root = new StackPane();
        root.getChildren().add(button);

        button.setOnAction(event -> {
            executor.submit(()->{
                try {
                    client.receiveInput("/new a 1");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        stage.setScene(new Scene(root,300,300));
        stage.setTitle("Codex naturalis");
        stage.show();
    }
    public void joinLobby(){
        ConsolePrinter.consolePrinter("new game");
    }
}
