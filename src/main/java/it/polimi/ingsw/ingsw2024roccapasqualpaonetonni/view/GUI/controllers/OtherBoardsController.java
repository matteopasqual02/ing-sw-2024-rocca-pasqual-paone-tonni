package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    public boolean showBoard(String nickname) {
        Pane board = boardsDict.get(nickname);
        if (board == null) {
            return false;
        }
        nicknameLabel.setText(nickname);
        boardPane = board;
        scrollPane.setContent(boardPane);
        scrollPane.setHvalue(0.5);
        scrollPane.setVvalue(0.5);
        return true;
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
        board.getChildren().clear();
        board.applyCss();
        board.layout();
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

