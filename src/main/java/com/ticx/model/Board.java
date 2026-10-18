package com.ticx.model;

public class Board {

    private final char[][] cells = new char[3][3];

    public Board() {
        reset();
    }

    public boolean makeMove(int row, int col, Player player) {

        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }

        if (cells[row][col] != ' ') {
            return false;
        }

        cells[row][col] = player.getSymbol();

        return true;
    }

    public char getCell(int row, int col) {
        return cells[row][col];
    }

    public boolean hasWinner() {

        // Rows
        for (int row = 0; row < 3; row++) {

            if (cells[row][0] != ' '
                    && cells[row][0] == cells[row][1]
                    && cells[row][1] == cells[row][2]) {

                return true;
            }
        }

        // Columns
        for (int col = 0; col < 3; col++) {

            if (cells[0][col] != ' '
                    && cells[0][col] == cells[1][col]
                    && cells[1][col] == cells[2][col]) {

                return true;
            }
        }

        // Main diagonal
        if (cells[0][0] != ' '
                && cells[0][0] == cells[1][1]
                && cells[1][1] == cells[2][2]) {

            return true;
        }

        // Other diagonal
        if (cells[0][2] != ' '
                && cells[0][2] == cells[1][1]
                && cells[1][1] == cells[2][0]) {

            return true;
        }

        return false;
    }

    public boolean isFull() {

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                if (cells[row][col] == ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    public void reset() {

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                cells[row][col] = ' ';
            }
        }
    }
}