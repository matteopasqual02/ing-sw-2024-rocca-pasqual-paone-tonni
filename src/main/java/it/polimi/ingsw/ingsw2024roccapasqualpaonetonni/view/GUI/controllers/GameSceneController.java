package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ExecutorService;

public class GameSceneController extends GenericController{
    @FXML
    private ImageView resourceCard1;
    @FXML
    private ImageView resourceCard2;
    @FXML
    private ImageView resourceCard3;
    @FXML
    private ImageView goldCard1;
    @FXML
    private ImageView goldCard2;
    @FXML
    private ImageView goldCard3;
    @FXML
    private ImageView myHandImage1;
    @FXML
    private ImageView myHandImage2;
    @FXML
    private ImageView myHandImage3;
    @FXML
    private ImageView commonObjectiveImage1;
    @FXML
    private ImageView commonObjectiveImage2;
    @FXML
    private ImageView secretObjectiveImage1;
    @FXML
    private ImageView secretObjectiveImage2;
    @FXML
    private ImageView startingCard1;
    @FXML
    private TextField publicMessage;
    @FXML
    private TextField privateReciever;
    @FXML
    private TextField privateMessage;
    @FXML
    private VBox chatBox;
    @FXML
    private VBox otherPlayersBox;
    @FXML
    private AnchorPane playerBoard;
    private ExecutorService executor;
    private Client client;
    private GUIApplication application;

    public void setParameters(ExecutorService executor, Client client,GUIApplication application){
        this.executor = executor;
        this.client = client;
        this.application = application;
    }
    public void setScene(GameImmutable gameImmutable, String nickname){
        int cardId;
        Player player = gameImmutable.getPlayers().stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);

        //setting hand
        if(player==null)return;

        playerBoard = new AnchorPane();

        cardId = player.getHand().get(0).getIdCard();
        myHandImage1.setImage(new Image(createPath(cardId)));
        cardId = player.getHand().get(1).getIdCard();
        myHandImage2.setImage(new Image(createPath(cardId)));
        cardId = player.getHand().get(2).getIdCard();
        myHandImage3.setImage(new Image(createPath(cardId)));

        //setting secret objectives
        cardId = player.getObjectiveBeforeChoice()[0].getIdCard();
        secretObjectiveImage1.setImage(new Image(createPath(cardId)));
        cardId = player.getObjectiveBeforeChoice()[1].getIdCard();
        secretObjectiveImage2.setImage(new Image(createPath(cardId)));

        //setting starting cards
        cardId = player.getStartingCard().getIdCard();
        startingCard1.setImage(new Image(createPath(cardId)));

        //setting common objectives

        cardId = gameImmutable.getBoardDeck().getCommonObjective(0).getIdCard();
        commonObjectiveImage1.setImage(new Image(createPath(cardId)));

        cardId = gameImmutable.getBoardDeck().getCommonObjective(1).getIdCard();
        commonObjectiveImage2.setImage(new Image(createPath(cardId)));

        //setting resource cards
        if(!gameImmutable.getDrawableDeck().getDecks().get("resources").isEmpty()){
            cardId = gameImmutable.getDrawableDeck().getDecks().get("resources").peek().getIdCard();
        }
        resourceCard1.setImage(new Image(createBackPath(cardId)));
        cardId = gameImmutable.getBoardDeck().getCard(1).getIdCard();
        resourceCard2.setImage(new Image(createPath(cardId)));
        cardId = gameImmutable.getBoardDeck().getCard(2).getIdCard();
        resourceCard3.setImage(new Image(createPath(cardId)));

        //setting gold cards

        if(!gameImmutable.getDrawableDeck().getDecks().get("gold").isEmpty()){
            cardId = gameImmutable.getDrawableDeck().getDecks().get("gold").peek().getIdCard();
        }
        goldCard1.setImage(new Image(createBackPath(cardId)));
        cardId = gameImmutable.getBoardDeck().getCard(3).getIdCard();
        goldCard2.setImage(new Image(createPath(cardId)));
        cardId = gameImmutable.getBoardDeck().getCard(4).getIdCard();
        goldCard3.setImage(new Image(createPath(cardId)));

        for(Player p: gameImmutable.getPlayers()){
            if(!p.getNickname().equals(nickname)){
                ImageView color = null;
                if(p.getColorPlayer() == 1){
                    color = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png")));
                } else if (p.getColorPlayer() == 2) {
                    color = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png")));
                } else if (p.getColorPlayer() == 3) {
                    color = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png")));
                } else if (p.getColorPlayer() == 4) {
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
                VBox vBox1 = new VBox(name,color);
                HBox hbox1 = new HBox(hand1,hand2,hand3);
                VBox vBox2 = new VBox(hbox1,points,button);
                HBox hbox2 = new HBox(vBox1,vBox2);

                otherPlayersBox.getChildren().add(hbox2);
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

    public void handleSendPublic(ActionEvent actionEvent) {
        executor.submit(()->{
            try {
                client.receiveInput("/chat "+ publicMessage.getText());
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void handleSendPrivate(ActionEvent actionEvent) {
        executor.submit(()->{
            try {
                client.receiveInput("/chatPrivate "+ privateReciever.getText() + " " + privateMessage.getText());
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void handleSeePublicChat(ActionEvent actionEvent) {
        executor.submit(()->{
            try {
                client.receiveInput("/seeChat");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void handleSeePrivateChat(ActionEvent actionEvent) {
        executor.submit(()->{
            try {
                client.receiveInput("/seeChatPrivate");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void myRunningTurnPlaceStarting() {
        // Effetto di illuminazione
        DropShadow borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.BLUE);
        borderGlow.setWidth(30);
        borderGlow.setHeight(30);
        startingCard1.setEffect(borderGlow);

    }
    @FXML
    public void handleStartingCardClicked(MouseEvent event){
        BoxBlur blur = new BoxBlur();
        blur.setWidth(5);
        blur.setHeight(5);
        blur.setIterations(3);
        startingCard1.setEffect(blur);
    }

    public void handleBoardClick(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        placeCardOnBoard(x, y);
    }
    public void placeCardOnBoard(double x, double y){
        //we have to handle the case in which it is flipped
        executor.submit(()->{
            try {
                client.receiveInput("/addStarting");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
        playerBoard.getChildren().add(startingCard1);
    }
}
