package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

public class PlayerBoard {
    private final int dim_x = 40;
    private final int dim_y = 40;
    private PlayingCard[][] board;

    public PlayerBoard() {
        board = new PlayingCard[dim_x][dim_y];
    }

    // method that receives a card and the coordinates where to put it,
    // it then checks if the coordinates are inside the matrix, and if they aren't calls a method to resize the matrix
    private void addCardToBoard(int[] coordinates, PlayingCard card) {
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
    public int addCard(PlayingCard card_to_add, PlayingCard card_on_board, int corner, int[] seedCount) {
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

        // #############################################################################################
        // #############################################################################################
        // #############################################################################################
        // need to use exceptions
        // #############################################################################################
        // #############################################################################################
        // #############################################################################################
        if (checkSpotAviable(card_to_add, place_coord) && card_to_add.checkPlaceCondition(seedCount)) {
            addCardToBoard(place_coord, card_to_add);
            return 1;
        }
        else {
            return 0;
        }
    }

    public int addCard(PlayingCard card_to_add, PlayingCard card_on_board, int corner) {
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

        if (checkSpotAviable(card_to_add, place_coord)) {
            addCardToBoard(place_coord, card_to_add);
            return 1;
        }
        else {
            return 0;
        }
    }

    // checks the four spots around the position where we want to place the card
    // if there is a card, it checks if the corners are compatible
    private boolean checkSpotAviable(PlayingCard card, int[] coordinates) {
        boolean result = false;
        int x = coordinates[0];
        int y = coordinates[1];
        int[][] postions = {{-1, -1, 1}, {-1, 1, 2}, {1, 1, 3}, {1, -1, 4}};
        for (int[] i : postions) {
            if (board[x + i[0]][y + i[1]] != null) {

            }
        }

        return result;
    }

}
