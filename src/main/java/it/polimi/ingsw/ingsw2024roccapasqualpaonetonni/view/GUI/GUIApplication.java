package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The type Gui application.
 */
public class GUIApplication extends Application {
    /**
     * The Client.
     */
    private Client client;
    /**
     * The GUI application.
     */
    private static GUIApplication instance;
    /**
     * The Stage.
     */
    private Stage stage;
    /**
     * The Scoreboard.
     */
    private Stage scoreBoardStage;
    /**
     * The others board.
     */
    private Stage otherBoardsStage;
    /**
     * The game root.
     */
    private StackPane joinedGameRoot;
    /**
     * The game controller.
     */
    private JoinedGameController joinedGameController = null;
    /**
     * The game scene controller.
     */
    private GameSceneController gameSceneController = null;
    /**
     * The score board controller.
     */
    private ScoreBoardController scoreBoardController = null;
    /**
     * The others board controller.
     */
    private OtherBoardsController otherBoardsController = null;

    /**
     * we use a ThreadPoolExecutor to execute background tasks that call allow actions on the server
     */
    private final ExecutorService executor = Executors.newCachedThreadPool();
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
        Parent root = loader.load();
        LobbyController controller = loader.getController();
        controller.setParameters(executor, client,this);

        stage.setMinWidth(1048);
        stage.setMinHeight(589);
        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Codex Naturalis");

        stage.setOnCloseRequest(event ->
            executor.submit(()->{
                try {
                    client.receiveInput("/leave");
                } catch (IOException | NotBoundException e) {
                    throw new RuntimeException(e);
                };
                stage.hide();}
        ));

        //stage.setFullScreen(true);
        stage.show();

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GUIApplication getInstance() {
        return instance;
    }

    /**
     * Join lobby.
     */
    public void joinLobby(){
        //ConsolePrinter.consolePrinter("joinLobby");
    }

    /**
     * Show created game.
     *
     * @param gameID the game id
     */
    public void show_createdGame(int gameID){
        String message = String.format("Game created, with GameID: %d", gameID);
        //ConsolePrinter.consolePrinter(message);
    }

