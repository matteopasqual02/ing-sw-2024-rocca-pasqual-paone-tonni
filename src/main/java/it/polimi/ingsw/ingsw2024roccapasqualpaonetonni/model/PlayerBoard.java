package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

public class PlayerBoard {
    private final int dim_x = 40;
    private final int dim_y = 40;
    private Card[][] board;

    public PlayerBoard() {
        board = new Card[dim_x][dim_y];
    }

    // method that receives a card and the coordinates where to put it,
    // it then checks if the coordinates are inside the matrix, and if they aren't calls a method to resize the matrix
    private void setBoard(int x, int y, Card card) {
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
            board = increase_board(x_increase, y_increase);
        }

        board[x][y] = card;
    }

    // method used to resize the matrix, by creating a new one and copying the old elements
    private Card[][] increase_board(int x_increase, int y_increase) {
        int row_offset = 0;
        int col_offset = 0;
        Card[][] tmp_board = board;
        board = new Card[dim_x + x_increase][dim_y + y_increase];

        if (x_increase < 0) {
            row_offset = -x_increase;
        }
        if (y_increase < 0) {
            col_offset = -y_increase;
        }

        for (int row = 0; row < dim_x; row++) {
            System.arraycopy(tmp_board[row], 0, board[row_offset + row], col_offset, dim_y);
        }

        return board;
    }

}
