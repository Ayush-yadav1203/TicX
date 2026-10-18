package com.ticx.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScoreManager {

    private static final Path DATA_FOLDER =
            Paths.get(
                    System.getProperty("user.home"),
                    "TicXData"
            );

    private static final Path SCORE_FILE =
            DATA_FOLDER.resolve(
                    "ticx_scores.txt"
            );

    private int playerXWins;
    private int playerOWins;
    private int draws;

    public ScoreManager() {

        loadScores();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public int getPlayerXWins() {

        return playerXWins;
    }

    public int getPlayerOWins() {

        return playerOWins;
    }

    public int getDraws() {

        return draws;
    }

    // ==========================================
    // ADD X WIN
    // ==========================================

    public void addXWin() {

        playerXWins++;

        saveScores();
    }

    // ==========================================
    // ADD O WIN
    // ==========================================

    public void addOWin() {

        playerOWins++;

        saveScores();
    }

    // ==========================================
    // ADD DRAW
    // ==========================================

    public void addDraw() {

        draws++;

        saveScores();
    }

    // ==========================================
    // SAVE SCORES
    // ==========================================

    private void saveScores() {

        try {

            Files.createDirectories(
                    DATA_FOLDER
            );

            String data =
                    "X=" + playerXWins + "\n"
                    + "O=" + playerOWins + "\n"
                    + "DRAWS=" + draws;

            Files.writeString(
                    SCORE_FILE,
                    data
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to save scores."
            );

            e.printStackTrace();
        }
    }

    // ==========================================
    // LOAD SCORES
    // ==========================================

    private void loadScores() {

        if (!Files.exists(SCORE_FILE)) {

            playerXWins = 0;
            playerOWins = 0;
            draws = 0;

            return;
        }

        try {

            String data =
                    Files.readString(
                            SCORE_FILE
                    );

            String[] lines =
                    data.split("\n");

            for (String line : lines) {

                line = line.trim();

                if (line.startsWith("X=")) {

                    playerXWins =
                            Integer.parseInt(
                                    line.substring(2)
                            );

                } else if (line.startsWith("O=")) {

                    playerOWins =
                            Integer.parseInt(
                                    line.substring(2)
                            );

                } else if (
                        line.startsWith("DRAWS=")) {

                    draws =
                            Integer.parseInt(
                                    line.substring(6)
                            );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to load saved scores."
            );

            playerXWins = 0;
            playerOWins = 0;
            draws = 0;
        }
    }
}