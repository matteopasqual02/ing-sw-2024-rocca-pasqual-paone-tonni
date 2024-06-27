package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

/**
 * The type Game scene controller.
 */
public class GameSceneController extends GenericController{
    @FXML
    public Label gameId;
    /**
     * The Pane.
     */
    @FXML
    private Pane pane;

    /**
     * The Turn label.
     */
    @FXML
    private Label turnLabel;

    /**
     * The Personal board.
     */

    @FXML
    private ScrollPane personalBoard;

    /**
     * The Board.
     */
    @FXML
    private Pane board;

    /**
     * The Cards h box.
     */

    @FXML
    private HBox cardsHBox;

    /**
     * The Hand cards.
     */
    @FXML
    private HBox handCards;

    /**
     * My hand image 1.
     */
    @FXML
    private ImageView myHandImage1;

    /**
     * My hand image 2.
     */
    @FXML
    private ImageView myHandImage2;

    /**
     * My hand image 3.
     */
    @FXML
    private ImageView myHandImage3;

    /**
     * The Flip hand card.
     */
    @FXML
    private Button flipHandCard;

    /**
     * The Secret objective v box.
     */
    @FXML
    private VBox secretObjectiveVBox;

    /**
     * The Secret objective.
     */
    @FXML
    private VBox secretObjective;

    /**
     * My secret objective 1.
     */
    @FXML
    private ImageView mySecretObjective1;

    /**
     * My secret objective 2.
     */
    @FXML
    private ImageView mySecretObjective2;

    /**
     * The Starting v box.
     */
    @FXML
    private VBox startingVBox;

    /**
     * The Starting card v box.
     */
    @FXML
    private VBox startingCardVBox;

    /**
     * My starting card.
     */
    @FXML
    private ImageView myStartingCard;

    /**
     * The Flip starting button.
     */
    @FXML
    private Button flipStartingButton;

    /**
     * The Board cards.
     */
    @FXML
    private VBox boardCards;

    /**
     * The Board card 1.
     */
    @FXML
    private ImageView boardCard1;

    /**
     * The Board card 2.
     */
    @FXML
    private ImageView boardCard2;

    /**
     * The Board card 3.
     */
    @FXML
    private ImageView boardCard3;

    /**
     * The Board card 4.
     */
    @FXML
    private ImageView boardCard4;

    /**
     * The Decks.
     */
    @FXML
    private HBox decks;

    /**
     * The Deck resources card.
     */
    @FXML
    private ImageView deckResourcesCard;

    /**
     * The Deck gold card.
     */
    @FXML
    private ImageView deckGoldCard;

    /**
     * The Common objective card 1.
     */
    @FXML
    private ImageView commonObjectiveCard1;

    /**
     * The Common objective card 2.
     */
    @FXML
    private ImageView commonObjectiveCard2;

    /**
     * The Other players v box.
     */
    @FXML
    private VBox otherPlayersVBox;

    /**
     * The Receiver private messages.
     */
    @FXML
    private ComboBox receiverPrivateMessages;

    /**
     * The Scroll pane.
     */
    @FXML
    private ScrollPane scrollPane;

    /**
     * The Message container.
     */
    @FXML
    private VBox messageContainer;

    /**
     * The Message input.
     */
    @FXML
    private TextArea messageInput;

    /**
     * The Receiver container.
     */
    @FXML
    private HBox receiverContainer;

    /**
     * The Public chat button.
     */
    @FXML
    private Button publicChatButton;

    /**
     * The Private chat button.
     */
    @FXML
    private Button privateChatButton;

    /**
     * The Goal.
     */
    private int goal = 0;
    /**
     * The Hand.
     */
    private int hand = -1;
    /**
     * The Selected card.
     */
    private ImageView selectedCard = null;
    /**
     * The Flipped starting.
     */
    private boolean flippedStarting = false;
    /**
     * The Starting id.
     */
    private int startingID = -1;
    /**
     * The Flipped hand.
     */
    private final boolean[] flippedHand = {false, false, false};
    /**
     * The Hand i ds.
     */
    private final int[] handIDs = {-1, -1, -1};
    /**
     * The Card size.
     */
    private final double[] CARD_SIZE = {88.0, 132.0};
    /**
     * The Board size.
     */
    private final double[] BOARD_SIZE = {1500.0, 2000.0};

    /**
     * The constant JUMP_HEIGHT.
     */
    private static final double JUMP_HEIGHT = 20.0;
    /**
     * The constant ANIMATION_DURATION.
     */
    private static final Duration ANIMATION_DURATION = Duration.millis(200);

    /**
     * The Executor.
     */
    private ExecutorService executor;
    /**
     * The Client.
     */
    private Client client;
    /**
     * The Application.
     */
    private GUIApplication application;
    /**
     * The Is private chat.
     */
    private boolean isPrivateChat = false;
    /**
     * The Player.
     */
    private Player player;
    /**
     * Saves info about the label for the turn of the player
     */
    private String myTurn = "Not your turn";
    /**
     * Needed to block the cards in waiting reconnection, and unlock them after
     */
    private boolean handCardsDisable = false;

    /**
     * Set parameters.
     *
     * @param executor    the executor
     * @param client      the client
     * @param application the application
     */
    public void setParameters(ExecutorService executor, Client client,GUIApplication application){
        this.executor = executor;
        this.client = client;
        this.application = application;
    }

