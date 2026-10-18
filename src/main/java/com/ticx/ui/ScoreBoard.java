package com.ticx.ui;

import com.ticx.util.ProfileManager;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScoreBoard {

    private final ProfileManager profileManager =
            new ProfileManager();

    private Label winsLabel;

    private Label lossesLabel;

    private Label drawsLabel;

    private Label gamesLabel;

    public VBox createScoreBoard() {

        Label title =
                new Label("Your Statistics");

        title.getStyleClass()
                .add("score-title");

        winsLabel =
                new Label(
                        "Wins: "
                                + profileManager.getWins()
                );

        lossesLabel =
                new Label(
                        "Losses: "
                                + profileManager.getLosses()
                );

        drawsLabel =
                new Label(
                        "Draws: "
                                + profileManager.getDraws()
                );

        gamesLabel =
                new Label(
                        "Games: "
                                + profileManager.getGamesPlayed()
                );

        winsLabel.getStyleClass()
                .add("score-label");

        lossesLabel.getStyleClass()
                .add("score-label");

        drawsLabel.getStyleClass()
                .add("score-label");

        gamesLabel.getStyleClass()
                .add("score-label");

        HBox row1 =
                new HBox(
                        35,
                        winsLabel,
                        lossesLabel
                );

        row1.setAlignment(
                Pos.CENTER
        );

        HBox row2 =
                new HBox(
                        35,
                        drawsLabel,
                        gamesLabel
                );

        row2.setAlignment(
                Pos.CENTER
        );

        VBox scoreboard =
                new VBox(
                        8,
                        title,
                        row1,
                        row2
                );

        scoreboard.setAlignment(
                Pos.CENTER
        );

        scoreboard.getStyleClass()
                .add("card");

        return scoreboard;
    }

    // ==========================================
    // RECORD WIN
    // ==========================================

    public void addWin() {

        profileManager.addWin();

        updateLabels();
    }

    // ==========================================
    // RECORD LOSS
    // ==========================================

    public void addLoss() {

        profileManager.addLoss();

        updateLabels();
    }

    // ==========================================
    // RECORD DRAW
    // ==========================================

    public void addDraw() {

        profileManager.addDraw();

        updateLabels();
    }

    // ==========================================
    // UPDATE UI
    // ==========================================

    private void updateLabels() {

        if (winsLabel == null) {
            return;
        }

        winsLabel.setText(
                "Wins: "
                        + profileManager.getWins()
        );

        lossesLabel.setText(
                "Losses: "
                        + profileManager.getLosses()
        );

        drawsLabel.setText(
                "Draws: "
                        + profileManager.getDraws()
        );

        gamesLabel.setText(
                "Games: "
                        + profileManager.getGamesPlayed()
        );
    }
}