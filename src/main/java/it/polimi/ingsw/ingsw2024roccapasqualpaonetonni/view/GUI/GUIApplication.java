package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUIApplication extends Application {
    /**
     * we use a ThreadPoolExecutor to execute bacground tasks that call alow actions on the server
     */
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Parent createContent(){
        return new StackPane(new Text("Hello world!"));
    }
    @Override
    public void start(Stage stage) throws Exception {
        Button button = new Button("New game");
        StackPane root = new StackPane();
        root.getChildren().add(button);

        button.setOnAction(event -> {
            executor.submit(()->{
                //client.newGame...
            });
        });

        stage.setScene(new Scene(root,300,300));
        stage.setTitle("Codex naturalis");
        stage.show();
    }
    public void joinLobby(){

    }
}