    /**
     * Set scene.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
    public void setScene(GameImmutable gameImmutable, String nickname){
        int cardId;
        this.player = gameImmutable.getPlayers().stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);

        //setting hand
        if(player==null) return;

        gameId.setText("GameId: "+ gameImmutable.getGameId());
        gameId.setStyle("-fx-alignment: CENTER;");

        board.setDisable(true);
        board.setPrefHeight(BOARD_SIZE[0]);
        board.setPrefWidth(BOARD_SIZE[1]);
        personalBoard.setHvalue(0.5);
        personalBoard.setVvalue(0.5);

        cardId = player.getHand().get(0).getIdCard();
        myHandImage1.setImage(new Image(createPath(cardId)));
        myHandImage1.setDisable(true);
        handIDs[0] = cardId;
        cardId = player.getHand().get(1).getIdCard();
        myHandImage2.setImage(new Image(createPath(cardId)));
        myHandImage2.setDisable(true);
        handIDs[1] = cardId;
        if(player.getHand().size()==3){
            cardId = player.getHand().get(2).getIdCard();
            myHandImage3.setImage(new Image(createPath(cardId)));
            myHandImage3.setDisable(true);
            handIDs[2] = cardId;
        }

        //setting secret objectives
        if(player.getGoal()!=null){
            secretObjective.getChildren().remove(mySecretObjective2);
            cardId = player.getGoal().getIdCard();
            mySecretObjective1.setImage(new Image(createPath(cardId)));
            mySecretObjective1.setDisable(true);
            mySecretObjective1.setFitWidth(132.0);
            mySecretObjective1.setFitHeight(88.0);
        }
        else{
            cardId = player.getObjectiveBeforeChoice()[0].getIdCard();
            mySecretObjective1.setImage(new Image(createPath(cardId)));
            mySecretObjective1.setDisable(true);
            cardId = player.getObjectiveBeforeChoice()[1].getIdCard();
            mySecretObjective2.setImage(new Image(createPath(cardId)));
            mySecretObjective2.setDisable(true);
        }

        //setting starting cards
        if(player.getStartingCard()==null){
            //vedi se funziona
        }
        cardId = player.getStartingCard().getIdCard();
        myStartingCard.setImage(new Image(createPath(cardId)));
        myStartingCard.setDisable(true);
        startingID = cardId;

        //setting common objectives

        cardId = gameImmutable.getBoardDeck().getCommonObjective(0).getIdCard();
        commonObjectiveCard1.setImage(new Image(createPath(cardId)));
        commonObjectiveCard1.setDisable(true);

        cardId = gameImmutable.getBoardDeck().getCommonObjective(1).getIdCard();
        commonObjectiveCard2.setImage(new Image(createPath(cardId)));
        commonObjectiveCard2.setDisable(true);

        //setting resource cards
        if(!gameImmutable.getDrawableDeck().getDecks().get("resources").isEmpty()){
            cardId = gameImmutable.getDrawableDeck().getDecks().get("resources").peek().getIdCard();
        }
        deckResourcesCard.setImage(new Image(createBackPath(cardId)));
        deckResourcesCard.setDisable(true);
        cardId = gameImmutable.getBoardDeck().getCard(1).getIdCard();
        boardCard1.setImage(new Image(createPath(cardId)));
        boardCard1.setDisable(true);
        cardId = gameImmutable.getBoardDeck().getCard(2).getIdCard();
        boardCard2.setImage(new Image(createPath(cardId)));
        boardCard2.setDisable(true);

        //setting gold cards

        if(!gameImmutable.getDrawableDeck().getDecks().get("gold").isEmpty()){
            cardId = gameImmutable.getDrawableDeck().getDecks().get("gold").peek().getIdCard();
        }
        deckGoldCard.setImage(new Image(createBackPath(cardId)));
        deckGoldCard.setDisable(true);
        cardId = gameImmutable.getBoardDeck().getCard(3).getIdCard();
        boardCard3.setImage(new Image(createPath(cardId)));
        boardCard3.setDisable(true);
        cardId = gameImmutable.getBoardDeck().getCard(4).getIdCard();
        boardCard4.setImage(new Image(createPath(cardId)));
        boardCard4.setDisable(true);

        flipHandCard.setDisable(true);

        //board reconstruction
        if(player.getBoard().getBoardMatrix()[player.getBoard().getDim_x()/2][player.getBoard().getDim_y()/2]!=null){
            updateBoard(gameImmutable,nickname);
        }

        for(Player p: gameImmutable.getPlayers()){

            ImageView color;
            if(p.getColorPlayer() == 1){
                color = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png")));
            } else if (p.getColorPlayer() == 2) {
                color = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png")));
            } else if (p.getColorPlayer() == 3) {
                color = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png")));
            } else {
                color = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_jaune.png")));
            }
            color.setFitHeight(40);
            color.setFitWidth(40);

            ImageView hand1 = new ImageView();
            ImageView hand2 = new ImageView();
            ImageView hand3 = new ImageView();
            cardId = p.getHand().get(0).getIdCard();
            hand1.setImage(new Image(createBackPath(cardId)));
            if(p.getHand().size()>1){
                cardId = p.getHand().get(1).getIdCard();
                hand2.setImage(new Image(createBackPath(cardId)));
            }
            if (p.getHand().size()>2) {
                cardId = p.getHand().get(2).getIdCard();
                hand3.setImage(new Image(createBackPath(cardId)));
            }
            hand1.setFitHeight(30);
            hand1.setFitWidth(30);
            hand2.setFitHeight(30);
            hand2.setFitWidth(30);
            hand3.setFitHeight(30);
            hand3.setFitWidth(30);

            String printPoints = "POINTS: " + p.getCurrentPoints();
            Label points = new Label(printPoints);
            points.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            points.setTextFill(Color.BROWN);

            ImageView green = new ImageView(String.valueOf(getClass().getResource("/images/Seed_image/green.png")));
            Label greenCount = new Label(" "+ p.getCountSeed()[0] + " ");
            green.setFitHeight(25);
            green.setFitWidth(25);

            ImageView blue = new ImageView(String.valueOf(getClass().getResource("/images/Seed_image/blue.png")));
            Label blueCount = new Label(" "+ p.getCountSeed()[1] + " ");
            blue.setFitHeight(25);
            blue.setFitWidth(25);

            ImageView red = new ImageView(String.valueOf(getClass().getResource("/images/Seed_image/red.png")));
            Label redCount = new Label(" "+ p.getCountSeed()[2] + " ");
            red.setFitHeight(25);
            red.setFitWidth(25);

            ImageView purple = new ImageView(String.valueOf(getClass().getResource("/images/Seed_image/purple.png")));
            Label purpleCount = new Label(" "+ p.getCountSeed()[3] + " ");
            purple.setFitHeight(25);
            purple.setFitWidth(25);

            ImageView feather = new ImageView(String.valueOf(getClass().getResource("/images/Seed_image/feather.png")));
            Label featherCount = new Label(" "+ p.getCountSeed()[4] + " ");
            feather.setFitHeight(25);
            feather.setFitWidth(25);

            ImageView potion = new ImageView(String.valueOf(getClass().getResource("/images/Seed_image/potion.png")));
            Label potionCount = new Label(" "+ p.getCountSeed()[5] + " ");
            potion.setFitHeight(25);
            potion.setFitWidth(25);

            ImageView scroll = new ImageView(String.valueOf(getClass().getResource("/images/Seed_image/scroll.png")));
            Label scrollCount = new Label(" "+ p.getCountSeed()[6] + " ");
            scroll.setFitHeight(25);
            scroll.setFitWidth(25);

            HBox seedCountBox1 = new HBox(green,greenCount,blue,blueCount,red,redCount,purple,purpleCount,feather,featherCount,potion,potionCount,scroll,scrollCount);

            Button button = new Button("SEE BOARD");
            button.setDisable(false);
            button.setStyle("-fx-background-color: #D3B48E;-fx-font-family: Arial;-fx-font-weight: bold; -fx-font-size: 10; -fx-padding: 5; -fx-border-color: black; -fx-border-radius: 5;");
            button.setId(p.getNickname());
            button.setOnMouseClicked(this::handleSeeOtherPlayersBoards);

            if(!p.getNickname().equals(nickname)){
                /*
                if(p.getBoard().getBoardMatrix()[player.getBoard().getDim_x()/2][player.getBoard().getDim_y()/2]!=null){
                    Platform.runLater(()->application.updateOtherBoard(gameImmutable,p.getNickname()));
                }
                */

