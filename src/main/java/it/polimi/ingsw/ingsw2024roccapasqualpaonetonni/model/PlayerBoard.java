package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Corner;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultModelValues;
import org.fusesource.jansi.Ansi;

import java.io.Console;
import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Player board.
 */
public class PlayerBoard implements Serializable {
    /**
     * The Dim x.
     */
    private int dim_x;
    /**
     * The Dim y.
     */
    private int dim_y;
    /**
     * The Board.
     */
    private PlayingCard[][] board;

    /**
     * The Player.
     */
    private final Player player;

    /**
     * Instantiates a new Player board.
     *
     * @param owner the owner
     */
    public PlayerBoard(Player owner) {
        dim_x = DefaultModelValues.Default_Board_Dim_X;
        dim_y = DefaultModelValues.Default_Board_Dim_Y;
        board = new PlayingCard[dim_x][dim_y];
        player = owner;
    }

    /**
     * Add card to board.
     *
     * @param coordinates the coordinates
     * @param card        the card
     * @param seedCount   the seed count
     */
// method that receives a card and the coordinates where to put it,
    // it then checks if the coordinates are inside the matrix, and if they aren't calls a method to resize the matrix
    private void addCardToBoard(int[] coordinates, PlayingCard card, int[] seedCount) {
        int x = coordinates[0];
        int y = coordinates[1];
        int x_increase = 0;
        int y_increase = 0;

        if (x >= dim_x) {
            x_increase = x - dim_x + 1;
        }
        else if (x < 0) {
            x_increase = x;
        }
        if (y >= dim_y) {
            y_increase = y - dim_y + 1;
        }
        else if (y < 0) {
            y_increase = y;
        }
        if (x_increase != 0 || y_increase != 0) {
            board = increaseBoard(x_increase, y_increase);
        }
        if (x < 0) {
            x -= x_increase;
        }
        if (y < 0) {
            y -= y_increase;
        }
        board[x][y] = card;
        card.setCoordinates(x,y);
        player.updateSeedCount(calculateSeedUpdate(x, y));
        if(!card.isFlipped()) {
            player.increasePoints(card.calculatePoints(board, seedCount, x, y));
        }
    }

    /**
     * Increase board playing card [ ] [ ].
     *
     * @param x_increase the x increase
     * @param y_increase the y increase
     * @return the playing card [ ] [ ]
     */
// method used to resize the matrix, by creating a new one and copying the old elements
    private PlayingCard[][] increaseBoard(int x_increase, int y_increase) {
        int row_offset = 0;
        int col_offset = 0;
        PlayingCard[][] tmp_board = board;
        board = new PlayingCard[dim_x+ Math.abs(x_increase)][dim_y+ Math.abs(y_increase)];

        if (x_increase < 0) {
            row_offset = - x_increase;
        }
        if (y_increase < 0) {
            col_offset = - y_increase;
        }
        PlayingCard card;
        for (int row = 0; row < dim_x; row++) {
            for (int col = 0; col < dim_y; col++) {
                board[row_offset + row][col_offset + col] = tmp_board[row][col];
                card = board[row_offset + row][col_offset + col];
                if (card != null) {
                    card.setCoordinates(row_offset + row, col_offset + col);
                }
            }
        }
        dim_x = dim_x + Math.abs(x_increase);
        dim_y = dim_y + Math.abs(y_increase);
        return board;
    }

    /**
     * Add card.
     *
     * @param card_to_add   the card to add
     * @param card_on_board the card on board
     * @param corner        the corner
     * @param seedCount     the seed count
     * @throws InvalidPlaceException     the invalid place exception
     * @throws ConditionsNotMetException the conditions not met exception
     */
// public method used to add a new card to the board
    // the position where to add the card is given by indicating the card and the corner where to attach it
    public void addCard(PlayingCard card_to_add, PlayingCard card_on_board, int corner, int[] seedCount) throws InvalidPlaceException, ConditionsNotMetException{
        int[] prev_cord = card_on_board.getCoordinates();
        int[] place_cord = new int[2];
        if (corner == 1) {
            place_cord[0] = prev_cord[0] - 1;
            place_cord[1] = prev_cord[1] - 1;
        }
        else if (corner == 2) {
            place_cord[0] = prev_cord[0] - 1;
            place_cord[1] = prev_cord[1] + 1;
        }
        else if (corner == 3) {
            place_cord[0] = prev_cord[0] + 1;
            place_cord[1] = prev_cord[1] + 1;
        }
        else if (corner == 4) {
            place_cord[0] = prev_cord[0] + 1;
            place_cord[1] = prev_cord[1] - 1;
        }
        if (checkSpotAvailable(place_cord)) {
            int[] tmp = card_to_add.checkRequirements(seedCount);
            ConsolePrinter.consolePrinter("Player board " + String.valueOf(tmp[0]));

            if (card_to_add.isFlipped() || tmp[0] == 1) {

                for(int i=0;i<board.length;i++){
                    for(int j=0;j<board[i].length;j++){
                        if(board[i][j]!=null && board[i][j].getIdCard()==card_on_board.getIdCard()){
                            board[i][j].getCorner(corner).setCardAttached(card_to_add);
                        }
                    }
                }

                addCardToBoard(place_cord, card_to_add, seedCount);
            }
            else {
                ConsolePrinter.consolePrinter("Not enough seed type " + (Seed.getById(tmp[1]) != null ? Seed.getById(tmp[1]).getName() : null));
                throw new ConditionsNotMetException("Not enough seed type " + (Seed.getById(tmp[1]) != null ? Seed.getById(tmp[1]).getName() : null));
            }
        }
        else {
            ConsolePrinter.consolePrinter("invalid position");
            throw new InvalidPlaceException("The card cannot be placed in the chosen position");
        }
    }

