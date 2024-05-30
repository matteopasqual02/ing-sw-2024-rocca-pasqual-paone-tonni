package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUIApplication extends Application {
    private Client client;
    private static GUIApplication instance;
    private Stage stage;
    private Stage scoreBoardStage;
    private Parent root;
    Parent rootScore = null;
    private StackPane joinedGameRoot;
    private JoinedGameController joinedGameController = null;
    private GameSceneController gameSceneController = null;
    private ScoreBoardController scoreBoardController = null;
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
        instance = this;

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
    public static GUIApplication getInstance() {
        return instance;
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
        setAlert("no games available, retry","Error","Message:", Alert.AlertType.ERROR, "/Lobby.fxml");
    }
    public void show_all(GameImmutable gameImmutable, String nickname, boolean myTurn){
        ConsolePrinter.consolePrinter("Game started");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameScene_noGrid.fxml"));
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
        infoBox();
        scoreBoardController.setStartingPawns(gameImmutable);
    }
    public void infoBox(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("How to play the game:");
 /*       VBox box = new VBox();
        HBox hBox1 = new HBox();
        box.setSpacing(50);
        Text infoStart = new Text("Quando sarà il tuo turno piazza una carta di tipo starting al centro della board");
        ImageView image = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_cards_back/083.png")));
        image.setFitWidth(50);
        image.setPreserveRatio(true);
        hBox1.getChildren().addAll(infoStart,image);
        Text infoPlace = new Text("Scegli una delle carte nella tua mano e clicca la posizione sulla board in cui vorresti inserirla");
        Text infoDraw = new Text("pesca una carta, resource o gold, o dal mazzo o dalle carte presenti sul tavolo");
        Text infoOther = new Text("Per osservare le board degli altri giocatori premere see board");
        box.getChildren().addAll(hBox1,infoPlace,infoDraw,infoOther);
        ScrollPane page = new ScrollPane(box);
        alert.getDialogPane().setContent(page);
        alert.getDialogPane().setStyle("-fx-background-color: #F5F5DC; -fx-text-fill: #333; -fx-font-family: Serif; -fx-font-size: 16px;-fx-font-weight: bold;");
*/
        VBox page = new VBox();
        page.setSpacing(10);
        Text info = new Text("When it's your turn, place a card of the type \"Starting\" in the middle of the board");
        info.setWrappingWidth(300);
        ImageView image = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_cards_back/083.png")));
        image.setFitWidth(100);
        image.setPreserveRatio(true);
        final Label pageIndex = new Label("1");
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        hBox1.getChildren().addAll(info,image);
        Button next = new Button("Next →");
        Button prev = new Button("← Go Back");
        hBox2.getChildren().addAll(prev,next,pageIndex);
        page.getChildren().addAll(hBox1,hBox2);
        //alert.getButtonTypes().remove(ButtonType.OK);
        //alert.getButtonTypes().add(ButtonType.CLOSE);
        alert.getDialogPane().setContent(page);
        gameSceneController.glowInfo("start");
/*
        GridPane dialogPane = (GridPane) alert.getDialogPane().lookup(".header-panel");
        dialogPane.getColumnConstraints().forEach(constraint -> constraint.setHgrow(Priority.NEVER));
*/
        //alert.setOnCloseRequest(event -> alert.close());

        next.setOnAction(e -> {
            if ("1".equals(pageIndex.getText())) {
                info.setText("Chose one of the cards in your hand and click the corner of the card on the board where you want to attach it");
                image.setImage(new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_cards_back/053.png"))).getImage());
                pageIndex.setText("2");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("hand");
            } else if("2".equals(pageIndex.getText())){
                info.setText("Draw a card, \"Resource\" or \"Gold\", from the decks or from the cards on the drawable board");
                pageIndex.setText("3");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("deck");
            }else{
                info.setText("Click on the button \"See Board\" next to a player to see his playing board");
                pageIndex.setText("4");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("others");
                /*next.setText("fine");
                next.setOnMouseClicked((MouseEvent event) ->{alert.close();});*/
            }
        });
        prev.setOnAction(e -> {
            if ("1".equals(pageIndex.getText())) {
                info.setText("Quando sarà il tuo turno piazza una carta di tipo starting al centro della board");
                pageIndex.setText("1");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("start");
            } else if("2".equals(pageIndex.getText())){
                info.setText("Quando sarà il tuo turno piazza una carta di tipo starting al centro della board");
                pageIndex.setText("1");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("start");
            } else if("3".equals(pageIndex.getText())){
                info.setText("Scegli una delle carte nella tua mano e clicca la posizione sulla board in cui vorresti inserirla");
                pageIndex.setText("2");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("hand");
            } else {
                info.setText("pesca una carta, resource o gold, o dal mazzo o dalle carte presenti sul tavolo");
                pageIndex.setText("3");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("deck");
            }
        });
        alert.getDialogPane().setStyle("-fx-background-color: #F5F5DC; -fx-text-fill: #333; -fx-font-family: Serif; -fx-font-size: 16px;-fx-font-weight: bold;");
        alert.showAndWait();
        gameSceneController.stopGlowInfo();
    }
    public void setAlert(String message, String title, String header, Alert.AlertType alertType, String fxml){
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
            switch (type) {
                case "Pub":
                    gameSceneController.displayChatPublic(message);
                case "Priv":
                    gameSceneController.displayChatPrivate(message);
                case null, default:
            }
        }
    }

    public void show_startCard(GameImmutable gameImmutable, String nickname, boolean myTurn) {
        if (myTurn) {
            gameSceneController.startCard(gameImmutable, nickname);
        }
    }

    public void show_board(GameImmutable gameImmutable, String nickname, boolean myTurn) {
        if (myTurn) {
            gameSceneController.updateBoard(gameImmutable, nickname);
        }
        scoreBoardController.updateScoreBoard(gameImmutable,nickname);
    }

    public void show_objective(GameImmutable gameImmutable, String nickname, boolean myTurn) {
        if (myTurn) {
            gameSceneController.chosenGoal();
        }
    }

    public void myRunningTurnChoseObjective() {
        gameSceneController.myRunningTurnChoseObjective();
    }

    public void myRunningTurnPlaceCard() {
        gameSceneController.myRunningTurnPlaceCard();
    }

    public void myRunningTurnDrawCard() {
        gameSceneController.myRunningTurnDrawCard();
    }

    public void notMyTurn() {
        gameSceneController.notMyTurn();
    }

    public void chatBeforeStart() {
    }

    public void show_status(String s) {
    }

    public void show_statusLast(String string) {
    }

    public void show_generic(String msg) {
    }

    public void invalidAction(String s) {
    }

    public void seeScoreBoard() {
        /*Stage scoreBoardStage = new Stage();
        scoreBoardStage.setTitle("Score Board");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ScoreBoard.fxml"));
        Parent rootScore = null;
        try {
            rootScore = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scoreBoardController = loader.getController();
        scoreBoardController.setParameters(executor, client,this);
        scoreBoardStage.setMinWidth(325);
        scoreBoardStage.setMinHeight(640);
        scoreBoardStage.setScene(new Scene(rootScore, 300, 200));*/
        scoreBoardStage.show();
    }

    public void setClient(Client client) {
        this.client=client;
    }

    public void setScoreBoard() {
        scoreBoardStage = new Stage();
        scoreBoardStage.setTitle("Score Board");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ScoreBoard.fxml"));
        Parent rootScore = null;
        try {
            rootScore = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scoreBoardController = loader.getController();
        scoreBoardController.setParameters(executor, client,this);
        scoreBoardStage.setMinWidth(325);
        scoreBoardStage.setMinHeight(640);
        scoreBoardStage.setFullScreen(false);
        scoreBoardStage.setResizable(false);
        scoreBoardStage.setScene(new Scene(rootScore, 300, 200));
    }

    public void show_boardDeck(GameImmutable gameImmutable, String nickname, boolean myTurn) {
        gameSceneController.updateDrawableBoard(gameImmutable, nickname);
        if (myTurn) {
            gameSceneController.updateHand();
        }
    }
}
