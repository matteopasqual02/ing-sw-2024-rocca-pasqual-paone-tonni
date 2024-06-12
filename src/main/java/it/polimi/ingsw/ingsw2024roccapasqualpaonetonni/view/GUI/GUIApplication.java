package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
    private Stage otherBoardsStage;
    private Parent root;
    Parent rootScore = null;
    private StackPane joinedGameRoot;
    private JoinedGameController joinedGameController = null;
    private GameSceneController gameSceneController = null;
    private ScoreBoardController scoreBoardController = null;
    private OtherBoardsController otherBoardsController = null;
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
        Platform.runLater(()-> {
            try {
                changeScene("/AreYouReady.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        String message = String.format("everyone entered, press y to begin");
        ConsolePrinter.consolePrinter(message);
    }

    public void show_addedNewPlayer(String nickname){
        String message = nickname + " joined this game";
        ConsolePrinter.consolePrinter(message);
        Platform.runLater(()-> joinedGameController.addNewLabel(message,joinedGameRoot));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameScene_final.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameScene_noGrid.fxml"));
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
        stage.setFullScreen(true);
        stage.show();
        Platform.runLater(this::infoBox);
        Platform.runLater(()->scoreBoardController.setStartingPawns(gameImmutable));
        Platform.runLater(()->otherBoardsController.setBoards(gameImmutable,nickname));
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
        prev.setVisible(false);

        HBox buttonContainer = new HBox(10);
        buttonContainer.setPadding(new Insets(10));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        buttonContainer.getChildren().addAll(prev, spacer, next);

        //hBox2.getChildren().addAll(prev,next,pageIndex);
        page.getChildren().addAll(hBox1,buttonContainer);
        //alert.getButtonTypes().remove(ButtonType.OK);
        //alert.getButtonTypes().add(ButtonType.CLOSE);
        alert.getDialogPane().setContent(page);
/*
        GridPane dialogPane = (GridPane) alert.getDialogPane().lookup(".header-panel");
        dialogPane.getColumnConstraints().forEach(constraint -> constraint.setHgrow(Priority.NEVER));
*/
        //alert.setOnCloseRequest(event -> alert.close());

        next.setOnAction(e -> {
            if ("1".equals(pageIndex.getText())) {
                info.setText("Choose one of the cards in your hand and click the corner of the card on the board where you want to attach it");
                image.setImage(new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_cards_back/053.png"))).getImage());
                pageIndex.setText("2");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("hand");
                prev.setVisible(true);
            } else if("2".equals(pageIndex.getText())){
                info.setText("Draw a card, \"Resource\" or \"Gold\", from the decks or from the cards on the drawable board");
                pageIndex.setText("3");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("deck");
                next.setVisible(true);
            } else if("3".equals(pageIndex.getText())){
                info.setText("Click on the button \"RuleBook\" to see the official game rules, and click on the button \"InfoBox\" to look back at these rules");
                pageIndex.setText("4");
                gameSceneController.glowInfo("others");
            } else if("4".equals(pageIndex.getText())){
                info.setText("Click on the button \"ScoreBoard\" to see every player's position in the score board");
                pageIndex.setText("5");
                gameSceneController.glowInfo("others");
            }else{
                info.setText("Click on the button \"See Other Board\" to see the other players' board");
                pageIndex.setText("6");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("others");
                next.setVisible(false);
            }
        });
        prev.setOnAction(e -> {
            if ("1".equals(pageIndex.getText())) {
                info.setText("When it's your turn, place a card of the type \"Starting\" in the middle of the board");
                pageIndex.setText("1");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("start");
            } else if("2".equals(pageIndex.getText())){
                info.setText("When it's your turn, place a card of the type \"Starting\" in the middle of the board");
                pageIndex.setText("1");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("start");
                prev.setVisible(false);
            } else if("3".equals(pageIndex.getText())){
                info.setText("Choose one of the cards in your hand and click the corner of the card on the board where you want to attach it");
                pageIndex.setText("2");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("hand");
            } else if("4".equals(pageIndex.getText())){
                info.setText("Draw a card, \"Resource\" or \"Gold\", from the decks or from the cards on the drawable board");
                pageIndex.setText("3");
                gameSceneController.stopGlowInfo();
                gameSceneController.glowInfo("deck");
            } else if("5".equals(pageIndex.getText())){
                info.setText("Click on the button \"RuleBook\" to see the official game rules, and click on the button \"InfoBox\" to look back at these rules");
                pageIndex.setText("4");
                gameSceneController.stopGlowInfo();
            } else if("6".equals(pageIndex.getText())){
                info.setText("Click on the button \"ScoreBoard\" to see every player's position in the score board");
                pageIndex.setText("5");
                gameSceneController.stopGlowInfo();
                next.setVisible(true);
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

        double currWidth = stage.getWidth();
        double currHeight = stage.getHeight();
        //Scene scene = new Scene(newRoot,currWidth,currHeight);
        Scene scene = new Scene(newRoot);
        stage.setScene(scene);
        stage.setWidth(currWidth);
        stage.setHeight(currHeight);
        stage.setScene(scene);
        stage.setMinWidth(1048);
        stage.setMinHeight(589);
    }
    public void changeSceneWithNoController(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent newRoot = loader.load();
        double currWidth = stage.getWidth();
        double currHeight = stage.getHeight();
        Scene scene = new Scene(newRoot);
        //Scene scene = new Scene(newRoot,currWidth,currHeight);
        stage.setScene(scene);
        stage.setWidth(currWidth);
        stage.setHeight(currHeight);
        stage.setMinWidth(1048);
        stage.setMinHeight(589);
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

    public void show_startCard(GameImmutable gameImmutable, String nickname, boolean myTurn, String playerChangedNickname) {
        if (myTurn) {
            gameSceneController.startCard(gameImmutable, nickname);
        }
        else {
            otherBoardsController.insertStartCard(gameImmutable,playerChangedNickname);
        }
    }

    public void show_board(GameImmutable gameImmutable, String nickname, boolean myTurn, String playerChangedNickname) {
        if (myTurn) {
            gameSceneController.updateBoard(gameImmutable, nickname);
        }
        scoreBoardController.updateScoreBoard(gameImmutable);
        gameSceneController.updateOtherPlayersPoints(gameImmutable,playerChangedNickname);
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

    public void invalidAction(String s, boolean myTurn) {
        if (myTurn) {
            ConsolePrinter.consolePrinter("Invalid action: " + s);
            switch (s) {
                case "Conditions not met":
                    gameSceneController.cardNotPlaced("Conditions not met, chose another card!");
                case "Card Invalid Place":
                    gameSceneController.cardNotPlaced("Invalid place chosen, try again!");
            }
        }
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
    public void seeOtherBoards(){
        otherBoardsStage.show();
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

    public void show_boardDeck(GameImmutable gameImmutable, String nickname, boolean myTurn, String playerChangedNickname) {
        if (myTurn) {
            gameSceneController.updateHand();
        }
        gameSceneController.updateOtherPlayersHand(gameImmutable,playerChangedNickname);
        gameSceneController.updateBoardDeck(gameImmutable);
    }

    public void setOtherPlayerBoard() {
        otherBoardsStage = new Stage();
        otherBoardsStage.setTitle("Other Boards");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OtherBoards.fxml"));
        Parent rootOtherBoards = null;
        try {
            rootOtherBoards = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        otherBoardsController = loader.getController();
        otherBoardsController.setParameters(executor, client,this);
        otherBoardsStage.setMinWidth(600);
        otherBoardsStage.setMinHeight(400);
        otherBoardsStage.setFullScreen(false);
        otherBoardsStage.setResizable(false);
        otherBoardsStage.setScene(new Scene(rootOtherBoards, 300, 200));
    }

    public void show_otherPlayerBoard(int cardID, Double coord0, Double coord1, String playerChangedNickname) {
        otherBoardsController.updateOtherBoards(cardID,coord0,coord1,playerChangedNickname);
    }

    public void ruleBook() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Rulebook:");
        VBox page = new VBox();
        page.setSpacing(10);
        ImageView pageImage = new ImageView();
        pageImage.setFitHeight(433.25);
        pageImage.setFitWidth(433.25);
        pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/01.png"))));
        final Label pageIndex = new Label("1");
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        hBox1.getChildren().add(pageImage);
        Button next = new Button("Next →");
        Button prev = new Button("← Go Back");
        prev.setVisible(false);
        HBox buttonContainer = new HBox(10);
        buttonContainer.setPadding(new Insets(10));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        buttonContainer.getChildren().addAll(prev, spacer, next);

        page.getChildren().addAll(hBox1,buttonContainer);
        alert.getDialogPane().setContent(page);

        next.setOnAction(e -> {
            if ("1".equals(pageIndex.getText())) {
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/02.png"))));
                pageIndex.setText("2");
                prev.setVisible(true);
            } else if("2".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/03.png"))));
                pageIndex.setText("3");
                next.setVisible(true);
            }else if("3".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/04.png"))));
                pageIndex.setText("4");
            }else if("4".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/05.png"))));
                pageIndex.setText("5");
            }else if("5".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/06.png"))));
                pageIndex.setText("6");
            }else if("6".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/07.png"))));
                pageIndex.setText("7");
            }else if("7".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/08.png"))));
                pageIndex.setText("8");
            }else if("8".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/09.png"))));
                pageIndex.setText("9");
            }else if("9".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/10.png"))));
                pageIndex.setText("10");
            }else if("10".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/11.png"))));
                pageIndex.setText("11");
            }else if("11".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/12.png"))));
                pageIndex.setText("12");
                next.setVisible(false);
            }
        });
        prev.setOnAction(e -> {
            if ("1".equals(pageIndex.getText())) {
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/01.png"))));
                pageIndex.setText("1");
            } else if("2".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/01.png"))));
                pageIndex.setText("1");
                prev.setVisible(false);
            } else if("3".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/02.png"))));
                pageIndex.setText("2");
            } else if("4".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/03.png"))));
                pageIndex.setText("3");
            } else if("5".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/04.png"))));
                pageIndex.setText("4");
            } else if("6".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/05.png"))));
                pageIndex.setText("5");
            } else if("7".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/06.png"))));
                pageIndex.setText("6");
            } else if("8".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/07.png"))));
                pageIndex.setText("7");
            } else if("9".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/08.png"))));
                pageIndex.setText("8");
            } else if("10".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/09.png"))));
                pageIndex.setText("9");
            } else if("11".equals(pageIndex.getText())){
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/10.png"))));
                pageIndex.setText("10");
            } else {
                pageImage.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_Rulebook/11.png"))));
                pageIndex.setText("11");
                next.setVisible(true);
            }
        });
        alert.getDialogPane().setStyle("-fx-background-color: #F5F5DC; -fx-text-fill: #333; -fx-font-family: Serif; -fx-font-size: 16px;-fx-font-weight: bold;");
        alert.showAndWait();
    }
}
