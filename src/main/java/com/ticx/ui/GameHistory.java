package com.ticx.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameHistory {

    private static final Path DATA_FOLDER =
            Path.of(
                    System.getProperty("user.home"),
                    "TicXData",
                    "profiles"
            );

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public void saveGame(
            String playerName,
            String result,
            String mode,
            String difficulty) {

        if (playerName == null || playerName.trim().isEmpty()) {
            return;
        }

        Path file = historyPath(playerName);

        try {
            Files.createDirectories(DATA_FOLDER);

            try (BufferedWriter writer =
                         new BufferedWriter(
                                 new FileWriter(file.toFile(), true))) {

                String dateTime =
                        LocalDateTime.now().format(FORMATTER);

                writer.write(
                        dateTime
                                + " | "
                                + mode
                                + " | "
                                + difficulty
                                + " | "
                                + result
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Could not save game history.");
        }
    }

    public static Path historyPath(String playerName) {
        return DATA_FOLDER.resolve(
                ProfileManager.getPlayerId(playerName) + "_history.txt"
        );
    }
}
