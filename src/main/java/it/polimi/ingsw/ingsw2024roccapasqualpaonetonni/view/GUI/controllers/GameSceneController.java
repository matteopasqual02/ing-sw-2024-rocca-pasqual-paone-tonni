package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ExecutorService;

public class GameSceneController extends GenericController{

    @FXML
    public Pane pane;

    // player board
    @FXML
    public ScrollPane personalBoard;

    @FXML
    public Pane board;

    // cards section
    @FXML
    public HBox cardsHBox;

    @FXML
    public VBox myHand;

    @FXML
    public HBox handCards;

    @FXML
    public ImageView myHandImage1;

    @FXML
    public ImageView myHandImage2;

    @FXML
    public ImageView myHandImage3;

    @FXML
    public Button flipHandCard;

    @FXML
    public VBox secretObjective;

    @FXML
    public ImageView mySecretObjective1;

    @FXML
    public ImageView mySecretObjective2;

    @FXML
    public VBox startingVBox;

    @FXML
    public VBox startingCardVBox;

    @FXML
    public ImageView myStartingCard;

    @FXML
    public Button flipStartingButton;

    @FXML
    public HBox boardCards;

    @FXML
    public ImageView boardCard1;

    @FXML
    public ImageView boardCard2;

    @FXML
    public ImageView boardCard3;

    @FXML
    public ImageView boardCard4;

    @FXML
    public HBox decks;

    @FXML
    public ImageView deckResourcesCard;

    @FXML
    public ImageView deckGoldCard;

    @FXML
    public HBox commonObjectives;

    @FXML
    public ImageView commonObjectiveCard1;

    @FXML
    public ImageView commonObjectiveCard2;

    @FXML
    public VBox otherPlayersVBox;

    // chat section
    @FXML
    public VBox chatVBox;

    @FXML
    private ComboBox receiverPrivateMessages;

    @FXML
    private VBox startCardVbox;

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
    private int hand = 4;
    private final Object jumpLock = new Object();
    private ImageView selectedCard = null;
    private boolean flippedStarting = false;
    private boolean flippedHand1 = false;
    private boolean flippedHand2 = false;
    private boolean flippedHand3 = false;

    private ExecutorService executor;
    private Client client;
    private GUIApplication application;
    private boolean isPrivateChat = false;
    private Player player;
    private String myNickname;

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
        cardId = player.getHand().get(1).getIdCard();
        myHandImage2.setImage(new Image(createPath(cardId)));
        myHandImage2.setDisable(true);
        cardId = player.getHand().get(2).getIdCard();
        myHandImage3.setImage(new Image(createPath(cardId)));
        myHandImage3.setDisable(true);

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

        for(Player p: gameImmutable.getPlayers()){
            if(!p.getNickname().equals(nickname)){
                ImageView color = null;
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
                VBox vBox1 = new VBox(name, color);
                HBox hbox1 = new HBox(hand1,hand2,hand3);
                VBox vBox2 = new VBox(hbox1,points,button);
                HBox hbox2 = new HBox(vBox1,vBox2);

                otherPlayersVBox.getChildren().add(hbox2);

                receiverPrivateMessages.getItems().add(p.getNickname());
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
        disable(false);
        glow(myStartingCard);
    }

    @FXML
    public void handleStartingCardClicked(){
        synchronized (jumpLock) {
            int res = jump(myStartingCard);
        }
        this.selectedCard = myStartingCard;
        board.setDisable(false);
    }

    @FXML
    public void handleFlipStarting() {
        int cardId = this.player.getStartingCard().getIdCard();
        if (!flippedStarting) {
            myStartingCard.setImage(new Image(createBackPath(cardId)));
            flippedStarting = true;
            selectedCard = myStartingCard;
        }
        else {
            myStartingCard.setImage(new Image(createPath(cardId)));
            flippedStarting = false;
            selectedCard = myStartingCard;
        }
    }

    public void handleBoardClick(MouseEvent mouseEvent) {
        String flipped;
        if (selectedCard != null) {
            board.setDisable(true);
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            if (this.selectedCard == myStartingCard) {
                if (flippedStarting) {
                    flipped = " true";
                } else {
                    flipped = "";
                }
                executor.submit(() -> {
                    try {
                        client.receiveInput("/addStarting" + flipped);
                    } catch (IOException | NotBoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                cardsHBox.getChildren().remove(startingVBox);
            }
        }
    }

    public void placeCardOnBoard(ImageView card, double x, double y){
        //we have to handle the case in which it is flipped
        ImageView newCard = new ImageView(card.getImage());
        newCard.setFitHeight(30);
        newCard.setFitWidth(30);
        newCard.setLayoutX(x);
        newCard.setLayoutY(y);
        board.getChildren().add(newCard);
        card.setVisible(false);
    }

    public void startCard(GameImmutable gameImmutable, String nickname) {
        // i have to check if it's my turn because everyone gets these updates but only the player in turn has to update its gui
        if(nickname.equals(myNickname)) {
            placeCardOnBoard(myStartingCard, board.getWidth() / 2 - 50, board.getHeight() / 2 - 70);
            cardsHBox.getChildren().remove(startingCardVBox);
        }
    }

    public void chosenGoal() {
        if(client.getMyTurn()){
            switch (goal){
                case 1->{
                    secretObjective.getChildren().remove(mySecretObjective2);
                    mySecretObjective1.setEffect(null);
                }
                case 2->{
                    secretObjective.getChildren().remove(mySecretObjective1);
                    mySecretObjective2.setEffect(null);
                }
            }
        }
    }

    public void myRunningTurnChoseObjective() {
        glow(mySecretObjective1);
        glow(mySecretObjective2);
    }

    public void glow(ImageView image){
        // blue shadow
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.BLUE);
        borderGlow.setWidth(30);
        borderGlow.setHeight(30);
        image.setEffect(borderGlow);
    }

    public int jump(ImageView image){
        synchronized (jumpLock) {
            TranslateTransition jump = new TranslateTransition(Duration.millis(500), image);
            jump.setByY(-20);
            jump.setAutoReverse(true);
            jump.setCycleCount(2);
            jump.play();
            return 1;
        }
    }

    public void handleObjectiveCard2Clicked(MouseEvent mouseEvent) {
        mySecretObjective1.setEffect(null);
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

    public void handleObjectiveCard1Clicked(MouseEvent mouseEvent) {
        mySecretObjective2.setEffect(null);
        jump(mySecretObjective1);
        goal = 1;
        executor.submit(()->{
            try {
                client.receiveInput("/choseGoal 1");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void myRunningTurnPlaceCard() {
        disable(false);
        glow(myHandImage1);
        glow(myHandImage2);
        glow(myHandImage3);
    }

    public void handleHandCardClicked(MouseEvent mouseEvent) {
        ImageView card = (ImageView) mouseEvent.getSource();
        for (int i = 0; i < handCards.getChildren().size(); i++) {
            if (handCards.getChildren().get(i) != card) {
                handCards.getChildren().get(i).setEffect(null);}
            else {
                hand = i + 1;
            }
        }
        jump(card);
        selectedCard = card;
        board.setDisable(false);
    }

    public void handleFlipHandCard() {

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

    public void disable(Boolean truefalse){
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


    // chat
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

    public void handleSend(ActionEvent actionEvent) {
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
    public void handleSeePublicChat(ActionEvent actionEvent) {
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
    public void handleSeePrivateChat(ActionEvent actionEvent) {
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
    public void handleSelectPlayerChat(ActionEvent actionEvent) {
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
}
