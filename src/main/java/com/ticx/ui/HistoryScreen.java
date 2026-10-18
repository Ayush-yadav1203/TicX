package com.ticx.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class HistoryScreen {

    private static final String FILE_NAME =
            "ticx_history.txt";

    public void show(
            Stage stage,
            Runnable backAction) {

        Label title =
                new Label("Game History");

        title.setStyle(
                "-fx-font-size: 32px;" +
                "-fx-font-weight: bold;"
        );

        VBox historyBox =
                new VBox(10);

        historyBox.setAlignment(
                Pos.TOP_CENTER
        );

        loadHistory(historyBox);

        ScrollPane scrollPane =
                new ScrollPane(historyBox);

        scrollPane.setFitToWidth(true);

        scrollPane.setPrefHeight(450);

        Button backButton =
                new Button("Back to Game");

        backButton.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-padding: 10 30;"
        );

        backButton.setOnAction(
                event -> backAction.run()
        );

        VBox root =
                new VBox(
                        20,
                        title,
                        scrollPane,
                        backButton
                );

        root.setAlignment(
                Pos.CENTER
        );

        Scene scene =
                new Scene(
                        root,
                        650,
                        600
                );

        stage.setTitle(
                "TicX - Game History"
        );

        stage.setScene(scene);

        stage.show();
    }

    private void loadHistory(
            VBox historyBox) {

        Path path =
                Path.of(FILE_NAME);

        if (!Files.exists(path)) {

            Label emptyLabel =
                    new Label(
                            "No games played yet."
                    );

            emptyLabel.setStyle(
                    "-fx-font-size: 18px;"
            );

            historyBox.getChildren()
                    .add(emptyLabel);

            return;
        }

        try {

            List<String> history =
                    Files.readAllLines(path);

            if (history.isEmpty()) {

                Label emptyLabel =
                        new Label(
                                "No games played yet."
                        );

                historyBox.getChildren()
                        .add(emptyLabel);

                return;
            }

            for (String record : history) {

                Label gameLabel =
                        new Label(record);

                gameLabel.setStyle(
                        "-fx-font-size: 16px;" +
                        "-fx-padding: 10;"
                );

                gameLabel.setMaxWidth(
                        Double.MAX_VALUE
                );

                historyBox.getChildren()
                        .add(gameLabel);
            }

        } catch (IOException e) {

            Label errorLabel =
                    new Label(
                            "Could not load game history."
                    );

            historyBox.getChildren()
                    .add(errorLabel);
        }
    }
}