    /**
     * Add starting card.
     *
     * @param firstCard the first card
     */
    public void addStartingCard(StartingCard firstCard){
        int x = dim_x/2;
        int y = dim_y/2;
        board[x][y] = firstCard;
        firstCard.setCoordinates(x,y);
        player.setStartingCard(firstCard);
        player.updateSeedCount(calculateCenterUpdate(firstCard));
    }

    /**
     * Calculate center update int [ ].
     *
     * @param c the c
     * @return the int [ ]
     */
    private int[] calculateCenterUpdate(StartingCard c) {
        int[] seedUpdate = {0, 0, 0, 0, 0, 0, 0};
        Corner current_corner;
        Seed current_seed;
        for(int i=0; i<4; i++){
            current_corner = c.getCorner(i+1);
            if(current_corner!=null){
                current_seed=current_corner.getSeed();
                if(current_seed != Seed.EMPTY){
                    seedUpdate[current_seed.getId()]++;
                }
            }
        }
        if(!c.isFlipped()){
            for (int i=0; i<4; i++){
                if(c.getCenter()[i]) {
                    seedUpdate[i] += 1;
                }
            }
        }
        return seedUpdate;
    }

    /**
     * Check spot available boolean.
     *
     * @param coordinates the coordinates
     * @return the boolean
     */
// checks the four spots around the position where we want to place the card
    // if there is a card, it checks if the corners are compatible
    private boolean checkSpotAvailable(int[] coordinates) {
        PlayingCard cardOnBoard;
        int x = coordinates[0];
        int y = coordinates[1];
        int[][] positions = {{-1, -1, 1}, {-1, 1, 2}, {1, 1, 3}, {1, -1, 4}};
        //If we're adding a card that goes on the border we cannot check its corners because it would be an OutOfBoundsException, and there is no need to do so because we know that after the borders we have nothing
        if(x >= 0 && x < dim_x && y >= 0 && y < dim_y){
            if (board[x][y] != null) {
                return false;
            }
        }

        for (int[] i : positions) {
            //If we're adding a card that goes on the border we cannot check its corners because it would be an OutOfBoundsException,
            //so we check if we are looking at a border, and if we are we know that there is no card there so there's no need to check if the corner is okay
            //The condition >= might be wrong when adding multiple cards over the border, for now this is the only way to make the test work, but we will have to look into it more
            if((x+i[0]) >= 0 && (x+i[0]) < dim_x && (y+i[1]) >= 0 && (y+i[1]) < dim_y){
                cardOnBoard = board[x + i[0]][y + i[1]];
                int cornerToCheck = (i[2] == 1 || i[2] == 2) ? i[2] + 2 : i[2] - 2;
                if (cardOnBoard != null && cardOnBoard.getCorner(cornerToCheck) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calculate seed update int [ ].
     *
     * @param xNewCard the x new card
     * @param yNewCard the y new card
     * @return the int [ ]
     */
    private int[] calculateSeedUpdate(int xNewCard, int yNewCard) {
        PlayingCard card, cardAttached;
        int[] seedUpdate = {0, 0, 0, 0, 0, 0, 0};
        int[][] positions = {{-1, -1, 1}, {-1, 1, 2}, {1, 1, 3}, {1, -1, 4}};
        Corner c;
        int j;

        //forall corners
        for (int[] i : positions) {
            //card is the card placed then increase seed into seedCount
            card = board[xNewCard][yNewCard];
            if(!card.isFlipped()){
                c = card.getCorner(i[2]);
                if (c != null ) {
                    j = c.getSeed().getId();
                    if (j < 7) {
                        seedUpdate[j] += 1;
                    }
                }
            }

            if((xNewCard + i[0]) >= 0 && (xNewCard + i[0]) < dim_x && (yNewCard + i[1]) >= 0 && (yNewCard + i[1]) < dim_y) {
                cardAttached = board[xNewCard + i[0]][yNewCard + i[1]];
                if (cardAttached != null ) {
                    int cornerToCheck = (i[2] == 1 || i[2] == 2) ? i[2] + 2 : i[2] - 2;
                    c = cardAttached.getCorner(cornerToCheck);
                    if (c != null) {
                        j = c.getSeed().getId();
                        if (j < 7) {
                            seedUpdate[j] -= 1;
                        }
                    }
                }
            }
        }
        card = board[xNewCard][yNewCard];
        if(card.isFlipped()){
            j = card.getSeed().getId();
            seedUpdate[j] += 1;
        }
        return seedUpdate;
    }

    /**
     * Get board matrix playing card [ ] [ ].
     *
     * @return the playing card [ ] [ ]
     */
    public PlayingCard[][] getBoardMatrix() {
        return board;
    }

    /**
     * Gets dim x.
     *
     * @return the dim x
     */
    public int getDim_x() {
        return dim_x;
    }

    /**
     * Gets dim y.
     *
     * @return the dim y
     */
    public int getDim_y() {
        return dim_y;
    }

    /**
     * Get player player.
     *
     * @return the player
     */
    public Player getPlayer(){
        return player;
    }


    /**
     * To string string.
     *
     * @return the string
     */
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i=-1;i<dim_x+1;i++){
            for (int j=-1;j<dim_y+1;j++){
                if(i==-1){
                    if(j+1<dim_y && board[i+1][j+1]!=null  && (board[i+1][j+1].getCorner(1)!=null|| board[i+1][j-1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a("  C1 "));
                    }
                    else if(j-1>=0 && board[i+1][j-1]!=null  && (board[i+1][j-1].getCorner(2)!=null|| board[i+1][j-1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a(" C2  "));
                    }

                    else {
                        stringBuilder.append(ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLACK).a("     "));
                    }
                }
                else if(j==-1){
                    if(i+1<dim_x && board[i+1][j+1]!=null && (board[i+1][j+1].getCorner(1)!=null || board[i+1][j+1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a("  C1 "));
                    }
                    else if(i-1>=0 && board[i-1][j+1]!=null && (board[i-1][j+1].getCorner(4)!=null|| board[i-1][j+1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a("  C4 "));
                    }
                    else {
                        stringBuilder.append(ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLACK).a("     "));
                    }
                }
                else if(i==dim_x){
                    if(j+1<dim_y && board[i-1][j+1]!=null && (board[i-1][j+1].getCorner(4)!=null || board[i-1][j+1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a("  C4 "));
                    }
                    else if(j-1>=0 && board[i-1][j-1]!=null && (board[i-1][j-1].getCorner(3)!=null|| board[i-1][j-1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a(" C3  "));
                    }
                    else {
                        stringBuilder.append(ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLACK).a("     "));
                    }
                }
                else if(j==dim_y){
                    if(i-1>=0 && board[i-1][j-1]!=null && (board[i-1][j-1].getCorner(3)!=null|| board[i-1][j-1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a(" C3  "));
                    }
                    else if(i+1<dim_x && board[i+1][j-1]!=null && (board[i+1][j-1].getCorner(2)!=null|| board[i+1][j-1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a(" C2  "));
                    }
                    else {
                        stringBuilder.append(ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLACK).a("     "));
                    }
                }

                else if(board[i][j]==null){
                    if(i+1<board.length && j-1>0 && board[i+1][j-1]!=null && (board[i+1][j-1].getCorner(2)!=null|| board[i+1][j-1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a(" C2  "));
                    }
                    else if(i+1<board.length && j+1<board[i].length && board[i+1][j+1]!=null && (board[i+1][j+1].getCorner(1)!=null || board[i+1][j+1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a("  C1 "));
                    }
                    else if(i-1>0 && j+1<board[i].length && board[i-1][j+1]!=null && (board[i-1][j+1].getCorner(4)!=null|| board[i-1][j+1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a("  C4 "));
                    }
                    else if(i-1>0 && j-1>0 && board[i-1][j-1]!=null && (board[i-1][j-1].getCorner(3)!=null|| board[i-1][j-1].isFlipped())){
                        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a(" C3  "));
                    }
                    else {
                        stringBuilder.append(ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLACK).a("     "));
                    }

                }
                else{
                    Seed seed = board[i][j].getSeed();
                    Ansi.Color color;
                    if(seed==Seed.EMPTY){
                        color = Ansi.Color.WHITE;
                    }
                    else {
                        color = seed.getByAnsi();
                    }
                    int id = board[i][j].getIdCard();
                    String idString;
                    if(id<10){
                        idString = " "+id+" ";
                    } else if (id <100) {
                        idString = " "+id;
                    }
                    else {
                        idString = ""+id;
                    }

                    stringBuilder.append(
                            ansi().fg(Ansi.Color.DEFAULT).bg(color).a(" ").a(idString).a(" ")
                    );

                }
            }
            stringBuilder.append(ansi().fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a("\n"));
        }

        return stringBuilder.toString();
    }


}
