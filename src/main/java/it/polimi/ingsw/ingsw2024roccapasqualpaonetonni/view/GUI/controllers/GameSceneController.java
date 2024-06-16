package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class GameSceneController extends GenericController{

    @FXML
    private Pane pane;

    // player board
    @FXML
    private ScrollPane personalBoard;

    @FXML
    private Pane board;

    // cards section
    @FXML
    private HBox cardsHBox;

    @FXML
    private HBox handCards;

    @FXML
    private ImageView myHandImage1;

    @FXML
    private ImageView myHandImage2;

    @FXML
    private ImageView myHandImage3;

    @FXML
    private Button flipHandCard;

    @FXML
    private VBox secretObjectiveVBox;

    @FXML
    private VBox secretObjective;

    @FXML
    private ImageView mySecretObjective1;

    @FXML
    private ImageView mySecretObjective2;

    @FXML
    private VBox startingVBox;

    @FXML
    private VBox startingCardVBox;

    @FXML
    private ImageView myStartingCard;

    @FXML
    private Button flipStartingButton;

    @FXML
    private VBox boardCards;

    @FXML
    private ImageView boardCard1;

    @FXML
    private ImageView boardCard2;

    @FXML
    private ImageView boardCard3;

    @FXML
    private ImageView boardCard4;

    @FXML
    private HBox decks;

    @FXML
    private ImageView deckResourcesCard;

    @FXML
    private ImageView deckGoldCard;

    @FXML
    private ImageView commonObjectiveCard1;

    @FXML
    private ImageView commonObjectiveCard2;

    @FXML
    private VBox otherPlayersVBox;

    // chat section
    @FXML
    private ComboBox receiverPrivateMessages;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox messageContainer;

    @FXML
    private TextArea messageInput;

    @FXML
    private HBox receiverContainer;

    @FXML
    private Button publicChatButton;

    @FXML
    private Button privateChatButton;

    private int goal = 0;
    private int hand = -1;
    private ImageView selectedCard = null;
    private boolean flippedStarting = false;
    private int startingID = -1;
    private final boolean[] flippedHand = {false, false, false};
    private final int[] handIDs = {-1, -1, -1};
    private final int[] deckIDs = {-1, -1};
    private int toReplace = -1;
    private ImageView toReplaceIV = null;
    private final int[] boardIDs = {-1, -1, -1, -1};
    private int drawedID = -1;
    private final double[] CARD_SIZE = {88.0, 132.0};

    private static final double JUMP_HEIGHT = 20.0;
    private static final Duration ANIMATION_DURATION = Duration.millis(200);

    private ExecutorService executor;
    private Client client;
    private GUIApplication application;
    private boolean isPrivateChat = false;
    private Player player;

    public void setParameters(ExecutorService executor, Client client,GUIApplication application){
        this.executor = executor;
        this.client = client;
        this.application = application;
    }

    public void setScene(GameImmutable gameImmutable, String nickname){
        int cardId;
        this.player = gameImmutable.getPlayers().stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);

        //setting hand
        if(player==null) return;

        board.setDisable(true);

        cardId = player.getHand().get(0).getIdCard();
        myHandImage1.setImage(new Image(createPath(cardId)));
        myHandImage1.setDisable(true);
        handIDs[0] = cardId;
        cardId = player.getHand().get(1).getIdCard();
        myHandImage2.setImage(new Image(createPath(cardId)));
        myHandImage2.setDisable(true);
        handIDs[1] = cardId;
        cardId = player.getHand().get(2).getIdCard();
        myHandImage3.setImage(new Image(createPath(cardId)));
        myHandImage3.setDisable(true);
        handIDs[2] = cardId;

        //setting secret objectives
        cardId = player.getObjectiveBeforeChoice()[0].getIdCard();
        mySecretObjective1.setImage(new Image(createPath(cardId)));
        mySecretObjective1.setDisable(true);
        cardId = player.getObjectiveBeforeChoice()[1].getIdCard();
        mySecretObjective2.setImage(new Image(createPath(cardId)));
        mySecretObjective2.setDisable(true);

        //setting starting cards
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
        deckIDs[0] = cardId;
        deckResourcesCard.setImage(new Image(createBackPath(cardId)));
        deckResourcesCard.setDisable(true);
        cardId = gameImmutable.getBoardDeck().getCard(1).getIdCard();
        boardCard1.setImage(new Image(createPath(cardId)));
        boardCard1.setDisable(true);
        boardIDs[0] = cardId;
        cardId = gameImmutable.getBoardDeck().getCard(2).getIdCard();
        boardCard2.setImage(new Image(createPath(cardId)));
        boardCard2.setDisable(true);
        boardIDs[1] = cardId;

        //setting gold cards

        if(!gameImmutable.getDrawableDeck().getDecks().get("gold").isEmpty()){
            cardId = gameImmutable.getDrawableDeck().getDecks().get("gold").peek().getIdCard();
        }
        deckIDs[1] = cardId;
        deckGoldCard.setImage(new Image(createBackPath(cardId)));
        deckGoldCard.setDisable(true);
        cardId = gameImmutable.getBoardDeck().getCard(3).getIdCard();
        boardCard3.setImage(new Image(createPath(cardId)));
        boardCard3.setDisable(true);
        boardIDs[2] = cardId;
        cardId = gameImmutable.getBoardDeck().getCard(4).getIdCard();
        boardCard4.setImage(new Image(createPath(cardId)));
        boardCard4.setDisable(true);
        boardIDs[3] = cardId;

        flipHandCard.setDisable(true);

        for(Player p: gameImmutable.getPlayers()){
            if(!p.getNickname().equals(nickname)){
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
                color.setFitHeight(50);
                color.setFitWidth(50);
                Label name = new Label(p.getNickname());

                ImageView hand1 = new ImageView();
                ImageView hand2 = new ImageView();
                ImageView hand3 = new ImageView();
                cardId = p.getHand().get(0).getIdCard();
                hand1.setImage(new Image(createBackPath(cardId)));
                cardId = p.getHand().get(1).getIdCard();
                hand2.setImage(new Image(createBackPath(cardId)));
                cardId = p.getHand().get(2).getIdCard();
                hand3.setImage(new Image(createBackPath(cardId)));

                hand1.setFitHeight(30);
                hand1.setFitWidth(30);
                hand2.setFitHeight(30);
                hand2.setFitWidth(30);
                hand3.setFitHeight(30);
                hand3.setFitWidth(30);

                String printPoints = "Points: " + p.getCurrentPoints();
                Label points = new Label(printPoints);
                Button button = new Button("See board");
                //button.setOnMouseClicked(event -> handleSeeOtherPlayerBoardClicked(event, p.getNickname()));
                VBox vBox1 = new VBox(name, color);
                HBox hbox1 = new HBox(hand1,hand2,hand3);
                VBox vBox2 = new VBox(hbox1,points,button);
                HBox hbox2 = new HBox(vBox1,vBox2);

                otherPlayersVBox.getChildren().add(hbox2);

                receiverPrivateMessages.getItems().add(p.getNickname());

                // score board
                Platform.runLater(()->application.setScoreBoard());
                Platform.runLater(()->application.setOtherPlayerBoard());
            }
        }
    }

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

    public void myRunningTurnPlaceStarting() {
        startingCardVBox.setDisable(false);
        //ConsolePrinter.consolePrinter(String.valueOf(myStartingCard));
        myStartingCard.setDisable(false);
        flipStartingButton.setVisible(true);
        flipStartingButton.setDisable(false);
        glow(flipStartingButton);
        glow(myStartingCard);
    }

    @FXML
    public void handleStartingCardClicked(MouseEvent event){
        ConsolePrinter.consolePrinter("starting clicked");
        Node card = (Node) event.getSource();
        glow(card);
        if (myStartingCard.getUserData() == null || !(boolean) myStartingCard.getUserData()) {
            jump(card);
        }
        selectedCard = myStartingCard;
        glow(board);
        board.setDisable(false);
    }

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
                ConsolePrinter.consolePrinter(input);

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

    /*
    public void placeCardOnBoard(ImageView card, double x, double y){
        //we have to handle the case in which it is flipped
        ConsolePrinter.consolePrinter(String.valueOf(x));
        ConsolePrinter.consolePrinter(String.valueOf(y));
        ImageView newCard = new ImageView(card.getImage());
        newCard.setFitHeight(card.getFitHeight());
        newCard.setFitWidth(card.getFitWidth());
        newCard.setLayoutX(x);
        newCard.setLayoutY(y);
        if (card.equals(myStartingCard)) {
            newCard.setId(String.valueOf(startingID));
            cardsHBox.getChildren().remove(startingVBox);
        }
        else {
            if (hand > 0 && handIDs[hand - 1] >= 0) {
                newCard.setId(String.valueOf(handIDs[hand - 1]));
            }
            handIDs[hand - 1] = -1;
            hand = - 1;
        }
        newCard.setOnMouseClicked(this::handleBoardCardClick);
        board.getChildren().add(newCard);
        newCard.setDisable(false);
        card.setVisible(false);
    }
    public void startCard(GameImmutable gameImmutable, String nickname) {
        placeCardOnBoard(selectedCard, personalBoard.getWidth() / 2, personalBoard.getHeight() / 2);
        selectedCard = null;
    }
    */

    public void chosenGoal() {
        secretObjectiveVBox.setPrefWidth(132.0);
        switch (goal){
            case 1->{
                secretObjective.getChildren().remove(mySecretObjective2);
                mySecretObjective1.setEffect(null);
                mySecretObjective1.setFitWidth(132.0);
            }
            case 2->{
                secretObjective.getChildren().remove(mySecretObjective1);
                mySecretObjective2.setEffect(null);
                mySecretObjective2.setFitWidth(132.0);
            }
        }
    }

    public void cardNotPlaced(String s) {
        infoBox(s);
        myRunningTurnPlaceCard();
    }

    public void objectiveNotSelected(String s) {
        infoBox(s);
        myRunningTurnChoseObjective();
    }

    public void startAlreadyAdded(String s) {
        infoBox(s);
    }

    public void cardAlreadyAdded(String s) {
        infoBox(s);
    }

    public void wrongPhase(String s) {
        infoBox(s);
    }

    public void noResourcesDeck(String s) {
        infoBox(s);
        decks.getChildren().remove(deckResourcesCard);
        myRunningTurnDrawCard();
    }

    public void noGoldDeck(String s) {
        infoBox(s);
        decks.getChildren().remove(deckGoldCard);
        myRunningTurnDrawCard();
    }

    public void noBoardCard(String s) {
        infoBox(s);
        myRunningTurnDrawCard();
    }

    private void infoBox(String message) {
        // System.out.println("infoBox called with message: " + message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");
        alert.setHeaderText("Invalid Action");
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    public void statusInfo(String status) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("THE STATUS HAS CHANGED");
        alert.setHeaderText("New Status:");
        if (status.equals("LAST_TURN")) {
            status = status + "\nWhen it'll be your turn, you will place the last card" +
                    "\nAfter the last player has done it, the points will be counted, by checking the objectives too, " +
                    "and a the winner will be found";
        }
        alert.setContentText(status);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    public void updateBoard(GameImmutable gameImmutable, String nickname) {
        StartingCard start = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(nickname)).toList().getFirst().getStartingCard();
        placeStartCardOnBoardFromMatrix(start,personalBoard.getWidth()/2,personalBoard.getHeight()/2);
        selectedCard = null;
    }

    private void placeStartCardOnBoardFromMatrix(PlayingCard start,double x,double y) {
        board.getChildren().removeAll();
        ImageView startCard = new ImageView();
        if(start.isFlipped()){
            startCard.setImage(new Image(createBackPath(start.getIdCard())));
        }
        else {
            startCard.setImage(new Image(createPath(start.getIdCard())));
        }
        startCard.setFitWidth(myStartingCard.getFitWidth());
        startCard.setFitHeight(myStartingCard.getFitHeight());
        startCard.setLayoutX(x);
        startCard.setLayoutY(y);
        startCard.setId(String.valueOf(startingID));
        startCard.setOnMouseClicked(this::handleBoardCardClick);
        cardsHBox.getChildren().remove(startingVBox);
        board.getChildren().add(startCard);

        cardOffset(start, x, y, startCard);
    }

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

    private void cardOffset(PlayingCard card, double x, double y, ImageView cardImage) {
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
                placeCardOnBoardFromMatrix(card.getCorner(i).getCardAttached(),x+xoffset,y+yoffset);
            }
        }
    }

    public void myRunningTurnChoseObjective() {
        mySecretObjective1.setDisable(false);
        mySecretObjective2.setDisable(false);
        glow(mySecretObjective1);
        glow(mySecretObjective2);
    }

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

    public void myRunningTurnDrawCard() {
        toReplace = -1;
        toReplaceIV = null;
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

    private void jump(Node node){
        node.setUserData(true);
        node.setDisable(true);
        TranslateTransition jumpUp = new TranslateTransition(ANIMATION_DURATION, node);
        jumpUp.setByY(-20);
        jumpUp.setOnFinished(e -> dropCard(node));
        jumpUp.play();
    }

    private void dropCard(Node node) {
        TranslateTransition dropDown = new TranslateTransition(ANIMATION_DURATION, node);
        dropDown.setByY(JUMP_HEIGHT);
        dropDown.setOnFinished(e -> {
            node.setUserData(false);
            node.setDisable(false);  // Re-enable the card after the animation
        });
        dropDown.play();
    }

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
        ConsolePrinter.consolePrinter(String.valueOf(selectedCard));
        flipHandCard.setDisable(false);
    }

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

    private void disableDecks() {
        for (int i = 0; i < decks.getChildren().size(); i++) {
            decks.getChildren().get(i).setEffect(null);
            decks.getChildren().get(i).setDisable(true);
        }
    }

    public void handleDrawableCardClicked(MouseEvent mouseEvent) {
        toReplaceIV = (ImageView) mouseEvent.getSource();
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

        drawedID = boardIDs[toReplace];
        try {
            //ConsolePrinter.consolePrinter(String.valueOf(toReplace));
            String msg = "/drawBoard " + (toReplace + 1);
            ConsolePrinter.consolePrinter(msg);
            client.receiveInput(msg);
        } catch (IOException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleResourceDeckClicked() {
        toReplaceIV = deckResourcesCard;
        jump(toReplaceIV);

        for (int i = 0; i < decks.getChildren().size(); i++) {
            ImageView card = (ImageView) decks.getChildren().get(i);
            card.setEffect(null);
            card.setDisable(true);
            if (card.equals(toReplaceIV)) {
                toReplace = i;
            }
        }

        drawedID = deckIDs[toReplace];
        toReplaceIV.setImage(new Image(createPath(drawedID)));

        disableBoardCards();

        try {
            client.receiveInput("/drawResources");
        } catch (IOException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleGoldDeckClicked() {
        toReplaceIV = deckGoldCard;
        jump(toReplaceIV);

        for (int i = 0; i < decks.getChildren().size(); i++) {
            ImageView card = (ImageView) decks.getChildren().get(i);
            card.setEffect(null);
            card.setDisable(true);
            if (card.equals(toReplaceIV)) {
                toReplace = i;
            }
        }

        drawedID = deckIDs[toReplace];
        toReplaceIV.setImage(new Image(createPath(drawedID)));

        disableBoardCards();

        try {
            client.receiveInput("/drawGold");
        } catch (IOException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBoardDeck(GameImmutable gameImmutable) {
        updateDecks(gameImmutable);
        updateDrawableBoard(gameImmutable);
    }

    private void updateDecks(GameImmutable gameImmutable) {
        int cardId;

        if(!gameImmutable.getDrawableDeck().getDecks().get("resources").isEmpty()){
            cardId = gameImmutable.getDrawableDeck().getDecks().get("resources").peek().getIdCard();
            deckResourcesCard.setImage(new Image(createBackPath(cardId)));
            deckIDs[0] = cardId;
        }
        if (!gameImmutable.getDrawableDeck().getDecks().get("gold").isEmpty()) {
            cardId = gameImmutable.getDrawableDeck().getDecks().get("gold").peek().getIdCard();
            deckGoldCard.setImage(new Image(createBackPath(cardId)));
            deckIDs[1] = cardId;
        }
    }

    private void updateDrawableBoard(GameImmutable gameImmutable) {
        int cardId;
        cardId = gameImmutable.getBoardDeck().getCard(1).getIdCard();
        boardCard1.setImage(new Image(createPath(cardId)));
        boardCard1.setDisable(true);
        boardIDs[0] = cardId;
        boardCard1.setVisible(true);
        cardId = gameImmutable.getBoardDeck().getCard(2).getIdCard();
        boardCard2.setImage(new Image(createPath(cardId)));
        boardCard2.setDisable(true);
        boardIDs[1] = cardId;
        boardCard2.setVisible(true);
        cardId = gameImmutable.getBoardDeck().getCard(3).getIdCard();
        boardCard3.setImage(new Image(createPath(cardId)));
        boardCard3.setDisable(true);
        boardIDs[2] = cardId;
        boardCard3.setVisible(true);
        cardId = gameImmutable.getBoardDeck().getCard(4).getIdCard();
        boardCard4.setImage(new Image(createPath(cardId)));
        boardCard4.setDisable(true);
        boardIDs[3] = cardId;
        boardCard4.setVisible(true);
    }

    public void updateHand() {
        shiftHand();
        ImageView handCard = (ImageView) handCards.getChildren().get(hand - 1);
        if (toReplaceIV != null) {
            handCard.setImage(toReplaceIV.getImage());
        }
        handCard.setFitHeight(CARD_SIZE[0]);
        handCard.setFitWidth(CARD_SIZE[1]);
        handCard.setDisable(true);
        handCard.setVisible(true);
        handCard.setEffect(null);
        handCard.setOnMouseClicked(this::handleHandCardClicked);
        jump(handCard);
        handIDs[hand - 1] = drawedID;
        toReplace = -1;
        toReplaceIV = null;
        hand = - 1;
    }

    private void shiftHand() {
        ImageView card, next;
        for (int i = hand - 1; i < handCards.getChildren().size() - 1; i++) {
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

    public void notMyTurn() {
        disable(true);
        Label label = new Label("Not your turn");
        label.setStyle("-fx-background-color: lightblue; -fx-padding: 20; -fx-border-color: black; -fx-border-width: 2px;");
        StackPane labelContainer = new StackPane(label);
        labelContainer.setAlignment(Pos.CENTER);
        pane.getChildren().add(labelContainer);
        PauseTransition message = new PauseTransition(Duration.seconds(3));
        message.setOnFinished(event->pane.getChildren().remove(labelContainer));
        message.play();
    }

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
    public void stopGlowInfo() {
        myStartingCard.setEffect(null);
        myHandImage1.setEffect(null);
        myHandImage2.setEffect(null);
        myHandImage3.setEffect(null);
        deckGoldCard.setEffect(null);
        deckResourcesCard.setEffect(null);
        otherPlayersVBox.setEffect(null);
    }

    public void handleSeeInfoBox() {
        Platform.runLater(()-> application.infoBox());
    }
    public void handleSeeRuleBook() {
        Platform.runLater(()-> application.ruleBook());
    }


    //--------------------------------------------CHAT

    public void displayChatPublic(String message) {
        if (!isPrivateChat) {
            Text text = new Text(message);
            System.out.println(message);
            text.setWrappingWidth(scrollPane.getWidth() - 20); // Adjust width to fit scroll pane
            messageContainer.getChildren().add(text);
            scrollPane.layout();
        }
    }
    public void displayChatPrivate(String message) {
        if (isPrivateChat) {
            Text text = new Text(message);
            System.out.println(message);
            text.setWrappingWidth(scrollPane.getWidth() - 20); // Adjust width to fit scroll pane
            messageContainer.getChildren().add(text);
            scrollPane.layout();
        }
    }
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
    @FXML
    public void handleSeeScoreBoard() {
        Platform.runLater(()->application.seeScoreBoard());
    }

    //--------------------------------------------POINTS

    public void updateOtherPlayersPoints(GameImmutable gameImmutable, String playerChangedNickname) {
        Optional<Player> p = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(playerChangedNickname)).findFirst();
        String currPoints = "error";
        if(p.isPresent()){
             currPoints = "Points " + p.get().getCurrentPoints();
        }
        for(int i=1; i<otherPlayersVBox.getChildren().size(); i++){ //it starts from 1 because there are buttons before
            HBox hBox1 = (HBox) otherPlayersVBox.getChildren().get(i);
            VBox vBox1 = (VBox) hBox1.getChildren().get(0);
            Label name = (Label) vBox1.getChildren().get(0);
            if(name.getText().equals(playerChangedNickname)){
                VBox vBox2 = (VBox) hBox1.getChildren().get(1);
                Label points = (Label) vBox2.getChildren().get(1);
                points.setText(currPoints);
                //i=gameImmutable.getPlayers().size()+1; //stops the for cycle
            }
        }

    }
    public void updateOtherPlayersHand(GameImmutable gameImmutable, String playerChangedNickname) {
        Optional<Player> p = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(playerChangedNickname)).findFirst();
        if(p.isPresent())
        {
            for(int i=1; i<otherPlayersVBox.getChildren().size(); i++){ //it starts from 1 because there are buttons before
                HBox hBox2 = (HBox) otherPlayersVBox.getChildren().get(i);
                VBox vBox1 = (VBox) hBox2.getChildren().get(0);
                Label name = (Label) vBox1.getChildren().get(0);
                if(name.getText().equals(playerChangedNickname)){
                    VBox vBox2 = (VBox) hBox2.getChildren().get(1);
                    HBox hBox1 = (HBox) vBox2.getChildren().get(0);
                    ImageView hand1 = (ImageView) hBox1.getChildren().get(0);
                    ImageView hand2 = (ImageView) hBox1.getChildren().get(1);
                    ImageView hand3 = (ImageView) hBox1.getChildren().get(2);
                    int cardId = p.get().getHand().get(0).getIdCard();
                    hand1.setImage(new Image(createBackPath(cardId)));
                    cardId = p.get().getHand().get(1).getIdCard();
                    hand2.setImage(new Image(createBackPath(cardId)));
                    cardId = p.get().getHand().get(2).getIdCard();
                    hand3.setImage(new Image(createBackPath(cardId)));
                }
            }
        }
    }
    @FXML
    public void handleSeeOtherPlayersBoards() {
        Platform.runLater(()->application.seeOtherBoards());
    }
}
