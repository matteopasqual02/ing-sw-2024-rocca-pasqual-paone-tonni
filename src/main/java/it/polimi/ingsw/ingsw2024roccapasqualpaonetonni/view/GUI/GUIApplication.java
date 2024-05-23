package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumConnectionType;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers.GameSceneController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers.GenericController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers.LobbyController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers.JoinedGameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private StackPane joinedGameRoot;
    private JoinedGameController joinedGameController = null;
    private GameSceneController gameSceneController = null;
    //private int i=0; //used to change the position in which the joined message arrives for each player
    /**
     * we use a ThreadPoolExecutor to execute background tasks that call allow actions on the server
     */
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private Parent createContent(){
        return new StackPane(new Text("Hello world!"));
    }

    /**
     * Start.
     *
     * @param stage the stage
     * @throws Exception the exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        client = new Client(this,Objects.requireNonNull(EnumConnectionType.valueOf(getParameters().getRaw().get(0))));

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Lobby.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Lobby.fxml"));
        root = loader.load();
        LobbyController controller = loader.getController();
        controller.setParameters(executor, client,this);

        stage.setMinWidth(1048);
        stage.setMinHeight(589);
        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Codex Naturalis");
        //stage.setFullScreen(true);
        stage.show();

    }
    public void joinLobby(){
        ConsolePrinter.consolePrinter("joinLobby");
    }
    public void show_createdGame(int gameID){
        String message = String.format("Game created, with GameID: %d", gameID);
        ConsolePrinter.consolePrinter(message);
    }
    public void show_areYouReady(){
        try {
            changeScene("/AreYouReady.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String message = String.format("everyone entered, press y to begin");
        ConsolePrinter.consolePrinter(message);
    }

    public void show_addedNewPlayer(String nickname){
        String message = nickname + " joined this game";
        ConsolePrinter.consolePrinter(message);
        joinedGameController.addNewLabel(message,joinedGameRoot);
    }

    //i'm not using changeScene here because it needs a specific controller to be saved in order to update the file with incoming listeners.
    //when I need to dynamically change the file we need to keep a reference to the controller.
    public void show_youJoinedGame(int gameID) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/JoinedGame.fxml"));
        //Parent newRoot = null;
        //StackPane newRoot = null;
        try {
            joinedGameRoot = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        joinedGameController = loader.getController();
        double currWidth = stage.getWidth();
        double currHeight = stage.getHeight();
        Scene scene = new Scene(joinedGameRoot,currWidth,currHeight);
        stage.setScene(scene);
        stage.setTitle("Codex Naturalis");
        stage.show();
        String message = String.format("Joined game: %d", gameID);
        ConsolePrinter.consolePrinter(message);
    }
    public void show_noAvailableGame(){
        //infoBox("no games available, retry","Error","Message:", Alert.AlertType.ERROR,"/Lobby.fxml");
        infoBox("no games available, retry","Error","Message:", Alert.AlertType.ERROR, "/Lobby.fxml");
    }
    public void show_all(GameImmutable gameImmutable, String nickname){
        ConsolePrinter.consolePrinter("Game started");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameScene_1.fxml"));
        Parent newRoot = null;
        try {
            newRoot = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gameSceneController = loader.getController();

        gameSceneController.setParameters(executor, client,this);
        gameSceneController.setScene(gameImmutable,nickname);

        double currWidth = stage.getWidth();
        double currHeight = stage.getHeight();
        Scene scene = new Scene(newRoot,currWidth,currHeight);
        stage.setScene(scene);
        stage.setTitle("Codex Naturalis");
        stage.show();
    }
    public void infoBox(String message, String title, String header, Alert.AlertType alertType, String fxml){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-background-color: #F5F5DC; -fx-text-fill: #333; -fx-font-family: Serif; -fx-font-size: 16px;-fx-font-weight: bold;");
        alert.setHeight(30);
        alert.setWidth(40);
        alert.showAndWait();
        Platform.runLater(()->{
            try {
                this.changeScene(fxml);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent newRoot = loader.load();
        GenericController controller = loader.getController();

        controller.setParameters(executor, client,this);

        //Stage stage = (Stage) root.getScene().getWindow();
        double currWidth = stage.getWidth();
        double currHeight = stage.getHeight();
        Scene scene = new Scene(newRoot,currWidth,currHeight);
        stage.setScene(scene);
        stage.setTitle("Codex Naturalis");
        stage.show();
    }
    public void changeSceneWithNoController(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent newRoot = loader.load();
        double currWidth = stage.getWidth();
        double currHeight = stage.getHeight();
        Scene scene = new Scene(newRoot,currWidth,currHeight);
        stage.setScene(scene);
        stage.setTitle("Codex Naturalis");
        stage.show();
    }

    public void myRunningTurnPlaceStarting() {
        gameSceneController.myRunningTurnPlaceStarting();
    }

    public void displayChat(String message){}

    public void displayChat(String message, String type) {
        if (gameSceneController != null) {
            ConsolePrinter.consolePrinter("here");
            switch (type) {
                case "Pub":
                    gameSceneController.displayChatPublic(message);
                case "Priv":
                    gameSceneController.displayChatPrivate(message);
                case null, default:

            }
        }
    }

    public void show_startCard(GameImmutable gameImmutable, String nickname) {
    }

    public void show_board(GameImmutable gameImmutable, String nickname) {
    }

    public void show_objective(GameImmutable gameImmutable, String nickname) {
    }
}
