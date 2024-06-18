package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OtherBoardsController extends GenericController{

    @FXML
    public Label nicknameLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pane boardPane;

    private final HBox hBox = new HBox();
    private final double[] CARD_SIZE = {88.0, 132.0};
    private final double[] BOARD_SIZE = {1500.0, 2000.0};
    private final Map<String, Pane> boardsDict = new HashMap<>();

    /*
    public void setBoards(GameImmutable gameImmutable, String myNickame) {
        int num = gameImmutable.getPlayers().size();
        Label name1 = new Label(gameImmutable.getPlayers().stream().filter(player -> !player.getNickname().equals(myNickame)).findFirst().map(Player::getNickname).orElse("error"));
        switch (num){
            case 2->{
                ScrollPane scrollPane = new ScrollPane(new Pane());
                scrollPane.setPrefSize(800,600);
                VBox vBox = new VBox(name1,scrollPane);
                vBox.setPrefSize(800,600);
                VBox.setVgrow(scrollPane, Priority.ALWAYS);
                VBox.setVgrow(vBox, Priority.ALWAYS);
                HBox.setHgrow(vBox, Priority.ALWAYS);
                hBox.getChildren().add(vBox);

                AnchorPane.setTopAnchor(hBox, 0.0);
                AnchorPane.setBottomAnchor(hBox, 0.0);
                AnchorPane.setLeftAnchor(hBox, 0.0);
                AnchorPane.setRightAnchor(hBox, 0.0);
            }
            case 3->{
                Label name2 = new Label(gameImmutable.getPlayers().stream().filter(player -> !player.getNickname().equals(myNickame)).skip(1).findFirst().map(Player::getNickname).orElse("error"));
                VBox vBox1 = new VBox(name1,new ScrollPane(new Pane()));
                VBox vBox2 = new VBox(name2,new ScrollPane(new Pane()));
                hBox.getChildren().add(vBox1);
                hBox.getChildren().add(vBox2);
            }
            case 4->{
                Label name2 = new Label(gameImmutable.getPlayers().stream().filter(player -> !player.getNickname().equals(myNickame)).skip(1).findFirst().map(Player::getNickname).orElse("error"));
                Label name3 = new Label(gameImmutable.getPlayers().stream().filter(player -> !player.getNickname().equals(myNickame)).skip(2).findFirst().map(Player::getNickname).orElse("error"));
                VBox vBox1 = new VBox(name1,new ScrollPane(new Pane()));
                VBox vBox2 = new VBox(name2,new ScrollPane(new Pane()));
                VBox vBox3 = new VBox(name3,new ScrollPane(new Pane()));
                hBox.getChildren().add(vBox1);
                hBox.getChildren().add(vBox2);
                hBox.getChildren().add(vBox3);
            }
        }
        anchorPane.getChildren().add(hBox);
    }

    public void insertStartCard(GameImmutable gameImmutable,String playerChangedNickname){
        int cardId = gameImmutable.getPlayers().stream().filter(player -> player.getNickname().equals(playerChangedNickname)).findFirst().map(player -> player.getStartingCard().getIdCard()).orElse(-1);
        if (cardId != -1) {
            for(int i=0; i<hBox.getChildren().size();i++){
                VBox vBox = (VBox) hBox.getChildren().get(i);
                Label name = (Label) vBox.getChildren().get(0);
                if(name.getText().equals(playerChangedNickname)){
                    ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(1);
                    Pane pane = (Pane) scrollPane.getContent();
                    ImageView card = new ImageView();
                    card.setImage(new Image(createPath(cardId)));
                    placeCardOnBoard(card,scrollPane.getWidth()/2,scrollPane.getHeight()/2,pane);
                }
            }
        }

    }

    public void updateOtherBoards(int cardId,String playerChangedNickname) {
        for(int i=0; i<hBox.getChildren().size();i++){
            VBox vBox = (VBox) hBox.getChildren().get(i);
            Label name = (Label) vBox.getChildren().get(0);
            if(name.getText().equals(playerChangedNickname)){
                ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(1);
                Pane pane = (Pane) scrollPane.getContent();
                ImageView card = new ImageView();
                card.setImage(new Image(createPath(cardId)));
                placeCardOnBoard(card,coord0*scrollPane.getWidth(),coord1*scrollPane.getHeight(),pane);
            }
        }
    }

    public void placeCardOnBoard(ImageView card, double x, double y,Pane board){
        //we have to handle the case in which it is flipped
        ConsolePrinter.consolePrinter(String.valueOf(x));
        ConsolePrinter.consolePrinter(String.valueOf(y));
        ImageView newCard = new ImageView(card.getImage());
        newCard.setFitHeight(88);
        newCard.setFitWidth(132);
        newCard.setLayoutX(x);
        newCard.setLayoutY(y);
        board.getChildren().add(newCard);
        newCard.setDisable(false);
    }
    */

    public void showBoard(String nickname) {
        Pane board = boardsDict.get(nickname);
        if (board == null) {
            return;
        }
        nicknameLabel.setText(nickname);
        boardPane = board;
        scrollPane.setContent(boardPane);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);
        //centerBoard();
    }

    private void centerBoard() {
        double scrollPaneWidth = scrollPane.getViewportBounds().getWidth();
        double scrollPaneHeight = scrollPane.getViewportBounds().getHeight();
        double contentWidth = boardPane.getLayoutBounds().getWidth();
        double contentHeight = boardPane.getLayoutBounds().getHeight();

        double offsetX = (scrollPaneWidth - contentWidth) / 2;
        double offsetY = (scrollPaneHeight - contentHeight) / 2;

        boardPane.setLayoutX(Math.max(offsetX, 0));
        boardPane.setLayoutY(Math.max(offsetY, 0));
    }

    public void updateOtherBoard(GameImmutable gameImmutable, String nickname) {
        Pane board = boardsDict.get(nickname);
        if (board == null) {
            board = new Pane();
            board.setPrefHeight(BOARD_SIZE[0]);
            board.setPrefWidth(BOARD_SIZE[1]);
            board.applyCss();
            board.layout();
            boardsDict.put(nickname, board);
        }
        updateBoard(gameImmutable, board, nickname);
    }

    private void updateBoard(GameImmutable gameImmutable, Pane board, String nickname) {
        StartingCard start = gameImmutable.getPlayers().stream().filter(player1 -> player1.getNickname().equals(nickname)).toList().getFirst().getStartingCard();
        placeStartCardOnBoardFromMatrix(start, board, BOARD_SIZE[1]/2 - CARD_SIZE[1]/2,BOARD_SIZE[0]/2 - CARD_SIZE[0]/2);
    }

    private void placeStartCardOnBoardFromMatrix(PlayingCard start, Pane board, double x,double y) {
        board.getChildren().removeAll();
        ImageView startCard = new ImageView();
        if(start.isFlipped()){
            startCard.setImage(new Image(createBackPath(start.getIdCard())));
        }
        else {
            startCard.setImage(new Image(createPath(start.getIdCard())));
        }
        startCard.setFitHeight(CARD_SIZE[0]);
        startCard.setFitWidth(CARD_SIZE[1]);
        startCard.setLayoutX(x);
        startCard.setLayoutY(y);
        board.getChildren().add(startCard);

        cardOffset(board, start, x, y, startCard);
    }

    private void placeCardOnBoardFromMatrix(Pane board, PlayingCard card, double x, double y){
        ImageView cardImage = new ImageView();
        if(card.isFlipped()){
            cardImage.setImage(new Image(createBackPath(card.getIdCard())));
        }
        else {
            cardImage.setImage(new Image(createPath(card.getIdCard())));
        }
        cardImage.setFitHeight(CARD_SIZE[0]);
        cardImage.setFitWidth(CARD_SIZE[1]);
        cardImage.setLayoutX(x);
        cardImage.setLayoutY(y);
        board.getChildren().add(cardImage);

        cardOffset(board, card, x, y, cardImage);
    }

    private void cardOffset(Pane board, PlayingCard card, double x, double y, ImageView cardImage) {
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
                placeCardOnBoardFromMatrix(board, card.getCorner(i).getCardAttached(),x+xoffset,y+yoffset);
            }
        }
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

}

