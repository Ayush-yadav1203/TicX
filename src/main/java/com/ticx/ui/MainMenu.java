package com.ticx.ui;

import com.ticx.util.ThemeManager;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {

    public void show(Stage stage) {

        // =========================
        // TITLE
        // =========================

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


        // =========================
        // SECTION TITLE
        // =========================

        Label chooseLabel =
                new Label(
                        "Choose Game Mode"
                );

        chooseLabel.getStyleClass()
                .add("menu-section-title");


        // =========================
        // GAME MODE CARDS
        // =========================

        Button playerCard =
                createGameCard(
                        "👥",
                        "Player vs Player",
                        "Play against a friend",
                        "2 Players"
                );

        Button easyCard =
                createGameCard(
                        "🤖",
                        "Computer - Easy",
                        "A relaxed game for beginners",
                        "Easy AI"
                );

        Button mediumCard =
                createGameCard(
                        "⚡",
                        "Computer - Medium",
                        "A balanced challenge",
                        "Medium AI"
                );

        Button hardCard =
                createGameCard(
                        "🧠",
                        "Computer - Hard",
                        "Challenge the unbeatable AI",
                        "Minimax AI"
                );


        // =========================
        // GAME ACTIONS
        // =========================

        playerCard.setOnAction(event -> {

            GameScreen gameScreen =
                    new GameScreen(
                            false,
                            ""
                    );

            gameScreen.show(stage);
        });


        easyCard.setOnAction(event -> {

            GameScreen gameScreen =
                    new GameScreen(
                            true,
                            "Easy"
                    );

            gameScreen.show(stage);
        });


        mediumCard.setOnAction(event -> {

            GameScreen gameScreen =
                    new GameScreen(
                            true,
                            "Medium"
                    );

            gameScreen.show(stage);
        });


        hardCard.setOnAction(event -> {

            GameScreen gameScreen =
                    new GameScreen(
                            true,
                            "Hard"
                    );

            gameScreen.show(stage);
        });


        // =========================
        // THEME BUTTON
        // =========================

        Button themeButton =
                new Button();

        themeButton.getStyleClass()
                .add("theme-button");

        updateThemeButton(themeButton);


        themeButton.setOnAction(event -> {

            ThemeManager.toggleTheme(
                    stage.getScene()
            );

            updateThemeButton(
                    themeButton
            );
        });


        // =========================
        // FOOTER
        // =========================

        Label footer =
                new Label(
                        "Choose your challenge and start playing"
                );

        footer.getStyleClass()
                .add("menu-footer");


        // =========================
        // MAIN LAYOUT
        // =========================

        VBox root =
                new VBox(
                        15,
                        title,
                        subtitle,
                        chooseLabel,
                        playerCard,
                        easyCard,
                        mediumCard,
                        hardCard,
                        themeButton,
                        footer
                );

        root.setAlignment(
                Pos.CENTER
        );

        root.setPadding(
                new javafx.geometry.Insets(
                        30,
                        30,
                        25,
                        30
                )
        );


        // =========================
        // SCENE
        // =========================

        Scene scene =
                new Scene(
                        root,
                        650,
                        850
                );


        stage.setTitle(
                "TicX"
        );

        stage.setScene(
                scene
        );

        stage.show();


        // Apply CSS
        ThemeManager.applyTheme(
                scene
        );
    }


    // =================================================
    // CREATE GAME CARD
    // =================================================

    private Button createGameCard(
            String icon,
            String title,
            String description,
            String tag) {

        Label iconLabel =
                new Label(icon);

        iconLabel.getStyleClass()
                .add("game-card-icon");


        Label titleLabel =
                new Label(title);

        titleLabel.getStyleClass()
                .add("game-card-title");


        Label descriptionLabel =
                new Label(description);

        descriptionLabel.getStyleClass()
                .add("game-card-description");


        Label tagLabel =
                new Label(tag);

        tagLabel.getStyleClass()
                .add("game-card-tag");


        VBox content =
                new VBox(
                        4,
                        iconLabel,
                        titleLabel,
                        descriptionLabel,
                        tagLabel
                );

        content.setAlignment(
                Pos.CENTER_LEFT
        );


        Button card =
                new Button();

        card.setGraphic(
                content
        );

        card.setMaxWidth(
                Double.MAX_VALUE
        );

        card.setPrefHeight(
                105
        );

        card.setMnemonicParsing(
                false
        );

        card.getStyleClass()
                .add("game-card");


        return card;
    }


    // =================================================
    // THEME BUTTON
    // =================================================

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