package com.ticx.ui;

import com.ticx.util.ProfileManager;
import com.ticx.util.ThemeManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen {

    private final ProfileManager profileManager =
            new ProfileManager();

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

        Label welcome =
                new Label(
                        "Welcome to TicX"
                );

        welcome.getStyleClass()
                .add("menu-section-title");

        Label instruction =
                new Label(
                        "Enter your player name to continue"
                );

        instruction.getStyleClass()
                .add("menu-footer");

        TextField nameField =
                new TextField();

        nameField.setPromptText(
                "Enter your name"
        );

        nameField.setMaxWidth(350);

        nameField.getStyleClass()
                .add("profile-input");

        if (profileManager.hasProfile()) {

            nameField.setText(
                    profileManager.getPlayerName()
            );
        }

        Label errorLabel =
                new Label();

        errorLabel.getStyleClass()
                .add("error-label");

        Button continueButton =
                new Button(
                        "Continue  →"
                );

        continueButton
                .getStyleClass()
                .add("primary-button");

        continueButton.setPrefWidth(350);

        continueButton.setPrefHeight(50);

        continueButton.setOnAction(
                event -> {

                    String name =
                            nameField
                                    .getText()
                                    .trim();

                    if (name.isEmpty()) {

                        errorLabel.setText(
                                "Please enter your name."
                        );

                        return;
                    }

                    if (name.length() > 20) {

                        errorLabel.setText(
                                "Name must be 20 characters or less."
                        );

                        return;
                    }

                    boolean saved =
                            profileManager
                                    .createProfile(name);

                    if (!saved) {

                        errorLabel.setText(
                                "Unable to save profile."
                        );

                        return;
                    }

                    openMainMenu(stage);
                }
        );

        nameField.setOnAction(
                event ->
                        continueButton.fire()
        );

        Button themeButton =
                new Button();

        themeButton
                .getStyleClass()
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
                        15,
                        title,
                        subtitle,
                        welcome,
                        instruction,
                        nameField,
                        errorLabel,
                        continueButton,
                        themeButton
                );

        root.setAlignment(
                Pos.CENTER
        );

        root.setPadding(
                new Insets(
                        40
                )
        );

        Scene scene =
                new Scene(
                        root,
                        650,
                        750
                );

        stage.setTitle(
                "TicX - Welcome"
        );

        stage.setScene(
                scene
        );

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