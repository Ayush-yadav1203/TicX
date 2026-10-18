package com.ticx.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameHistory {

    private static final String FILE_NAME =
            "ticx_history.txt";

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(
                    "dd-MM-yyyy HH:mm"
            );

    public void saveGame(
            String result,
            String mode,
            String difficulty) {

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     FILE_NAME,
                                     true
                             ))) {

            String dateTime =
                    LocalDateTime.now()
                            .format(FORMATTER);

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

        } catch (IOException e) {

            System.out.println(
                    "Could not save game history."
            );
        }
    }
}