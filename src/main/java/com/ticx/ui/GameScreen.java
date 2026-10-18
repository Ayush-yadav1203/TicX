package com.ticx.ui;

import com.ticx.ai.EasyAI;
import com.ticx.ai.MediumAI;
import com.ticx.ai.HardAI;
import com.ticx.model.Board;
import com.ticx.model.Game;
import com.ticx.model.Player;
import com.ticx.util.GameHistory;
import com.ticx.util.ProfileManager;
import com.ticx.util.ThemeManager;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameScreen {

    private final Button[][] buttons =
            new Button[3][3];

    private final Game game =
            new Game();

    private final EasyAI easyAI =
            new EasyAI();

    private final MediumAI mediumAI =
            new MediumAI();

    private final HardAI hardAI =
            new HardAI();

    private final GameHistory gameHistory =
            new GameHistory();

    private final ScoreBoard scoreBoard =
            new ScoreBoard();

    private final ProfileManager profileManager =
            new ProfileManager();

    private Label turnLabel;

    private VBox playerXCard;

    private VBox playerOCard;

    private final boolean vsComputer;

    private final String difficulty;

    public GameScreen(
            boolean vsComputer,
            String difficulty) {

        this.vsComputer = vsComputer;
        this.difficulty = difficulty;
    }

    public void show(Stage stage) {

        Label title =
                new Label("TicX");

        title.getStyleClass()
                .add("app-title");

        Label subtitle =
                new Label(
                        "Tic-Tac-Toe Reimagined"
                );

        subtitle.getStyleClass()
                .add("app-subtitle");

        turnLabel =
                new Label(
                        "Your Turn • X"
                );

        turnLabel.getStyleClass()
                .add("turn-label");

        HBox playerInfo =
                createPlayerInfo();

        GridPane board =
                createBoard();

        Button restartButton =
                new Button("↻  Restart Game");

        restartButton.getStyleClass()
                .add("primary-button");

        restartButton.setPrefWidth(180);

        restartButton.setOnAction(
                event -> restartGame()
        );

        Button historyButton =
                new Button("📜  Game History");

        historyButton.getStyleClass()
                .add("secondary-button");

        historyButton.setPrefWidth(180);

        historyButton.setOnAction(
                event -> openHistory(stage)
        );

        Button menuButton =
                new Button("⌂  Main Menu");

        menuButton.getStyleClass()
                .add("secondary-button");

        menuButton.setPrefWidth(180);

        menuButton.setOnAction(
                event -> openMainMenu(stage)
        );

        Button themeButton =
                new Button();

        themeButton.getStyleClass()
                .add("secondary-button");

        themeButton.setPrefWidth(180);

        updateThemeButton(themeButton);

        themeButton.setOnAction(
                event -> {

                    ThemeManager.toggleTheme(
                            stage.getScene()
                    );

                    updateThemeButton(
                            themeButton
                    );
                }
        );

        HBox gameActions =
                new HBox(
                        12,
                        restartButton,
                        historyButton
                );

        gameActions.setAlignment(
                Pos.CENTER
        );

        HBox navigation =
                new HBox(
                        12,
                        menuButton,
                        themeButton
                );

        navigation.setAlignment(
                Pos.CENTER
        );

        VBox root =
                new VBox(
                        12,
                        title,
                        subtitle,
                        turnLabel,
                        playerInfo,
                        scoreBoard.createScoreBoard(),
                        board,
                        gameActions,
                        navigation
                );

        root.setAlignment(
                Pos.CENTER
        );

        root.setPadding(
                new Insets(
                        25,
                        30,
                        25,
                        30
                )
        );

        Scene scene =
                new Scene(
                        root,
                        700,
                        900
                );

        stage.setTitle(
                "TicX - Game"
        );

        stage.setScene(scene);

        stage.show();

        ThemeManager.applyTheme(scene);

        updateActivePlayer();
    }

    private HBox createPlayerInfo() {

        String playerName =
                profileManager.getPlayerName();

        if (playerName == null
                || playerName.trim().isEmpty()) {

            playerName = "PLAYER 1";
        }

        playerXCard =
                createPlayerCard(
                        playerName.toUpperCase(),
                        "X",
                        false
                );

        if (vsComputer) {

            playerOCard =
                    createPlayerCard(
                            "COMPUTER",
                            "O",
                            true
                    );

        } else {

            playerOCard =
                    createPlayerCard(
                            "PLAYER 2",
                            "O",
                            false
                    );
        }

        HBox container =
                new HBox(
                        15,
                        playerXCard,
                        playerOCard
                );

        container.setAlignment(
                Pos.CENTER
        );

        return container;
    }

    private VBox createPlayerCard(
            String name,
            String symbol,
            boolean computer) {

        Label nameLabel =
                new Label(name);

        nameLabel.getStyleClass()
                .add("player-name");

        Label symbolLabel =
                new Label(symbol);

        symbolLabel.getStyleClass()
                .add("player-symbol");

        if (symbol.equals("X")) {

            symbolLabel.getStyleClass()
                    .add("x-symbol");

        } else {

            symbolLabel.getStyleClass()
                    .add("o-symbol");
        }

        VBox card =
                new VBox(
                        3,
                        nameLabel,
                        symbolLabel
                );

        if (computer) {

            Label difficultyLabel =
                    new Label(
                            difficulty + " AI"
                    );

            difficultyLabel.getStyleClass()
                    .add("difficulty-label");

            card.getChildren()
                    .add(difficultyLabel);
        }

        card.setAlignment(
                Pos.CENTER
        );

        card.getStyleClass()
                .add("player-card");

        return card;
    }

    private GridPane createBoard() {

        GridPane board =
                new GridPane();

        board.setHgap(9);

        board.setVgap(9);

        board.setAlignment(
                Pos.CENTER
        );

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                Button button =
                        new Button();

                button.setPrefSize(
                        125,
                        125
                );

                button.getStyleClass()
                        .add("board-cell");

                final int r = row;
                final int c = col;

                button.setOnAction(
                        event ->
                                playMove(r, c)
                );

                buttons[row][col] =
                        button;

                board.add(
                        button,
                        col,
                        row
                );
            }
        }

        return board;
    }

    private void playMove(
            int row,
            int col) {

        if (vsComputer
                && game.getCurrentPlayer()
                == Player.O) {

            return;
        }

        boolean moveMade =
                game.makeMove(
                        row,
                        col
                );

        if (!moveMade) {

            return;
        }

        updateBoard();

        animateMove(
                buttons[row][col]
        );

        if (game.isGameOver()) {

            showResult();

            return;
        }

        updateTurnLabel();

        updateActivePlayer();

        if (vsComputer
                && game.getCurrentPlayer()
                == Player.O) {

            computerMove();
        }
    }

    private void computerMove() {

        Board board =
                createBoardCopy();

        int[] move = null;

        if (difficulty.equals("Easy")) {

            move =
                    easyAI.chooseMove(
                            board,
                            Player.O
                    );

        } else if (
                difficulty.equals("Medium")) {

            move =
                    mediumAI.chooseMove(
                            board,
                            Player.O
                    );

        } else if (
                difficulty.equals("Hard")) {

            move =
                    hardAI.chooseMove(
                            board,
                            Player.O
                    );
        }

        if (move == null) {

            return;
        }

        game.makeMove(
                move[0],
                move[1]
        );

        updateBoard();

        animateMove(
                buttons[
                        move[0]
                ][
                        move[1]
                ]
        );

        if (game.isGameOver()) {

            showResult();

            return;
        }

        updateTurnLabel();

        updateActivePlayer();
    }

    private Board createBoardCopy() {

        Board copy =
                new Board();

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                char value =
                        game.getCell(
                                row,
                                col
                        );

                if (value == 'X') {

                    copy.makeMove(
                            row,
                            col,
                            Player.X
                    );

                } else if (value == 'O') {

                    copy.makeMove(
                            row,
                            col,
                            Player.O
                    );
                }
            }
        }

        return copy;
    }

    private void updateBoard() {

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                char value =
                        game.getCell(
                                row,
                                col
                        );

                Button button =
                        buttons[row][col];

                button.getStyleClass()
                        .removeAll(
                                "x-cell",
                                "o-cell"
                        );

                if (value != ' ') {

                    button.setText(
                            String.valueOf(value)
                    );

                    if (value == 'X') {

                        button.getStyleClass()
                                .add("x-cell");

                    } else {

                        button.getStyleClass()
                                .add("o-cell");
                    }
                }
            }
        }
    }

    private void animateMove(
            Button button) {

        ScaleTransition animation =
                new ScaleTransition(
                        Duration.millis(180),
                        button
                );

        animation.setFromX(0.5);

        animation.setFromY(0.5);

        animation.setToX(1.0);

        animation.setToY(1.0);

        animation.play();
    }

    private void updateActivePlayer() {

        playerXCard.getStyleClass()
                .remove("active-player");

        playerOCard.getStyleClass()
                .remove("active-player");

        if (game.getCurrentPlayer()
                == Player.X) {

            playerXCard.getStyleClass()
                    .add("active-player");

        } else {

            playerOCard.getStyleClass()
                    .add("active-player");
        }
    }

    private void updateTurnLabel() {

        if (game.getCurrentPlayer()
                == Player.X) {

            turnLabel.setText(
                    "Your Turn • X"
            );

        } else if (vsComputer) {

            turnLabel.setText(
                    "Computer's Turn • O"
            );

        } else {

            turnLabel.setText(
                    "Player 2's Turn • O"
            );
        }
    }

    private void showResult() {

        String result;

        String mode;

        if (vsComputer) {

            mode =
                    "Player vs Computer";

        } else {

            mode =
                    "Player vs Player";
        }

        if (game.getWinner() != null) {

            char winner =
                    game.getWinner()
                            .getSymbol();

            result =
                    "Player "
                            + winner
                            + " Won";

            if (winner == 'X') {

                turnLabel.setText(
                        "🏆 You Won!"
                );

                scoreBoard.addWin();

            } else {

                turnLabel.setText(
                        "😔 You Lost!"
                );

                scoreBoard.addLoss();
            }

            highlightWinningCells();

        } else {

            result =
                    "Draw";

            turnLabel.setText(
                    "🤝 It's a Draw!"
            );

            scoreBoard.addDraw();
        }

        gameHistory.saveGame(
                result,
                mode,
                vsComputer
                        ? difficulty
                        : "N/A"
        );

        disableBoard();

        playerXCard.getStyleClass()
                .remove("active-player");

        playerOCard.getStyleClass()
                .remove("active-player");
    }

    private void highlightWinningCells() {

        char winner =
                game.getWinner()
                        .getSymbol();

        int[][] lines = {

                {0, 0, 0, 1, 0, 2},

                {1, 0, 1, 1, 1, 2},

                {2, 0, 2, 1, 2, 2},

                {0, 0, 1, 0, 2, 0},

                {0, 1, 1, 1, 2, 1},

                {0, 2, 1, 2, 2, 2},

                {0, 0, 1, 1, 2, 2},

                {0, 2, 1, 1, 2, 0}
        };

        for (int[] line : lines) {

            if (game.getCell(
                    line[0],
                    line[1]
            ) == winner
                    && game.getCell(
                    line[2],
                    line[3]
            ) == winner
                    && game.getCell(
                    line[4],
                    line[5]
            ) == winner) {

                highlightCell(
                        line[0],
                        line[1]
                );

                highlightCell(
                        line[2],
                        line[3]
                );

                highlightCell(
                        line[4],
                        line[5]
                );

                break;
            }
        }
    }

    private void highlightCell(
            int row,
            int col) {

        Button button =
                buttons[row][col];

        button.getStyleClass()
                .add("winning-cell");

        ScaleTransition animation =
                new ScaleTransition(
                        Duration.millis(350),
                        button
                );

        animation.setFromX(1.0);

        animation.setFromY(1.0);

        animation.setToX(1.08);

        animation.setToY(1.08);

        animation.setAutoReverse(true);

        animation.setCycleCount(4);

        animation.play();
    }

    private void disableBoard() {

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                buttons[row][col]
                        .setDisable(true);
            }
        }
    }

    private void restartGame() {

        game.reset();

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                Button button =
                        buttons[row][col];

                button.setText("");

                button.setDisable(false);

                button.getStyleClass()
                        .removeAll(
                                "x-cell",
                                "o-cell",
                                "winning-cell"
                        );
            }
        }

        turnLabel.setText(
                "Your Turn • X"
        );

        updateActivePlayer();
    }

    private void openHistory(
            Stage stage) {

        HistoryScreen historyScreen =
                new HistoryScreen();

        historyScreen.show(
                stage,
                () -> show(stage)
        );
    }

    private void openMainMenu(
            Stage stage) {

        MainMenu mainMenu =
                new MainMenu();

        mainMenu.show(stage);
    }

    private void updateThemeButton(
            Button button) {

        if (ThemeManager.isDarkMode()) {

            button.setText(
                    "☀  Light Mode"
            );

        } else {

            button.setText(
                    "🌙  Dark Mode"
            );
        }
    }
}