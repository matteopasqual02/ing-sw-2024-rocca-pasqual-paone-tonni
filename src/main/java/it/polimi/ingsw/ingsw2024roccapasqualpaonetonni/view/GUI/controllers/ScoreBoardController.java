package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

/**
 * The type Score board controller.
 */
public class ScoreBoardController extends GenericController{
    /**
     * The Image 0.
     */
    @FXML
    private ImageView image0;
    /**
     * The Image 1.
     */
    @FXML
    private ImageView image1;
    /**
     * The Image 2.
     */
    @FXML
    private ImageView image2;
    /**
     * The Image 3.
     */
    @FXML
    private ImageView image3;
    /**
     * The Image 4.
     */
    @FXML
    private ImageView image4;
    /**
     * The Image 5.
     */
    @FXML
    private ImageView image5;
    /**
     * The Image 6.
     */
    @FXML
    private ImageView image6;
    /**
     * The Image 7.
     */
    @FXML
    private ImageView image7;
    /**
     * The Image 8.
     */
    @FXML
    private ImageView image8;
    /**
     * The Image 9.
     */
    @FXML
    private ImageView image9;
    /**
     * The Image 10.
     */
    @FXML
    private ImageView image10;
    /**
     * The Image 11.
     */
    @FXML
    private ImageView image11;
    /**
     * The Image 12.
     */
    @FXML
    private ImageView image12;
    /**
     * The Image 13.
     */
    @FXML
    private ImageView image13;
    /**
     * The Image 14.
     */
    @FXML
    private ImageView image14;
    /**
     * The Image 15.
     */
    @FXML
    private ImageView image15;
    /**
     * The Image 16.
     */
    @FXML
    private ImageView image16;
    /**
     * The Image 17.
     */
    @FXML
    private ImageView image17;
    /**
     * The Image 18.
     */
    @FXML
    private ImageView image18;
    /**
     * The Image 19.
     */
    @FXML
    private ImageView image19;
    /**
     * The Image 20.
     */
    @FXML
    private ImageView image20;
    /**
     * The Image 21.
     */
    @FXML
    private ImageView image21;
    /**
     * The Image 22.
     */
    @FXML
    private ImageView image22;
    /**
     * The Image 23.
     */
    @FXML
    private ImageView image23;
    /**
     * The Image 24.
     */
    @FXML
    private ImageView image24;
    /**
     * The Image 25.
     */
    @FXML
    private ImageView image25;
    /**
     * The Image 26.
     */
    @FXML
    private ImageView image26;
    /**
     * The Image 27.
     */
    @FXML
    private ImageView image27;
    /**
     * The Image 28.
     */
    @FXML
    private ImageView image28;
    /**
     * The Image 29.
     */
    @FXML
    private ImageView image29;

    /**
     * The Anchor.
     */
    @FXML
    private AnchorPane anchor;
    /**
     * The Position map.
     */
    private HashMap<Integer,ImageView> positionMap;
    /**
     * The Player image.
     */
    private HashMap<Integer,ImageView> playerImage;
    /**
     * The X.
     */
    private int x=0;
    /**
     * The Y.
     */
    private int y=0;

    /**
     * Set parameters.
     *
     * @param executor    the executor
     * @param client      the client
     * @param application the application
     */
    public void setParameters(ExecutorService executor, Client client, GUIApplication application){
        positionMap = new HashMap<>();
        playerImage = new HashMap<>();

        positionMap.put(0,image0);
        positionMap.put(1, image1);
        positionMap.put(2, image2);
        positionMap.put(3, image3);
        positionMap.put(4, image4);
        positionMap.put(5, image5);
        positionMap.put(6, image6);
        positionMap.put(7, image7);
        positionMap.put(8, image8);
        positionMap.put(9, image9);
        positionMap.put(10, image10);
        positionMap.put(11, image11);
        positionMap.put(12, image12);
        positionMap.put(13, image13);
        positionMap.put(14, image14);
        positionMap.put(15, image15);
        positionMap.put(16, image16);
        positionMap.put(17, image17);
        positionMap.put(18, image18);
        positionMap.put(19, image19);
        positionMap.put(20, image20);
        positionMap.put(21, image21);
        positionMap.put(22, image22);
        positionMap.put(23, image23);
        positionMap.put(24, image24);
        positionMap.put(25, image25);
        positionMap.put(26, image26);
        positionMap.put(27, image27);
        positionMap.put(28, image28);
        positionMap.put(29, image29);
    }

    /**
     * Sets starting pawns.
     *
     * @param gameImmutable the game immutable
     */
    public void setStartingPawns(GameImmutable gameImmutable) {
        ImageView reference;
        for(Player p: gameImmutable.getPlayers()){
            if (p.getColorPlayer() == 1) {
                playerImage.put(1,new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png"))));
                x=-5;
                y=-5;
            } else if (p.getColorPlayer() == 2) {
                playerImage.put(2,new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png"))));
                x=-5;
                y=5;
            } else if (p.getColorPlayer() == 3) {
                playerImage.put(3,new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png"))));
                x=5;
                y=-5;
            } else {
                playerImage.put(4,new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_jaune.png"))));
                x=5;
                y=5;
            }
            reference = positionMap.get(p.getCurrentPoints());
            anchor.getChildren().add(playerImage.get(p.getColorPlayer()));
            playerImage.get(p.getColorPlayer()).setLayoutX(reference.getLayoutX() + x);
            playerImage.get(p.getColorPlayer()).setLayoutY(reference.getLayoutY() + y);
            playerImage.get(p.getColorPlayer()).setFitWidth(reference.getFitWidth());
            playerImage.get(p.getColorPlayer()).setFitHeight(reference.getFitHeight());
            playerImage.get(p.getColorPlayer()).setPreserveRatio(true);
        }
    }

    /**
     * Update score board.
     *
     * @param gameImmutable the game immutable
     */
    public void updateScoreBoard(GameImmutable gameImmutable){
        int color;
        for(Player p: gameImmutable.getPlayers()){
            color = p.getColorPlayer();
            ImageView toRemove = playerImage.get(color);
            anchor.getChildren().remove(toRemove);
            if (color == 1) {
                playerImage.put(1,new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png"))));
                x=-5;
                y=-5;
            } else if (color == 2) {
                playerImage.put(2,new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png"))));
                x=-5;
                y=5;
            } else if (color == 3) {
                playerImage.put(3,new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png"))));
                x=5;
                y=-5;
            } else {
                playerImage.put(4,new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_jaune.png"))));
                x=5;
                y=5;
            }
            ImageView reference = positionMap.get(p.getCurrentPoints());
            anchor.getChildren().add(playerImage.get(color));
            playerImage.get(color).setLayoutX(reference.getLayoutX() + x);
            playerImage.get(color).setLayoutY(reference.getLayoutY() + y);
            playerImage.get(color).setFitWidth(reference.getFitWidth());
            playerImage.get(color).setFitHeight(reference.getFitHeight());
            playerImage.get(color).setPreserveRatio(true);
        }
    }
}