                Label name = new Label(p.getNickname());
                name.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                name.setTextFill(Color.BROWN);

                HBox hbox1 = new HBox(hand1,hand2,hand3,button);
                VBox vBox2 = new VBox(hbox1,points);
                HBox hbox2 = new HBox(color,vBox2);
                VBox vBox3 = new VBox(name,hbox2,seedCountBox1);
                //button.setPadding(new Insets(5,5,5,5));

                vBox3.setStyle("-fx-background-color: beige; -fx-border-color: black; -fx-border-radius: 0;");
                hbox1.setPadding(new Insets(5,5,5,5));
                hbox1.setSpacing(8);
                vBox3.setPadding(new Insets(5,5,5,5));
                vBox2.setPadding(new Insets(5,5,5,5));
                hbox2.setPadding(new Insets(5,5,5,5));
                hbox2.setSpacing(10);

                otherPlayersVBox.getChildren().add(vBox3);

                receiverPrivateMessages.getItems().add(p.getNickname());

                // score board
                //Platform.runLater(()->application.setScoreBoard());
            }
            else {

                Label name = new Label(p.getNickname() + " (YOU)");
                name.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                name.setTextFill(Color.BROWN);

                HBox hbox1 = new HBox(hand1,hand2,hand3);
                VBox vBox2 = new VBox(hbox1,points);
                HBox hbox2 = new HBox(color,vBox2);
                VBox vBox3 = new VBox(name,hbox2,seedCountBox1);

                vBox3.setStyle("-fx-background-color: beige; -fx-border-color: black; -fx-border-radius: 0;");
                hbox1.setPadding(new Insets(5,5,5,5));
                hbox1.setSpacing(8);
                vBox3.setPadding(new Insets(5,5,5,5));
                vBox2.setPadding(new Insets(5,5,5,5));
                hbox2.setPadding(new Insets(5,5,5,5));
                hbox2.setSpacing(10);

                otherPlayersVBox.getChildren().add(vBox3);
            }
        }
        // score board
        Platform.runLater(()->application.setScoreBoard());
        Platform.runLater(()->application.setOtherPlayerBoard());
    }

    /**
     * Create back path string.
     *
     * @param cardId the card id
     * @return the string
     */
    private String createBackPath(int cardId) {
        String path;
        if(cardId<10){
            path = "/images/Codex_image/CODEX_cards_back/00" + cardId +".png";
        }
        else if(cardId<100)
        {
            path = "/images/Codex_image/CODEX_cards_back/0" + cardId +".png";
        }
        else {
            path = "/images/Codex_image/CODEX_cards_back/" + cardId +".png";
        }
        return String.valueOf(getClass().getResource(path));
    }

    /**
     * Create path string.
     *
     * @param cardId the card id
     * @return the string
     */
    private String createPath(int cardId) {
        String path;
        if(cardId<10){
            path = "/images/Codex_image/CODEX_cards_front/00" + cardId +".png";
        }
        else if(cardId<100)
        {
            path = "/images/Codex_image/CODEX_cards_front/0" + cardId +".png";
        }
        else {
            path = "/images/Codex_image/CODEX_cards_front/" + cardId +".png";
        }
        return String.valueOf(getClass().getResource(path));
    }

    /**
     * My running turn place starting.
     */
    public void myRunningTurnPlaceStarting() {
        startingCardVBox.setDisable(false);
        //ConsolePrinter.consolePrinter(String.valueOf(myStartingCard));
        myStartingCard.setDisable(false);
        flipStartingButton.setVisible(true);
        flipStartingButton.setDisable(false);
        glow(flipStartingButton);
        glow(myStartingCard);
    }

    /**
     * Handle starting card clicked.
     *
     * @param event the event
     */
    @FXML
    public void handleStartingCardClicked(MouseEvent event){
        //ConsolePrinter.consolePrinter("starting clicked");
        Node card = (Node) event.getSource();
        glow(card);
        if (myStartingCard.getUserData() == null || !(boolean) myStartingCard.getUserData()) {
            jump(card);
        }
        selectedCard = myStartingCard;
        glow(board);
        board.setDisable(false);
    }

    /**
     * Handle flip starting.
     */
    @FXML
    public void handleFlipStarting() {
        int cardId = this.player.getStartingCard().getIdCard();
        if (!flippedStarting) {
            myStartingCard.setImage(new Image(createBackPath(cardId)));
            flippedStarting = true;
        }
        else {
            myStartingCard.setImage(new Image(createPath(cardId)));
            flippedStarting = false;
        }
        selectedCard = myStartingCard;
    }

    /**
     * Handle board click.
     */
    public void handleBoardClick() {
        String flipped;
        board.setEffect(null);
        flipHandCard.setDisable(true);
        if (selectedCard == myStartingCard) {
            board.setOnMouseClicked(event -> {});
            flipped = flippedStarting ? " true" : "";
            executor.submit(() -> {
                try {
                    client.receiveInput("/addStarting" + flipped);
                } catch (IOException | NotBoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    /**
     * Handle board card click.
     *
     * @param mouseEvent the mouse event
     */
    private void handleBoardCardClick(MouseEvent mouseEvent) {
        ImageView card = (ImageView) mouseEvent.getSource();
        handCards.setDisable(true);
        String flipped;
        flipHandCard.setDisable(true);
        int corner = -1;
        if (selectedCard != null) {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            double cardWidth = card.getFitWidth();
            double cardHeight = card.getFitHeight();
            flipped = flippedHand[hand - 1] ? "true" : "";

            if(x<= cardWidth*0.25){
                if(y<=cardHeight*0.44){
                    corner = 1;
                }
                else if(y>=cardHeight*0.56){
                    corner = 4;
                }
            }
            else if(x>= cardWidth*0.75){
                if(y<=cardHeight*0.44){
                    corner = 2;
                }
                else if(y>=cardHeight*0.56){
                    corner = 3;
                }
            }

            int finalCorner = corner;
            if (finalCorner != -1) {
                selectedCard.setVisible(false);
                String input = String.format("/addCard %d %s %d %s", hand, card.getId(), finalCorner,flipped);
                //ConsolePrinter.consolePrinter(input);

                executor.submit(() -> {
                    try {
                        client.receiveInput(input);
                    } catch (IOException | NotBoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    /**
     * Chosen goal.
     */
    public void chosenGoal() {
        secretObjectiveVBox.setPrefWidth(132.0);
        secretObjective.setPrefSize(132.0,88.0);
        switch (goal){
            case 1->{
                secretObjective.getChildren().remove(mySecretObjective2);
                mySecretObjective1.setEffect(null);
                mySecretObjective1.setFitWidth(132.0);
                mySecretObjective1.setFitHeight(88.0);
            }
            case 2->{
                secretObjective.getChildren().remove(mySecretObjective1);
                mySecretObjective2.setEffect(null);
                mySecretObjective2.setFitWidth(132.0);
                mySecretObjective2.setFitHeight(88.0);
            }
        }
    }

    /**
     * Update board.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
    public void updateBoard(GameImmutable gameImmutable, String nickname) {
        board.getChildren().clear();
        board.applyCss();
        board.layout();
        StartingCard start = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(nickname)).toList().getFirst().getStartingCard();
        placeStartCardOnBoardFromMatrix(start,BOARD_SIZE[1]/2 - CARD_SIZE[1]/2,BOARD_SIZE[0]/2 - CARD_SIZE[0]/2);
        selectedCard = null;
    }

    /**
     * Place start card on board from matrix.
     *
     * @param start the start
     * @param x     the x
     * @param y     the y
     */
    private void placeStartCardOnBoardFromMatrix(PlayingCard start,double x,double y) {
        board.getChildren().removeAll();
        ImageView startCard = new ImageView();
        if(start.isFlipped()){
            startCard.setImage(new Image(createBackPath(start.getIdCard())));
        }
        else {
            startCard.setImage(new Image(createPath(start.getIdCard())));
        }
        startCard.setFitWidth(CARD_SIZE[1]);
        startCard.setFitHeight(CARD_SIZE[0]);
        startCard.setLayoutX(x);
        startCard.setLayoutY(y);
        startCard.setId(String.valueOf(startingID));
        startCard.setOnMouseClicked(this::handleBoardCardClick);
        cardsHBox.getChildren().remove(startingVBox);
        board.getChildren().add(startCard);

        cardOffset(start, x, y, startCard);
    }

    /**
     * Place card on board from matrix.
     *
     * @param card the card
     * @param x    the x
     * @param y    the y
     */
    private void placeCardOnBoardFromMatrix(PlayingCard card, double x, double y){
        ImageView cardImage = new ImageView();
        if(card.isFlipped()){
            cardImage.setImage(new Image(createBackPath(card.getIdCard())));
        }
        else {
            cardImage.setImage(new Image(createPath(card.getIdCard())));
        }

        cardImage.setLayoutX(x);
        cardImage.setLayoutY(y);

        cardImage.setId(String.valueOf(card.getIdCard()));
        cardImage.setFitWidth(CARD_SIZE[1]);
        cardImage.setFitHeight(CARD_SIZE[0]);
        cardImage.setOnMouseClicked(this::handleBoardCardClick);
        board.getChildren().add(cardImage);
        cardImage.setDisable(false);
        cardOffset(card, x, y, cardImage);
    }

    /**
     * Card offset.
     *
     * @param card      the card
     * @param x         the x
     * @param y         the y
     * @param cardImage the card image
     */
    private void cardOffset(PlayingCard card, double x, double y, ImageView cardImage) {
        double updatedX = x;
        double updatedY = y;
        for(int i=1;i<=4;i++){
            if(card.getCorner(i)!=null && card.getCorner(i).getCardAttached()!=null){
                double xoffset=0;
                double yoffset=0;
                switch (i){
                    case 1 ->{
                        xoffset = - (cardImage.getFitWidth()*0.75);
                        yoffset = - (cardImage.getFitHeight()*0.56);
                    }
                    case 2 ->{
                        xoffset = (cardImage.getFitWidth()*0.75);
                        yoffset = - (cardImage.getFitHeight()*0.56);
                    }
                    case 3 ->{
                        xoffset = (cardImage.getFitWidth()*0.75);
                        yoffset = (cardImage.getFitHeight()*0.56);
                    }
                    case 4 ->{
                        xoffset = - (cardImage.getFitWidth()*0.75);
                        yoffset = (cardImage.getFitHeight()*0.56);
                    }
                }

                double newX = updatedX;
                double newY = updatedY;
                if (updatedX + xoffset < 0) {
                    BOARD_SIZE[1] = board.getPrefWidth() + CARD_SIZE[1];
                    board.setPrefWidth(BOARD_SIZE[1]);
                    for (Node child : board.getChildren()) {
                        child.setLayoutX(child.getLayoutX() + CARD_SIZE[1]);
                    }
                    newX = updatedX + CARD_SIZE[1];
                }
                else if (updatedX + CARD_SIZE[1] > board.getPrefWidth()) {
                    BOARD_SIZE[1] = board.getPrefWidth() + CARD_SIZE[1];
                    board.setPrefWidth(BOARD_SIZE[1]);
                }
                if (updatedY + yoffset < 0) {
                    BOARD_SIZE[0] = board.getPrefHeight() + CARD_SIZE[0];
                    board.setPrefHeight(BOARD_SIZE[0]);
                    for (Node child : board.getChildren()) {
                        child.setLayoutY(child.getLayoutY() + CARD_SIZE[0]);
                    }
                    newY = updatedY + CARD_SIZE[0];
                }
                else if (updatedY + CARD_SIZE[0] > board.getPrefHeight()) {
                    BOARD_SIZE[0] = board.getPrefHeight() + CARD_SIZE[0];
                    board.setPrefHeight(BOARD_SIZE[0]);
                }

                updatedX = newX;
                updatedY = newY;
                board.applyCss();
                board.layout();

                placeCardOnBoardFromMatrix(card.getCorner(i).getCardAttached(),updatedX+xoffset,updatedY+yoffset);
            }
        }
    }

    /**
     * My running turn chose objective.
     */
    public void myRunningTurnChoseObjective() {
        mySecretObjective1.setDisable(false);
        mySecretObjective2.setDisable(false);
        glow(mySecretObjective1);
        glow(mySecretObjective2);
    }

    /**
     * My running turn place card.
     */
    public void myRunningTurnPlaceCard() {
        ImageView card;
        for (int i = 0; i < handCards.getChildren().size(); i++) {
            card = (ImageView) handCards.getChildren().get(i);
            glow(card);
            card.setVisible(true);
            card.setDisable(false);
        }
        handCards.setDisable(false);
    }

    /**
     * My running turn draw card.
     */
    public void myRunningTurnDrawCard() {
        for (int i = 0; i < boardCards.getChildren().size(); i++) {
            if (boardCards.getChildren().get(i) instanceof HBox cards) {
                for (int j = 0; j < cards.getChildren().size(); j++) {
                    glow(cards.getChildren().get(j));
                    cards.getChildren().get(j).setDisable(false);
                }
            }
        }

        for (int i = 0; i < decks.getChildren().size(); i++) {
            glow(decks.getChildren().get(i));
            decks.getChildren().get(i).setDisable(false);
        }
    }

    /**
     * Glow.
     *
     * @param node the node
     */
    private void glow(Node node){
        // blue shadow
        DropShadow borderGlow = new DropShadow();
        borderGlow.setBlurType(javafx.scene.effect.BlurType.GAUSSIAN);
        borderGlow.setColor(Color.rgb(36, 160, 237, 0.8));
        borderGlow.setRadius(10);
        borderGlow.setSpread(0.5);
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        node.setEffect(borderGlow);
    }

    /**
     * Jump.
     *
     * @param node the node
     */
    private void jump(Node node){
        node.setUserData(true);
        node.setDisable(true);
        TranslateTransition jumpUp = new TranslateTransition(ANIMATION_DURATION, node);
        jumpUp.setByY(-20);
        jumpUp.setOnFinished(e -> dropCard(node));
        jumpUp.play();
    }

    /**
     * Drop card.
     *
     * @param node the node
     */
    private void dropCard(Node node) {
        TranslateTransition dropDown = new TranslateTransition(ANIMATION_DURATION, node);
        dropDown.setByY(JUMP_HEIGHT);
        dropDown.setOnFinished(e -> {
            node.setUserData(false);
            node.setDisable(false);  // Re-enable the card after the animation
        });
        dropDown.play();
    }

    /**
     * Handle objective card 2 clicked.
     */
    public void handleObjectiveCard2Clicked() {
        mySecretObjective1.setEffect(null);
        mySecretObjective1.setDisable(true);
        mySecretObjective2.setDisable(true);
        jump(mySecretObjective2);
        goal = 2;
        executor.submit(()->{
            try {
                client.receiveInput("/choseGoal 2");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Handle objective card 1 clicked.
     */
    public void handleObjectiveCard1Clicked() {
        mySecretObjective2.setEffect(null);
        mySecretObjective2.setDisable(true);
        mySecretObjective1.setDisable(true);
        jump(mySecretObjective1);
        goal = 1;
        executor.submit(()->{
            try {
                client.receiveInput("/choseGoal 1");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
        mySecretObjective2.setDisable(true);
    }

    /**
     * Handle hand card clicked.
     *
     * @param mouseEvent the mouse event
     */
    public void handleHandCardClicked(MouseEvent mouseEvent) {
        // ImageView card = (ImageView) mouseEvent.getSource();
        Node card = (Node) mouseEvent.getSource();
        for (int i = 0; i < handCards.getChildren().size(); i++) {
            if (!handCards.getChildren().get(i).equals(card)) {
                handCards.getChildren().get(i).setEffect(null);}
            else {
                hand = i + 1;
                // glow((ImageView) handCards.getChildren().get(i));
                glow(card);
            }
        }
        if (card.getUserData() == null || !(boolean) card.getUserData()) {
            jump(card);
        }
        selectedCard = (ImageView) card;
        //ConsolePrinter.consolePrinter(String.valueOf(selectedCard));
        flipHandCard.setDisable(false);
        board.setDisable(false);
    }

    /**
     * Handle flip hand card.
     */
    public void handleFlipHandCard() {
        Image img;
        if (!flippedHand[hand - 1]) {
            img = new Image(createBackPath(handIDs[hand - 1]));
            flippedHand[hand - 1] = true;
        }
        else {
            img = new Image(createPath(handIDs[hand - 1]));
            flippedHand[hand - 1] = false;
        }
        selectedCard.setImage(img);
    }

    /**
     * Disable board cards.
     */
    private void disableBoardCards() {
        for (int i = 0; i < boardCards.getChildren().size(); i++) {
            if (boardCards.getChildren().get(i) instanceof HBox cards) {
                for (int j = 0; j < cards.getChildren().size(); j++) {
                    cards.getChildren().get(j).setEffect(null);
                    cards.getChildren().get(j).setDisable(true);
                }
            }
        }
    }

    /**
     * Disable decks.
     */
    private void disableDecks() {
        for (int i = 0; i < decks.getChildren().size(); i++) {
            decks.getChildren().get(i).setEffect(null);
            decks.getChildren().get(i).setDisable(true);
        }
    }

    /**
     * Handle drawable card clicked.
     *
     * @param mouseEvent the mouse event
     */
    public void handleDrawableCardClicked(MouseEvent mouseEvent) {
        ImageView toReplaceIV = (ImageView) mouseEvent.getSource();
        int toReplace = -1;
        jump(toReplaceIV);

        disableDecks();

        for (int i = 0; i < boardCards.getChildren().size(); i++) {
            if (boardCards.getChildren().get(i) instanceof HBox cards) {
                for (int j = 0; j < cards.getChildren().size(); j++) {
                    ImageView card = (ImageView) cards.getChildren().get(j);
                    card.setEffect(null);
                    card.setDisable(true);
                    if (card.equals(toReplaceIV)) {
                        toReplace = i * 2 + j;
                    }
                }
            }
        }

        try {
            String msg = "/drawBoard " + (toReplace + 1);
            client.receiveInput(msg);
        } catch (IOException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle resource deck clicked.
     */
    public void handleResourceDeckClicked() {
        jump(deckResourcesCard);

        for (int i = 0; i < decks.getChildren().size(); i++) {
            ImageView card = (ImageView) decks.getChildren().get(i);
            card.setEffect(null);
            card.setDisable(true);
        }

        disableBoardCards();

        try {
            client.receiveInput("/drawResources");
        } catch (IOException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle gold deck clicked.
     */
    public void handleGoldDeckClicked() {
        jump(deckGoldCard);

        for (int i = 0; i < decks.getChildren().size(); i++) {
            ImageView card = (ImageView) decks.getChildren().get(i);
            card.setEffect(null);
            card.setDisable(true);
        }

        disableBoardCards();

        try {
            client.receiveInput("/drawGold");
        } catch (IOException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update board deck.
     *
     * @param gameImmutable the game immutable
     */
    public void updateBoardDeck(GameImmutable gameImmutable) {
        updateDecks(gameImmutable);
        updateDrawableBoard(gameImmutable);
    }

    /**
     * Update decks.
     *
     * @param gameImmutable the game immutable
     */
    private void updateDecks(GameImmutable gameImmutable) {
        int cardId;

        if(!gameImmutable.getDrawableDeck().getDecks().get("resources").isEmpty()){
            cardId = gameImmutable.getDrawableDeck().getDecks().get("resources").peek().getIdCard();
            deckResourcesCard.setImage(new Image(createBackPath(cardId)));
        }
        if (!gameImmutable.getDrawableDeck().getDecks().get("gold").isEmpty()) {
            cardId = gameImmutable.getDrawableDeck().getDecks().get("gold").peek().getIdCard();
            deckGoldCard.setImage(new Image(createBackPath(cardId)));
        }
    }

    /**
     * Update drawable board.
     *
     * @param gameImmutable the game immutable
     */
    private void updateDrawableBoard(GameImmutable gameImmutable) {
        int cardId;
        cardId = gameImmutable.getBoardDeck().getCard(1).getIdCard();
        boardCard1.setImage(new Image(createPath(cardId)));
        boardCard1.setDisable(true);
        boardCard1.setVisible(true);
        cardId = gameImmutable.getBoardDeck().getCard(2).getIdCard();
        boardCard2.setImage(new Image(createPath(cardId)));
        boardCard2.setDisable(true);
        boardCard2.setVisible(true);
        cardId = gameImmutable.getBoardDeck().getCard(3).getIdCard();
        boardCard3.setImage(new Image(createPath(cardId)));
        boardCard3.setDisable(true);
        boardCard3.setVisible(true);
        cardId = gameImmutable.getBoardDeck().getCard(4).getIdCard();
        boardCard4.setImage(new Image(createPath(cardId)));
        boardCard4.setDisable(true);
        boardCard4.setVisible(true);
    }

    /**
     * Update hand.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
    public void updateHand(GameImmutable gameImmutable, String nickname) {
        Optional<Player> p = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(nickname)).findFirst();
        if(p.isPresent()){
            for (int i : handIDs) {
                i = -1;
            }

            hand = 0;

            for(int i = 0; i < handCards.getChildren().size(); i++){
                ImageView card = (ImageView) handCards.getChildren().get(i);
                card.setVisible(false);
                card.setEffect(null);
                flippedHand[i] = false;
            }

            for(int i = 0; i < p.get().getHand().size(); i++){
                ImageView handImg = (ImageView) handCards.getChildren().get(i);
                int cardId = p.get().getHand().get(i).getIdCard();
                handImg.setImage(new Image(createPath(cardId)));
                handImg.setVisible(true);
                handIDs[i] = cardId;
                hand += 1;
            }

        }
    }

    /**
     * Shift hand.
     */
    private void shiftHand() {
        ImageView card, next;
        for (int i = hand - 1; i < handCards.getChildren().size() - 1; i++) {
            if(i<player.getHand().size()){
                card = (ImageView) handCards.getChildren().get(i);
                card.setEffect(null);
                next = (ImageView) handCards.getChildren().get(i + 1);
                card.setImage(next.getImage());
                next.setVisible(false);
                card.setVisible(true);
                handIDs[i] = handIDs[i + 1];
                flippedHand[i] = flippedHand[i + 1];
                hand += 1;
            }
        }
    }

    /**
     * Card not placed.
     *
     * @param s the s
     */
    public void cardNotPlaced(String s) {
        infoBox(s);
        myRunningTurnPlaceCard();
    }

    /**
     * Objective not selected.
     *
     * @param s the s
     */
    public void objectiveNotSelected(String s) {
        infoBox(s);
        myRunningTurnChoseObjective();
    }

    /**
     * Start already added.
     *
     * @param s the s
     */
    public void startAlreadyAdded(String s) {
        infoBox(s);
    }

    /**
     * Card already added.
     *
     * @param s the s
     */
    public void cardAlreadyAdded(String s) {
        infoBox(s);
    }

    /**
     * Wrong phase.
     *
     * @param s the s
     */
    public void wrongPhase(String s) {
        infoBox(s);
    }

    /**
     * No resources deck.
     *
     * @param s the s
     */
    public void noResourcesDeck(String s) {
        //infoBox(s);
        decks.getChildren().remove(deckResourcesCard);
        myRunningTurnDrawCard();
    }

    /**
     * No gold deck.
     *
     * @param s the s
     */
    public void noGoldDeck(String s) {
        //infoBox(s);
        decks.getChildren().remove(deckGoldCard);
        myRunningTurnDrawCard();
    }

    /**
     * No board card.
     *
     * @param s the s
     */
    public void noBoardCard(String s) {
        //infoBox(s);
        myRunningTurnDrawCard();
    }

    /**
     * Info box.
     *
     * @param message the message
     */
    private void infoBox(String message) {
        // System.out.println("infoBox called with message: " + message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");
        alert.setHeaderText("Invalid Action");
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Not my turn.
     */
    public void notMyTurn() {
        disable(true);
        myTurn = "Not your turn";
        turnLabel.setText(myTurn);
    }

    /**
     * My turn.
     */
    public void myTurn() {
        myTurn = "Your turn";
        turnLabel.setText(myTurn);
    }

    /**
     * Waiting for at least two players to be in the game, prints a label
     */
    public void showWaitingReconnection() {
        turnLabel.setText("Waiting reconnection");
        if (!handCards.isDisabled()) {
            handCardsDisable = true;
        }
        secretObjectiveVBox.setDisable(true);
        if (startingVBox.getParent() != null) {
            startingVBox.setDisable(true);
        }
        handCards.setDisable(true);
        for (int i = 0; i < handCards.getChildren().size(); i++) {
            Node n = handCards.getChildren().get(i);
            if (n instanceof ImageView) {
                n.setEffect(null);
            }
        }
    }

    public void backFromReconnection() {
        if (handCardsDisable) {
            handCards.setDisable(false);
            handCardsDisable = false;
            for (int i = 0; i < handCards.getChildren().size(); i++) {
                Node n = handCards.getChildren().get(i);
                if (n instanceof ImageView) {
                    glow(n);
                }
            }
        }
        secretObjectiveVBox.setDisable(false);
        if (startingVBox.getParent() != null) {
            startingVBox.setDisable(false);
        }
        turnLabel.setText(myTurn);
    }

    /**
     * Disable.
     *
     * @param truefalse the truefalse
     */
    private void disable(Boolean truefalse){
        myHandImage1.setDisable(truefalse);
        myHandImage2.setDisable(truefalse);
        myHandImage3.setDisable(truefalse);
        mySecretObjective1.setDisable(truefalse);
        mySecretObjective2.setDisable(truefalse);
        startingCardVBox.setDisable(truefalse);
        deckResourcesCard.setDisable(truefalse);
        boardCard1.setDisable(truefalse);
        boardCard2.setDisable(truefalse);
        deckGoldCard.setDisable(truefalse);
        boardCard3.setDisable(truefalse);
        boardCard4.setDisable(truefalse);
    }

    /**
     * Glow info.
     *
     * @param obj the obj
     */
    public void glowInfo(String obj) {
        switch (obj){
            case "start"->glow(myStartingCard);
            case "hand"->{
                glow(myHandImage1);
                glow(myHandImage2);
                glow(myHandImage3);
            }
            case "deck"->{
                glow(deckGoldCard);
                glow(deckResourcesCard);
            }
            case "others"->{
                DropShadow borderGlow = new DropShadow();
                borderGlow.setOffsetY(0f);
                borderGlow.setOffsetX(0f);
                borderGlow.setColor(Color.BLUE);
                borderGlow.setWidth(30);
                borderGlow.setHeight(30);
                otherPlayersVBox.setEffect(borderGlow);
            }
        }
    }

    /**
     * Stop glow info.
     */
    public void stopGlowInfo() {
        myStartingCard.setEffect(null);
        myHandImage1.setEffect(null);
        myHandImage2.setEffect(null);
        myHandImage3.setEffect(null);
        deckGoldCard.setEffect(null);
        deckResourcesCard.setEffect(null);
        otherPlayersVBox.setEffect(null);
    }

    /**
     * Handle see info box.
     */
    public void handleSeeInfoBox() {
        Platform.runLater(()-> application.infoBox());
    }

    /**
     * Handle see rule book.
     */
    public void handleSeeRuleBook() {
        Platform.runLater(()-> application.ruleBook());
    }


    //--------------------------------------------CHAT
    /**
     * Display chat public.
     *
     * @param message the message
     */
    public void displayChatPublic(String message, Boolean age) {
        if (!isPrivateChat) {
            Text text = new Text(message);
            System.out.println(message);
            text.setWrappingWidth(scrollPane.getWidth() - 20); // Adjust width to fit scroll pane
            messageContainer.getChildren().add(text);
            scrollPane.layout();
        }
        String[] split1 = message.split("- ");
        String[] split2 =split1[1].split("]");

        if(age && !split2[0].equals(player.getNickname())){
            newMessage("New message!",message);
        }
    }
    /**
     * Display chat private.
     *
     * @param message the message
     */
    public void displayChatPrivate(String message,Boolean age) {
        if (isPrivateChat) {
            Text text = new Text(message);
            System.out.println(message);
            text.setWrappingWidth(scrollPane.getWidth() - 20); // Adjust width to fit scroll pane
            messageContainer.getChildren().add(text);
            scrollPane.layout();
        }

        String[] split1 = message.split("- ");
        String[] split2 =split1[1].split(" ");

        if(age && !split2[0].equals(player.getNickname())){
            newMessage("New private message!",message);
        }
    }

    public void newMessage(String s1,String s2){
        Label title = new Label(s1);
        Label content = new Label(s2);
        VBox messageBox = new VBox(title,content);
        messageBox.setLayoutY(turnLabel.getLayoutY());
        messageBox.setLayoutX(turnLabel.getLayoutX() + turnLabel.getWidth() + 10);
        messageBox.setStyle("-fx-background-color: white; -fx-font-size:14px; -fx-border-color: black; -fx-border-width: 2px; -fx-font-family: 'Times New Roman'; -fx-alignment: center;");
        pane.getChildren().add(messageBox);
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                event -> pane.getChildren().remove(messageBox)
        ));
        timeline.play();
    }

    /**
     * Handle send.
     */
    public void handleSend() {
        String message = messageInput.getText().trim();
        messageInput.clear();
        if (!message.isEmpty()) {
            if (isPrivateChat) {
                String receiver = (String) receiverPrivateMessages.getSelectionModel().getSelectedItem();
                if (!receiver.isEmpty()) {
                    executor.submit(() -> {
                        try {
                            client.receiveInput("/chatPrivate " + receiver + " " + message);
                        } catch (IOException | NotBoundException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
            else {
                executor.submit(() -> {
                    try {
                        client.receiveInput("/chat " + message);
                    } catch (IOException | NotBoundException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    /**
     * Handle see public chat.
     */
    @FXML
    public void handleSeePublicChat() {
        isPrivateChat = false;
        receiverContainer.setVisible(false);
        receiverContainer.setManaged(false);
        publicChatButton.setDisable(true);
        privateChatButton.setDisable(false);
        messageContainer.getChildren().removeAll(messageContainer.getChildren());
        executor.submit(()->{
            try {
                client.receiveInput("/seeChat");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Handle see private chat.
     */
    @FXML
    public void handleSeePrivateChat() {
        isPrivateChat = true;
        receiverContainer.setVisible(true);
        receiverContainer.setManaged(true);
        publicChatButton.setDisable(false);
        privateChatButton.setDisable(true);
        messageContainer.getChildren().removeAll(messageContainer.getChildren());
        String selectedPlayer = (String) receiverPrivateMessages.getSelectionModel().getSelectedItem();

        executor.submit(()->{
            if (selectedPlayer != null) {
                try {
                    client.receiveInput("/seeChatPrivate " + selectedPlayer);
                } catch (IOException | NotBoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Handle select player chat.
     */
    @FXML
    public void handleSelectPlayerChat() {
        isPrivateChat = true;
        messageContainer.getChildren().removeAll(messageContainer.getChildren());
        String selectedPlayer = (String) receiverPrivateMessages.getSelectionModel().getSelectedItem();

        executor.submit(()->{
            try {
                client.receiveInput("/seeChatPrivate " + selectedPlayer);
            }
            catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Handle see score board.
     */
    @FXML
    public void handleSeeScoreBoard() {
        Platform.runLater(()->application.seeScoreBoard());
    }

    //--------------------------------------------POINTS

    /**
     * Update other players points.
     *
     * @param gameImmutable         the game immutable
     * @param playerChangedNickname the player changed nickname
     */
    public void updateOtherPlayersPoints(GameImmutable gameImmutable, String playerChangedNickname) {
        Optional<Player> p = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(playerChangedNickname)).findFirst();
        String currPoints = "error";
        if(p.isPresent()){
             currPoints = "POINTS " + p.get().getCurrentPoints();
        }
        for(int i=1; i<otherPlayersVBox.getChildren().size(); i++){ //it starts from 1 because there are buttons before
            VBox vBox3 = (VBox) otherPlayersVBox.getChildren().get(i);
            Label name = (Label) vBox3.getChildren().get(0);
            if(name.getText().equals(playerChangedNickname) || name.getText().equals(playerChangedNickname+" (YOU)")){
                HBox hBox2 = (HBox) vBox3.getChildren().get(1);
                VBox vBox2 = (VBox) hBox2.getChildren().get(1);
                Label points = (Label) vBox2.getChildren().get(1);
                points.setText(currPoints);
            }
        }
    }

    /**
     * Update back hand draw.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
    public void updateBackHandDraw(GameImmutable gameImmutable, String nickname) {
        Optional<Player> p = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(nickname)).findFirst();
        if(p.isPresent())
        {
            for(int i=1; i<otherPlayersVBox.getChildren().size(); i++){ //it starts from 1 because there are buttons before
                VBox vBox3 = (VBox) otherPlayersVBox.getChildren().get(i);
                Label name = (Label) vBox3.getChildren().get(0);
                if(name.getText().equals(nickname) || name.getText().equals(nickname + " (YOU)")){
                    HBox hBox2 = (HBox) vBox3.getChildren().get(1);
                    VBox vBox2 = (VBox) hBox2.getChildren().get(1);
                    HBox hBox1 = (HBox) vBox2.getChildren().get(0);
                    ImageView hand1 = (ImageView) hBox1.getChildren().get(0);
                    ImageView hand2 = (ImageView) hBox1.getChildren().get(1);
                    ImageView hand3 = (ImageView) hBox1.getChildren().get(2);
                    int cardId = p.get().getHand().get(0).getIdCard();
                    hand1.setImage(new Image(createBackPath(cardId)));
                    if(p.get().getHand().size()>1){
                        cardId = p.get().getHand().get(1).getIdCard();
                        hand2.setImage(new Image(createBackPath(cardId)));
                        hand2.setVisible(true);
                    }
                    if (p.get().getHand().size()>2) {
                        cardId = p.get().getHand().get(2).getIdCard();
                        hand3.setImage(new Image(createBackPath(cardId)));
                        hand3.setVisible(true);
                    }
                }
            }
        }
    }

    /**
     * Update back hand place.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
    public void updateBackHandPlace(GameImmutable gameImmutable, String nickname) {
        Optional<Player> p = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(nickname)).findFirst();
        if(p.isPresent())
        {
            for(int i=1; i<otherPlayersVBox.getChildren().size(); i++){ //it starts from 1 because there are buttons before
                VBox vBox3 = (VBox) otherPlayersVBox.getChildren().get(i);
                Label name = (Label) vBox3.getChildren().get(0);
                if(name.getText().equals(nickname) || name.getText().equals(nickname + " (YOU)")) {
                    HBox hBox2 = (HBox) vBox3.getChildren().get(1);
                    VBox vBox2 = (VBox) hBox2.getChildren().get(1);
                    HBox hBox1 = (HBox) vBox2.getChildren().get(0);
                    ImageView hand1 = (ImageView) hBox1.getChildren().get(0);
                    ImageView hand2 = (ImageView) hBox1.getChildren().get(1);
                    ImageView hand3 = (ImageView) hBox1.getChildren().get(2);
                    int cardId = p.get().getHand().get(0).getIdCard();
                    hand1.setImage(new Image(createBackPath(cardId)));
                    cardId = p.get().getHand().get(1).getIdCard();
                    hand2.setImage(new Image(createBackPath(cardId)));
                    hand3.setVisible(false);
                }
            }
        }
    }

    /**
     * Handle see other players boards.
     *
     * @param event the event
     */
    @FXML
    public void handleSeeOtherPlayersBoards(MouseEvent event) {
        Button button = (Button) event.getSource();
        String nickname = button.getId();
        Platform.runLater(()->application.seeOtherBoards(nickname));
    }

    /**
     * Update players seed count.
     *
     * @param gameImmutable         the game immutable
     * @param playerChangedNickname the player changed nickname
     */
    public void updatePlayersSeedCount(GameImmutable gameImmutable, String playerChangedNickname) {
        Player p = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(playerChangedNickname)).toList().getFirst();
        for(int i=1; i<otherPlayersVBox.getChildren().size(); i++){ //it starts from 1 because there are buttons before
            VBox vBox3 = (VBox) otherPlayersVBox.getChildren().get(i);
            Label name = (Label) vBox3.getChildren().get(0);
            if(name.getText().equals(playerChangedNickname) || name.getText().equals(playerChangedNickname+" (YOU)")){
                HBox seedCountBox1 = (HBox) vBox3.getChildren().get(2);
                Label greenCount = (Label) seedCountBox1.getChildren().get(1);
                greenCount.setText(" "+ p.getCountSeed()[0] + " ");
                Label blueCount = (Label) seedCountBox1.getChildren().get(3);
                blueCount.setText(" "+ p.getCountSeed()[1] + " ");
                Label redCount = (Label) seedCountBox1.getChildren().get(5);
                redCount.setText(" "+ p.getCountSeed()[2] + " ");
                Label purpleCount = (Label) seedCountBox1.getChildren().get(7);
                purpleCount.setText(" "+ p.getCountSeed()[3] + " ");
                Label featherCount = (Label) seedCountBox1.getChildren().get(9);
                featherCount.setText(" "+ p.getCountSeed()[4] + " ");
                Label potionCount = (Label) seedCountBox1.getChildren().get(11);
                potionCount.setText(" "+ p.getCountSeed()[5] + " ");
                Label scrollCount = (Label) seedCountBox1.getChildren().get(13);
                scrollCount.setText(" "+ p.getCountSeed()[6] + " ");
            }
        }
    }
}
