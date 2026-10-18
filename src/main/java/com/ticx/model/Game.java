package com.ticx.model;

public class Game {

    private final Board board;

    private Player currentPlayer;

    private Player winner;

    private boolean gameOver;

    public Game() {

        board = new Board();

        currentPlayer = Player.X;

        winner = null;

        gameOver = false;
    }

    public boolean makeMove(int row, int col) {

        if (gameOver) {
            return false;
        }

        boolean moveMade =
                board.makeMove(row, col, currentPlayer);

        if (!moveMade) {
            return false;
        }

        // Check winner
        if (board.hasWinner()) {

            winner = currentPlayer;

            gameOver = true;

            return true;
        }

        // Check draw
        if (board.isFull()) {

            gameOver = true;

            return true;
        }

        // Change player
        currentPlayer = currentPlayer.switchPlayer();

        return true;
    }

    public char getCell(int row, int col) {
        return board.getCell(row, col);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isDraw() {
        return gameOver && winner == null;
    }

    public void reset() {

        board.reset();

        currentPlayer = Player.X;

        winner = null;

        gameOver = false;
    }
}