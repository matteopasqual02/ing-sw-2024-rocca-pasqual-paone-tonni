package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumConnectionType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUIApplication extends Application {
    private Client client;
    private Stage stage;
    private Parent root;
    /**
     * we use a ThreadPoolExecutor to execute bacground tasks that call alow actions on the server
     */
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private Parent createContent(){
        return new StackPane(new Text("Hello world!"));
    }
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        client = new Client(this,Objects.requireNonNull(EnumConnectionType.valueOf(getParameters().getRaw().get(0))));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Lobby.fxml"));
        root = loader.load();
        LobbyController controller = loader.getController();
        controller.setParameters(executor, client,this);

        stage.setMinWidth(1024);
        stage.setMinHeight(600);
        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Codex Naturalis");
        stage.show();

    }
    public void joinLobby(){
        ConsolePrinter.consolePrinter("joinLobby");
    }
    public void show_createdGame(int gameID){
        String message = String.format("Game created, with GameID: %d", gameID);
        ConsolePrinter.consolePrinter(message);
    }
    public void show_youJoinedGame(int gameID){
        String message = String.format("Joined game: %d", gameID);
        ConsolePrinter.consolePrinter(message);
    }
    public void show_noAvailableGame(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Error:");
        alert.setContentText("no games available");
        alert.showAndWait();
        Platform.runLater(()->{
            try {
                this.changeScene("/Lobby.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //infoBox("no games available","Error","Message:");
        String message = String.format("no games available");
        ConsolePrinter.consolePrinter(message);
    }
    public static void infoBox(String message, String title, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent newRoot = loader.load();
        GenericController controller = loader.getController();

        controller.setParameters(executor, client);

        //Stage stage = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(newRoot,300,200);
        stage.setScene(scene);
        stage.setTitle("Codex Naturalis");
        stage.show();
    }
}