    /**
     * Show are you ready.
     */
    public void show_areYouReady(){
        Platform.runLater(()-> {
            try {
                changeScene("/AreYouReady.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        String message = "everyone entered, press y to begin";
        //ConsolePrinter.consolePrinter(message);
    }

    /**
     * Show added new player.
     *
     * @param nickname the nickname
     */
    public void show_addedNewPlayer(String nickname){
        String message = nickname + " joined this game";
        //ConsolePrinter.consolePrinter(message);
        Platform.runLater(()-> joinedGameController.addNewLabel(message));
    }

    /**
     * Show you joined game.
     * //Not using changeScene here because it needs a specific controller to be saved in order to update the file with incoming listeners.
     * //when I need to dynamically change the file we need to keep a reference to the controller.
     *
     * @param gameID the game id
     */
    public void show_youJoinedGame(int gameID) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/JoinedGame.fxml"));
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
        stage.setOnCloseRequest(event -> {
            executor.submit(()->{
                try {
                    client.receiveInput("/leave");
                } catch (IOException | NotBoundException e) {
                    throw new RuntimeException(e);
                }
            });
            stage.hide();
        });
        stage.show();
        joinedGameController.setUpController(joinedGameRoot);
        String message = String.format("Joined game: %d", gameID);
        //ConsolePrinter.consolePrinter(message);
    }

    /**
     * Show no available game.
     */
    public void show_noAvailableGame(){
        //infoBox("no games available, retry","Error","Message:", Alert.AlertType.ERROR,"/Lobby.fxml");
        setAlert("no games available, retry","Error","Message:", Alert.AlertType.ERROR, "/Lobby.fxml");
    }

    /**
     * Show all.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     * @param myTurn        the my turn
     */
    public void show_all(GameImmutable gameImmutable, String nickname, boolean myTurn){
        ConsolePrinter.consolePrinter("Show All");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameScene_final.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameScene_noGrid.fxml"));
        Parent newRoot;
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
        stage.setMinWidth(1400);
        stage.setMinHeight(800);
        //stage.setFullScreen(true);
        stage.show();
        Platform.runLater(this::infoBox);
        Platform.runLater(()->scoreBoardController.setStartingPawns(gameImmutable));
        Platform.runLater(() -> otherBoardsController.upadateAll(gameImmutable));
    }

    /**
     * Info box.
     */
    public void infoBox(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("How to play the game:");
        VBox page = new VBox();
        page.setSpacing(10);
        Text info = new Text("When it's your turn, place a card of the type \"Starting\" in the middle of the board");
        info.setWrappingWidth(300);
        ImageView image = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_cards_back/083.png")));
        image.setFitWidth(100);
        image.setPreserveRatio(true);
        final Label pageIndex = new Label("1");
        HBox hBox1 = new HBox();
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

    /**
     * Set alert.
     *
     * @param message   the message
     * @param title     the title
     * @param header    the header
     * @param alertType the alert type
     * @param fxml      the fxml
     */
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

    /**
     * Change scene.
     *
     * @param fxmlFile the fxml file
     * @throws IOException the io exception
     */
    public void changeScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent newRoot = loader.load();
        GenericController controller = loader.getController();

        controller.setParameters(executor, client,this);

        double currWidth = stage.getWidth();
        double currHeight = stage.getHeight();
        Scene scene = new Scene(newRoot);
        stage.setScene(scene);
        stage.setWidth(currWidth);
        stage.setHeight(currHeight);
        stage.setScene(scene);
        stage.setMinWidth(1048);
        stage.setMinHeight(589);
    }

    /**
     * Change scene with no controller.
     *
     * @param fxmlFile the fxml file
     * @throws IOException the io exception
     */
    public void changeSceneWithNoController(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent newRoot = loader.load();
        double currWidth = stage.getWidth();
        double currHeight = stage.getHeight();
        Scene scene = new Scene(newRoot);
        stage.setScene(scene);
        stage.setWidth(currWidth);
        stage.setHeight(currHeight);
        stage.setMinWidth(1048);
        stage.setMinHeight(589);
    }

    /**
     * My running turn place starting.
     */
    public void myRunningTurnPlaceStarting() {
        gameSceneController.myRunningTurnPlaceStarting();
    }

    /**
     * Display chat.
     *
     * @param message the message
     */
    public void displayChat(String message){}

    /**
     * Display chat.
     *
     * @param message the message
     * @param type    the type
     */
    public void displayChat(String message, String type, Boolean age) {
        if (gameSceneController != null) {
            switch (type) {
                case "Pub":
                    gameSceneController.displayChatPublic(message,age);
                    break;
                case "Priv":
                    gameSceneController.displayChatPrivate(message,age);
                    break;
                case null, default:
            }
        }
    }

    /**
     * Show start card.
     *
     * @param gameImmutable         the game immutable
     * @param nickname              the nickname
     * @param myTurn                my turn
     * @param playerChangedNickname the player changed nickname
     */
    public void show_startCard(GameImmutable gameImmutable, String nickname, boolean myTurn, String playerChangedNickname) {
        if (myTurn) {
            //gameSceneController.startCard(gameImmutable, nickname);
            gameSceneController.updateBoard(gameImmutable, nickname);
        }
        else {
            //otherBoardsController.insertStartCard(gameImmutable,playerChangedNickname);
        }
        gameSceneController.updatePlayersSeedCount(gameImmutable,playerChangedNickname);
    }

    /**
     * Show board.
     *
     * @param gameImmutable         the game immutable
     * @param nickname              the nickname
     * @param myTurn                the my turn
     * @param playerChangedNickname the player changed nickname
     */
    public void show_board(GameImmutable gameImmutable, String nickname, boolean myTurn, String playerChangedNickname) {
        if (myTurn) {
            gameSceneController.updateHand(gameImmutable, nickname);
            gameSceneController.updateBoard(gameImmutable, nickname);
        }
        gameSceneController.updateBackHandPlace(gameImmutable, playerChangedNickname);
        scoreBoardController.updateScoreBoard(gameImmutable);
        gameSceneController.updateOtherPlayersPoints(gameImmutable, playerChangedNickname);
        gameSceneController.updatePlayersSeedCount(gameImmutable, playerChangedNickname);
    }

    /**
     * Show objective.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     * @param myTurn        the my turn
     */
    public void show_objective(GameImmutable gameImmutable, String nickname, boolean myTurn) {
        if (myTurn) {
            gameSceneController.chosenGoal();
        }
    }

    /**
     * My running turn chose objective.
     */
    public void myRunningTurnChoseObjective() {
        gameSceneController.myRunningTurnChoseObjective();
    }

    /**
     * My running turn place card.
     */
    public void myRunningTurnPlaceCard() {
        gameSceneController.myRunningTurnPlaceCard();
    }

    /**
     * My running turn draw card.
     */
    public void myRunningTurnDrawCard() {
        gameSceneController.myRunningTurnDrawCard();
    }

    /**
     * Not my turn.
     */
    public void notMyTurn() {
        if (gameSceneController != null) {
            gameSceneController.notMyTurn();
        }
    }

    /**
     * My turn.
     */
    public void myTurn() {
        if (gameSceneController != null) {
            gameSceneController.myTurn();
        }
    }

    /**
     * Update other board.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
    public void updateOtherBoard(GameImmutable gameImmutable, String nickname) {
        if (otherBoardsController == null) {
            setOtherPlayerBoard();
        }
        otherBoardsController.updateOtherBoard(gameImmutable, nickname);
    }

    /**
     * Chat before start.
     */
    public void chatBeforeStart() {
    }

    /**
     * Show status.
     *
     * @param s the s
     */
    public void show_status(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("THE STATUS HAS CHANGED");
        alert.setHeaderText("New Status:");
        switch(s) {
            case "LAST_TURN":
                s = "Last Turn\nWhen it'll be your turn, you will place the last card" +
                        "\nAfter the last player has done it, the points will be counted, by checking the objectives too, " +
                        "and a the winner will be found";
                break;
            case "ENDED":
                s = "The game has ended!\nCheck out how you did";
                break;
            case "WAITING_LAST_TURN", "RUNNING":
                return;
            case "WAITING_RECONNECTION":
                s = "You are the last player remaining\nWaiting for others to rejoin";
                break;
        }

        alert.setContentText(s);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Show status last.
     *
     * @param string the string
     */
    public void show_statusLast(String string) {
    }

    /**
     * Show generic.
     *
     * @param msg the msg
     */
    public void show_generic(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RECONNECTION");
        alert.setContentText(msg);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Invalid action.
     *
     * @param s      the s
     * @param myTurn my turn
     */
    public void invalidAction(String s, boolean myTurn) {
        if (myTurn) {
            //ConsolePrinter.consolePrinter("Invalid action: " + s);
            switch (s) {
                case "Goal invalid Action":
                    //gameSceneController.objectiveNotSelected("Error in objective selection, chose another card!");
                    alertBox("Error in objective selection, chose another card!");
                    gameSceneController.myRunningTurnChoseObjective();
                    break;
                case "Starting Card invalid Action: Card Already Added":
                    //gameSceneController.startAlreadyAdded("Starting card already added");
                    alertBox("Starting card already added");
                    break;
                case "Conditions not met":
                    //gameSceneController.cardNotPlaced("Conditions not met, chose another card!");
                    alertBox("Conditions not met, chose another card!");
                    gameSceneController.myRunningTurnPlaceCard();
                    break;
                case "Card Invalid Place":
                    //gameSceneController.cardNotPlaced("Invalid place chosen, try again!");
                    alertBox("Invalid place chosen, try again!");
                    gameSceneController.myRunningTurnPlaceCard();
                    break;
                case "Card not in Hand":
                    //gameSceneController.cardNotPlaced("Error in the card selection, try again!");
                    alertBox("Error in the card selection, try again!");
                    gameSceneController.myRunningTurnPlaceCard();
                    break;
                case "Not your turn":
                    gameSceneController.notMyTurn();
                    break;
                case "You cannot add two Cards in a turn":
                    //gameSceneController.cardAlreadyAdded(s);
                    alertBox(s);
                    break;
                case "You cannot add a Card in this phase", "You cannot add a Starting Card in this phase",
                     "You cannot choose the Objective Card in this phase",
                     "You cannot draw a Resource Card in this phase", "You cannot draw before a card is placed",
                     "You cannot draw a Gold Card in this phase", "You cannot draw from Common Board in this phase":
                    //gameSceneController.wrongPhase(s);
                    alertBox(s);
                    break;
                case "Resource deck is empty":
                    alertBox(s + ", try drawing somewhere else");
                    gameSceneController.noResourcesDeck(s + ", try drawing somewhere else");
                    break;
                case "Gold deck is empty":
                    alertBox(s + ", try drawing somewhere else");
                    gameSceneController.noGoldDeck(s + ", try drawing somewhere else");
                    break;
                case "This position is empty":
                    alertBox(s + ", try drawing somewhere else");
                    //gameSceneController.noBoardCard(s + ", try drawing somewhere else");
                    gameSceneController.myRunningTurnDrawCard();
                    break;
            }
        }
    }

    /**
     * Alert box.
     *
     * @param message the message
     */
    private void alertBox(String message) {
        // System.out.println("infoBox called with message: " + message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");
        alert.setHeaderText("Invalid Action");
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * See score board.
     */
    public void seeScoreBoard() {
        scoreBoardStage.show();
    }

    /**
     * See other boards.
     *
     * @param nickname the nickname
     */
    public void seeOtherBoards(String nickname){
        otherBoardsController.showBoard(nickname);
        otherBoardsStage.show();
    }

    /**
     * Sets client.
     *
     * @param client the client
     */
    public void setClient(Client client) {
        this.client=client;
    }

    /**
     * Sets score board.
     */
    public void setScoreBoard() {
        scoreBoardStage = new Stage();
        scoreBoardStage.setTitle("Score Board");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ScoreBoard.fxml"));
        Parent rootScore;
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

    /**
     * Show board deck.
     *
     * @param gameImmutable         the game immutable
     * @param nickname              the nickname
     * @param myTurn                the my turn
     * @param playerChangedNickname the player changed nickname
     */
    public void show_boardDeck(GameImmutable gameImmutable, String nickname, boolean myTurn, String playerChangedNickname) {
        if (myTurn) {
            gameSceneController.updateHand(gameImmutable, nickname);
        }
        gameSceneController.updateBackHandDraw(gameImmutable, playerChangedNickname);
        gameSceneController.updateBoardDeck(gameImmutable);
    }

    /**
     * Sets other player board.
     */
    public void setOtherPlayerBoard() {
        otherBoardsStage = new Stage();
        otherBoardsStage.setTitle("Other Boards");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OtherBoards_2.fxml"));
        Parent rootOtherBoards;
        try {
            rootOtherBoards = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        otherBoardsController = loader.getController();
        otherBoardsController.setParameters(executor, client,this);
        otherBoardsStage.setMinWidth(1048);
        otherBoardsStage.setMinHeight(589);
        otherBoardsStage.setFullScreen(false);
        otherBoardsStage.setResizable(false);
        otherBoardsStage.setScene(new Scene(rootOtherBoards, 300, 200));
    }

    /**
     * Winner.
     *
     * @param list the list
     */
    public void winner(List<Player> list) {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Winners.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Winners_final.fxml"));
        AnchorPane winnersRoot;
        try {
            winnersRoot = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        WinnersController winnersController = loader.getController();
        winnersController.showWinners(list);

        double currWidth = stage.getWidth();
        double currHeight = stage.getHeight();
        Scene scene = new Scene(winnersRoot, currWidth, currHeight);
        stage.setScene(scene);
        stage.setTitle("Codex Naturalis");
        stage.show();
    }

    /**
     * Rule book.
     */
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

    public void killGUI() {
        System.exit(0);
    }
}
