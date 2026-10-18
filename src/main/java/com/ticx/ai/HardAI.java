package com.ticx.ai;

import com.ticx.model.Board;
import com.ticx.model.Player;

public class HardAI {

    private char aiSymbol;
    private char humanSymbol;

    public int[] chooseMove(
            Board board,
            Player aiPlayer) {

        aiSymbol = aiPlayer.getSymbol();
        humanSymbol =
                aiPlayer.switchPlayer().getSymbol();

        char[][] state = createCopy(board);

        int bestScore = Integer.MIN_VALUE;

        int[] bestMove = null;

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                if (state[row][col] != ' ') {
                    continue;
                }

                state[row][col] = aiSymbol;

                int score = minimax(
                        state,
                        false
                );

                state[row][col] = ' ';

                if (score > bestScore) {

                    bestScore = score;

                    bestMove = new int[]{
                            row,
                            col
                    };
                }
            }
        }

        return bestMove;
    }

    private int minimax(
            char[][] board,
            boolean maximizing) {

        if (hasWinner(board, aiSymbol)) {
            return 10;
        }

        if (hasWinner(board, humanSymbol)) {
            return -10;
        }

        if (isFull(board)) {
            return 0;
        }

        if (maximizing) {

            int bestScore = Integer.MIN_VALUE;

            for (int row = 0; row < 3; row++) {

                for (int col = 0; col < 3; col++) {

                    if (board[row][col] != ' ') {
                        continue;
                    }

                    board[row][col] = aiSymbol;

                    int score = minimax(
                            board,
                            false
                    );

                    board[row][col] = ' ';

                    bestScore =
                            Math.max(bestScore, score);
                }
            }

            return bestScore;

        } else {

            int bestScore = Integer.MAX_VALUE;

            for (int row = 0; row < 3; row++) {

                for (int col = 0; col < 3; col++) {

                    if (board[row][col] != ' ') {
                        continue;
                    }

                    board[row][col] = humanSymbol;

                    int score = minimax(
                            board,
                            true
                    );

                    board[row][col] = ' ';

                    bestScore =
                            Math.min(bestScore, score);
                }
            }

            return bestScore;
        }
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

    private boolean isFull(char[][] board) {

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }

        return true;
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
}