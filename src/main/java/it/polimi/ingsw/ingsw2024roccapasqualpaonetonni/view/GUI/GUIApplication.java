package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIApplication extends Application {
    private Parent createContent(){
        return new StackPane(new Text("Hello world!"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent(),300,300));
        stage.show();
    }
}
