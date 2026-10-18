package com.ticx.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProfileManager {

    private static final Path DATA_FOLDER =
            Paths.get(
                    System.getProperty("user.home"),
                    "TicXData"
            );

    private static final Path PROFILE_FILE =
            DATA_FOLDER.resolve(
                    "ticx_profile.txt"
            );

    private String playerName;

    private int wins;

    private int losses;

    private int draws;

    private int gamesPlayed;

    public ProfileManager() {
        loadProfile();
    }

    // ==========================================
    // PLAYER NAME
    // ==========================================

    public String getPlayerName() {
        return playerName;
    }

    // ==========================================
    // CHECK PROFILE
    // ==========================================

    public boolean hasProfile() {

        return playerName != null
                && !playerName.trim().isEmpty();
    }

    // ==========================================
    // CREATE PROFILE
    // ==========================================

    public boolean createProfile(String name) {

        if (name == null) {
            return false;
        }

        name = name.trim();

        if (name.isEmpty()) {
            return false;
        }

        if (name.length() > 20) {
            return false;
        }

        playerName = name;

        saveProfile();

        return true;
    }

    // ==========================================
    // STATISTICS
    // ==========================================

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    // ==========================================
    // ADD WIN
    // ==========================================

    public void addWin() {

        wins++;

        gamesPlayed++;

        saveProfile();
    }

    // ==========================================
    // ADD LOSS
    // ==========================================

    public void addLoss() {

        losses++;

        gamesPlayed++;

        saveProfile();
    }

    // ==========================================
    // ADD DRAW
    // ==========================================

    public void addDraw() {

        draws++;

        gamesPlayed++;

        saveProfile();
    }

    // ==========================================
    // SAVE PROFILE
    // ==========================================

    private void saveProfile() {

        try {

            Files.createDirectories(
                    DATA_FOLDER
            );

            String data =
                    "NAME=" + playerName + "\n"
                    + "WINS=" + wins + "\n"
                    + "LOSSES=" + losses + "\n"
                    + "DRAWS=" + draws + "\n"
                    + "GAMES=" + gamesPlayed;

            Files.writeString(
                    PROFILE_FILE,
                    data
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to save profile."
            );

            e.printStackTrace();
        }
    }

    // ==========================================
    // LOAD PROFILE
    // ==========================================

    private void loadProfile() {

        if (!Files.exists(PROFILE_FILE)) {

            playerName = null;

            wins = 0;

            losses = 0;

            draws = 0;

            gamesPlayed = 0;

            return;
        }

        try {

            String data =
                    Files.readString(
                            PROFILE_FILE
                    );

            String[] lines =
                    data.split("\\R");

            for (String line : lines) {

                line = line.trim();

                if (line.startsWith("NAME=")) {

                    playerName =
                            line.substring(5);

                } else if (line.startsWith("WINS=")) {

                    wins =
                            Integer.parseInt(
                                    line.substring(5)
                            );

                } else if (line.startsWith("LOSSES=")) {

                    losses =
                            Integer.parseInt(
                                    line.substring(7)
                            );

                } else if (line.startsWith("DRAWS=")) {

                    draws =
                            Integer.parseInt(
                                    line.substring(6)
                            );

                } else if (line.startsWith("GAMES=")) {

                    gamesPlayed =
                            Integer.parseInt(
                                    line.substring(6)
                            );

                } else if (
                        playerName == null
                                && !line.isEmpty()) {

                    // Supports the old profile format
                    playerName = line;
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to load profile."
            );

            playerName = null;

            wins = 0;

            losses = 0;

            draws = 0;

            gamesPlayed = 0;
        }
    }

    // ==========================================
    // LOGOUT
    // ==========================================

    public void logout() {

        playerName = null;

        wins = 0;

        losses = 0;

        draws = 0;

        gamesPlayed = 0;

        try {

            Files.deleteIfExists(
                    PROFILE_FILE
            );

        } catch (IOException e) {

            System.out.println(
                    "Unable to remove profile."
            );
        }
    }
}