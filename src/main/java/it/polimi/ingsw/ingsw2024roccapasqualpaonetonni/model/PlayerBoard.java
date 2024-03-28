package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;

public class PlayerBoard {
    private int dim_x;
    private int dim_y;
    private PlayingCard[][] board;

    private Player player;

    public PlayerBoard(Player owner) {
        dim_x = 40;
        dim_y = 40;
        board = new PlayingCard[dim_x][dim_y];
        player = owner;
    }

    public Player getPlayer(){
        return player;
    }

    // method that receives a card and the coordinates where to put it,
    // it then checks if the coordinates are inside the matrix, and if they aren't calls a method to resize the matrix
    private void addCardToBoard(int[] coordinates, PlayingCard card, int[] seedCount) {
        int x = coordinates[0];
        int y = coordinates[1];
        int x_increase = 0;
        int y_increase = 0;

        if (x >= dim_x) {
            x_increase = x - dim_x;
        }
        else if (x < 0) {
            x_increase = x;
        }
        if (y >= dim_y) {
            y_increase = y - dim_y;
        }
        else if (y < 0) {
            y_increase = y;
        }
        if (x_increase != 0 || y_increase != 0) {
            board = increaseBoard(x_increase, y_increase);
        }

        board[x][y] = card;
        card.setCoordinates(x,y);
        player.updateSeedCount(calculateSeedUpdate(x, y));
        if(!card.isflipped()) {
            player.increasePoints(card.calculatePoints(board, seedCount, x, y));
        }
    }

    // method used to resize the matrix, by creating a new one and copying the old elements
    private PlayingCard[][] increaseBoard(int x_increase, int y_increase) {
        int row_offset = 0;
        int col_offset = 0;
        PlayingCard[][] tmp_board = board;
        board = new PlayingCard[dim_x + x_increase][dim_y + y_increase];

        if (x_increase < 0) {
            row_offset = -x_increase;
        }
        if (y_increase < 0) {
            col_offset = -y_increase;
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

        return board;
    }

    // public method used to add a new card to the board
    // the position where to add the card is given by indicating the card and the corner where to attach it
    public void addCard(GoldCard card_to_add, PlayingCard card_on_board, int corner, int[] seedCount) throws InvalidPlaceException, ConditionsNotMetException{
        int[] prev_coord = card_on_board.getCoordinates();
        int[] place_coord = new int[2];
        if (corner == 1) {
            place_coord[0] = prev_coord[0] - 1;
            place_coord[1] = prev_coord[1] - 1;
        }
        else if (corner == 2) {
            place_coord[0] = prev_coord[0] - 1;
            place_coord[1] = prev_coord[1] + 1;
        }
        else if (corner == 3) {
            place_coord[0] = prev_coord[0] + 1;
            place_coord[1] = prev_coord[1] - 1;
        }
        else if (corner == 4) {
            place_coord[0] = prev_coord[0] + 1;
            place_coord[1] = prev_coord[1] + 1;
        }

        if (checkSpotAvailable (card_to_add, place_coord)) {
            int tmp = checkRequirements(card_to_add)[0];
            if (tmp == 1) {
                addCardToBoard(place_coord, card_to_add, seedCount);
            }
            else {
                throw new ConditionsNotMetException("Not enough seed type" + Seed.getById(tmp).getName());
            }
        }
        else {
            throw new InvalidPlaceException("The card cannot be placed in the chosen position");
        }
    }
    public void addCard(ResourceCard card_to_add, PlayingCard card_on_board, int corner, int[] seedCount) throws InvalidPlaceException{
        int[] prev_coord = card_on_board.getCoordinates();
        int[] place_coord = new int[2];
        if (corner == 1) {
            place_coord[0] = prev_coord[0] - 1;
            place_coord[1] = prev_coord[1] - 1;
        }
        else if (corner == 2) {
            place_coord[0] = prev_coord[0] - 1;
            place_coord[1] = prev_coord[1] + 1;
        }
        else if (corner == 3) {
            place_coord[0] = prev_coord[0] + 1;
            place_coord[1] = prev_coord[1] - 1;
        }
        else if (corner == 4) {
            place_coord[0] = prev_coord[0] + 1;
            place_coord[1] = prev_coord[1] + 1;
        }

        if (checkSpotAvailable(card_to_add, place_coord)) {
            addCardToBoard(place_coord, card_to_add, seedCount);
        }
        else {
            throw new InvalidPlaceException("The card cannot be placed in the chosen position");
        }

        // if add is successful, player must update corners and seed count
    }

    public void addStartingCard(StartingCard firstCard){
        int x = 20;
        int y = 20;
        board[20][20] = firstCard;
        firstCard.setCoordinates(x,y);
        player.updateSeedCount(calculateCenterUpdate(x,y, firstCard));
    }

    private int[] calculateCenterUpdate(int x, int y, StartingCard c) {
        int[] seedUpdate = {0, 0, 0, 0, 0, 0, 0};
        for(int i=0; i<4; i++){
            if(c.isflipped()){
                seedUpdate[i]=1;
            }
            else {
                if(c.getCenter()[i]){
                    seedUpdate[i]=1;
                }
            }
        }
        return seedUpdate;
    }

    // checks the four spots around the position where we want to place the card
    // if there is a card, it checks if the corners are compatible
    private boolean checkSpotAvailable(PlayingCard card, int[] coordinates) {
        PlayingCard cardOnBoard;
        int x = coordinates[0];
        int y = coordinates[1];
        int[][] postions = {{-1, -1, 1}, {-1, 1, 2}, {1, 1, 3}, {1, -1, 4}};
        if (board[x][y] != null) {
            return false;
        }
        for (int[] i : postions) {
            cardOnBoard = board[x + i[0]][y + i[1]];
            //if (cardOnBoard != null && cardOnBoard.getCorner((i[2] + 2) % 4) == null) {
            if (cardOnBoard != null && cardOnBoard.getCorner(i[2]) == null) {
                return false;
            }
        }
        return true;
    }
    private int[] checkRequirements(GoldCard card) {
        int[] result = new int[2];
        int[] requirements = card.getPlaceCondition();
        for (int i = 0; i < requirements.length; i++) {
            if (requirements[i] > player.getCountSeed()[i]) {
                result[0] = 0;
                result[1] = i + 1;
                return result;
            }
        }
        result[0] = 1;
        return result;
    }

    private int[] calculateSeedUpdate(int xNewCard, int yNewCard) {
        PlayingCard card;
        int[] seedUpdate = {0, 0, 0, 0, 0, 0, 0};
        int[][] postions = {{-1, -1, 1}, {-1, 1, 2}, {1, 1, 3}, {1, -1, 4}};
        Corner c;
        int j;
        for (int[] i : postions) {
            card = board[xNewCard][yNewCard];
            if(!card.isflipped()){
                c = card.getCorner(i[2]);
                if (c != null) {
                    j = c.getSeed().getId();
                    if (j < 7) {
                        seedUpdate[j] += 1;
                    }
                }
            }
            card = board[xNewCard + i[0]][yNewCard + i[1]];
            if (card != null) {
                //c = card.getCorner((i[2] + 2) % 4);
                c = card.getCorner(i[2]);
                if (c != null) {
                    j = c.getSeed().getId();
                    if (j < 7) {
                        seedUpdate[j] -= 1;
                    }
                }
            }
        }
        return seedUpdate;
    }

    public PlayingCard[][] getBoard() {
        return board;
    }
}
