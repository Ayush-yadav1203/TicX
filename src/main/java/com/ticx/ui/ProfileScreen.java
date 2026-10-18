package com.ticx.ui;

import com.ticx.util.ProfileManager;
import com.ticx.util.ThemeManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileScreen {

    private final ProfileManager profileManager =
            new ProfileManager();

    public void show(Stage stage) {

        String playerName =
                profileManager.getPlayerName();

        if (playerName == null
                || playerName.trim().isEmpty()) {

            playerName = "Player";
        }

        String initial =
                playerName.substring(0, 1).toUpperCase();

        Label title =
                new Label("TicX");

        title.getStyleClass()
                .add("app-title");

        Label subtitle =
                new Label(
                        "Your Player Profile"
                );

        subtitle.getStyleClass()
                .add("app-subtitle");

        Label avatar =
                new Label(initial);

        avatar.setPrefSize(
                100,
                100
        );

        avatar.setAlignment(
                Pos.CENTER
        );

        avatar.setStyle(
                "-fx-background-color: #4f46e5;" +
                "-fx-background-radius: 50%;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 42px;" +
                "-fx-font-weight: bold;"
        );

        Label nameLabel =
                new Label(playerName);

        nameLabel.getStyleClass()
                .add("app-title");

        Label welcomeLabel =
                new Label(
                        "Welcome back, "
                                + playerName
                                + "!"
                );

        welcomeLabel.getStyleClass()
                .add("menu-section-title");

        Label infoLabel =
                new Label(
                        "Your profile is ready. " +
                        "Let's start playing!"
                );

        infoLabel.getStyleClass()
                .add("menu-footer");

        Button playButton =
                new Button(
                        "🎮  Start Playing"
                );

        playButton.getStyleClass()
                .add("primary-button");

        playButton.setPrefWidth(350);

        playButton.setPrefHeight(50);

        playButton.setOnAction(
                event -> openMainMenu(stage)
        );

        Button logoutButton =
                new Button(
                        "↩  Logout"
                );

        logoutButton.getStyleClass()
                .add("secondary-button");

        logoutButton.setPrefWidth(350);

        logoutButton.setPrefHeight(45);

        logoutButton.setOnAction(
                event -> logout(stage)
        );

        Button themeButton =
                new Button();

        themeButton.getStyleClass()
                .add("theme-button");

        updateThemeButton(
                themeButton
        );

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

        VBox root =
                new VBox(
                        18,
                        title,
                        subtitle,
                        avatar,
                        nameLabel,
                        welcomeLabel,
                        infoLabel,
                        playButton,
                        logoutButton,
                        themeButton
                );

        root.setAlignment(
                Pos.CENTER
        );

        root.setPadding(
                new Insets(40)
        );

        Scene scene =
                new Scene(
                        root,
                        650,
                        800
                );

        stage.setTitle(
                "TicX - Profile"
        );

        stage.setScene(scene);

        stage.show();

        ThemeManager.applyTheme(
                scene
        );
    }

    private void openMainMenu(
            Stage stage) {

        MainMenu mainMenu =
                new MainMenu();

        mainMenu.show(stage);
    }

    private void logout(
            Stage stage) {

        profileManager.logout();

        LoginScreen loginScreen =
                new LoginScreen();

        loginScreen.show(stage);
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