package com.ticx.ai;

import com.ticx.model.Board;
import com.ticx.model.Player;

public class MediumAI {

    public int[] chooseMove(Board board, Player aiPlayer) {

        Player opponent = aiPlayer.switchPlayer();

        // 1. Try to win
        int[] winningMove = findWinningMove(
                board,
                aiPlayer
        );

        if (winningMove != null) {
            return winningMove;
        }

        // 2. Block opponent
        int[] blockingMove = findWinningMove(
                board,
                opponent
        );

        if (blockingMove != null) {
            return blockingMove;
        }

        // 3. Take center
        if (board.getCell(1, 1) == ' ') {
            return new int[]{1, 1};
        }

        // 4. Take a corner
        int[][] corners = {
                {0, 0},
                {0, 2},
                {2, 0},
                {2, 2}
        };

        for (int[] corner : corners) {

            if (board.getCell(
                    corner[0],
                    corner[1]
            ) == ' ') {

                return corner;
            }
        }

        // 5. Take any available cell
        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                if (board.getCell(row, col) == ' ') {
                    return new int[]{row, col};
                }
            }
        }

        return null;
    }

    private int[] findWinningMove(
            Board board,
            Player player) {

        char symbol = player.getSymbol();

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                if (board.getCell(row, col) != ' ') {
                    continue;
                }

                char[][] testBoard = createCopy(board);

                testBoard[row][col] = symbol;

                if (hasWinner(testBoard, symbol)) {
                    return new int[]{row, col};
                }
            }
        }

        return null;
    }

    private char[][] createCopy(Board board) {

        char[][] copy = new char[3][3];

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                copy[row][col] =
                        board.getCell(row, col);
            }
        }

        return copy;
    }

    private boolean hasWinner(
            char[][] board,
            char symbol) {

        for (int row = 0; row < 3; row++) {

            if (board[row][0] == symbol
                    && board[row][1] == symbol
                    && board[row][2] == symbol) {

                return true;
            }
        }

        for (int col = 0; col < 3; col++) {

            if (board[0][col] == symbol
                    && board[1][col] == symbol
                    && board[2][col] == symbol) {

                return true;
            }
        }

        if (board[0][0] == symbol
                && board[1][1] == symbol
                && board[2][2] == symbol) {

            return true;
        }

        return board[0][2] == symbol
                && board[1][1] == symbol
                && board[2][0] == symbol;
    }
}