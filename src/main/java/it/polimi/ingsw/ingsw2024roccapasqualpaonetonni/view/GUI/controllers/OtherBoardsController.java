package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class OtherBoardsController extends GenericController{
    @FXML
    private AnchorPane anchorPane;
    private final HBox hBox = new HBox();
    public void setBoards(GameImmutable gameImmutable, String myNickame) {
        int num = gameImmutable.getPlayers().size();
        Label name1 = new Label(gameImmutable.getPlayers().stream().filter(player -> !player.getNickname().equals(myNickame)).findFirst().map(Player::getNickname).orElse("error"));
        switch (num){
            case 2->{
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setPrefSize(800,600);
                VBox vBox = new VBox(name1,new ScrollPane(new Pane()));
                vBox.setPrefSize(800,600);
                hBox.getChildren().add(vBox);
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

    public void updateOtherBoards(int cardId,Double coord0,Double coord1,String playerChangedNickname) {
        for(int i=0; i<hBox.getChildren().size();i++){
            VBox vBox = (VBox) hBox.getChildren().get(i);
            Label name = (Label) vBox.getChildren().get(0);
            if(name.getText().equals(playerChangedNickname)){
                ScrollPane scrollPane = (ScrollPane) vBox.getChildren().get(1);
                Pane pane = (Pane) scrollPane.getContent();
                ImageView card = new ImageView();
                card.setImage(new Image(createPath(cardId)));
                placeCardOnBoard(card,coord0,coord1,pane);
            }
        }
    }
    public void placeCardOnBoard(ImageView card, double x, double y,Pane board){
        //we have to handle the case in which it is flipped
        ConsolePrinter.consolePrinter(String.valueOf(x));
        ConsolePrinter.consolePrinter(String.valueOf(y));
        ImageView newCard = new ImageView(card.getImage());
        newCard.setFitHeight(132.0);
        newCard.setFitWidth(132.0);
        newCard.setLayoutX(x);
        newCard.setLayoutY(y);
        board.getChildren().add(newCard);
        newCard.setDisable(false);
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

}

