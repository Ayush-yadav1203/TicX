package com.ticx.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class ProfileManager {

    private static final Path DATA_FOLDER =
            Paths.get(System.getProperty("user.home"), "TicXData");

    private static final Path PROFILES_FOLDER =
            DATA_FOLDER.resolve("profiles");

    private static final Path CURRENT_PROFILE_FILE =
            DATA_FOLDER.resolve("ticx_current_profile.txt");

    private static final Path LEGACY_PROFILE_FILE =
            DATA_FOLDER.resolve("ticx_profile.txt");

    private String playerName;
    private int wins;
    private int losses;
    private int draws;
    private int gamesPlayed;

    public ProfileManager() {
        loadCurrentProfile();
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean hasProfile() {
        return playerName != null && !playerName.trim().isEmpty();
    }

    public boolean createProfile(String name) {
        if (name == null) return false;

        name = name.trim();
        if (name.isEmpty() || name.length() > 20) return false;

        playerName = name;

        if (!loadProfileFile(profilePath(playerName))) {
            resetStats();
        }

        saveProfile();
        saveCurrentProfile();
        return true;
    }

    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
    public int getGamesPlayed() { return gamesPlayed; }

    public void addWin() {
        wins++;
        gamesPlayed++;
        saveProfile();
    }

    public void addLoss() {
        losses++;
        gamesPlayed++;
        saveProfile();
    }

    public void addDraw() {
        draws++;
        gamesPlayed++;
        saveProfile();
    }

    public static String getPlayerId(String name) {
        String normalized = name == null
                ? "guest"
                : name.trim().toLowerCase(Locale.ROOT);

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(normalized.getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();

            for (byte value : hash) {
                result.append(String.format("%02x", value));
            }

            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(normalized.hashCode());
        }
    }

    private Path profilePath(String name) {
        return PROFILES_FOLDER.resolve(getPlayerId(name) + ".txt");
    }

    private void saveProfile() {
        if (!hasProfile()) return;

        try {
            Files.createDirectories(PROFILES_FOLDER);

            String data =
                    "NAME=" + playerName + "\n"
                    + "WINS=" + wins + "\n"
                    + "LOSSES=" + losses + "\n"
                    + "DRAWS=" + draws + "\n"
                    + "GAMES=" + gamesPlayed;

            Files.writeString(
                    profilePath(playerName),
                    data,
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            System.out.println("Unable to save profile.");
            e.printStackTrace();
        }
    }

    private void saveCurrentProfile() {
        if (!hasProfile()) return;

        try {
            Files.createDirectories(DATA_FOLDER);
            Files.writeString(
                    CURRENT_PROFILE_FILE,
                    playerName,
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            System.out.println("Unable to save current profile.");
        }
    }

    private void loadCurrentProfile() {
        resetStats();
        playerName = null;

        if (Files.exists(CURRENT_PROFILE_FILE)) {
            try {
                String currentName = Files.readString(
                        CURRENT_PROFILE_FILE,
                        StandardCharsets.UTF_8
                ).trim();

                if (!currentName.isEmpty()) {
                    playerName = currentName;
                    if (!loadProfileFile(profilePath(playerName))) {
                        resetStats();
                    }
                    return;
                }
            } catch (IOException e) {
                System.out.println("Unable to load current profile.");
            }
        }

        // One-time migration from the old single-profile format.
        if (Files.exists(LEGACY_PROFILE_FILE)
                && loadProfileFile(LEGACY_PROFILE_FILE)
                && hasProfile()) {
            saveProfile();
            saveCurrentProfile();

            try {
                Files.deleteIfExists(LEGACY_PROFILE_FILE);
            } catch (IOException e) {
                System.out.println("Unable to remove old profile file.");
            }

            return;
        }

        resetStats();
        playerName = null;
    }

    private boolean loadProfileFile(Path file) {
        if (!Files.exists(file)) return false;

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);

            String loadedName = null;
            int loadedWins = 0;
            int loadedLosses = 0;
            int loadedDraws = 0;
            int loadedGames = 0;

            for (String line : data.split("\\R")) {
                line = line.trim();

                if (line.startsWith("NAME=")) {
                    loadedName = line.substring(5).trim();
                } else if (line.startsWith("WINS=")) {
                    loadedWins = Integer.parseInt(line.substring(5).trim());
                } else if (line.startsWith("LOSSES=")) {
                    loadedLosses = Integer.parseInt(line.substring(7).trim());
                } else if (line.startsWith("DRAWS=")) {
                    loadedDraws = Integer.parseInt(line.substring(6).trim());
                } else if (line.startsWith("GAMES=")) {
                    loadedGames = Integer.parseInt(line.substring(6).trim());
                } else if (loadedName == null && !line.isEmpty()) {
                    loadedName = line;
                }
            }

            if (loadedName == null || loadedName.isEmpty()) return false;

            playerName = loadedName;
            wins = Math.max(0, loadedWins);
            losses = Math.max(0, loadedLosses);
            draws = Math.max(0, loadedDraws);
            gamesPlayed = Math.max(0, loadedGames);
            return true;

        } catch (Exception e) {
            System.out.println("Unable to load profile.");
            return false;
        }
    }

    private void resetStats() {
        wins = 0;
        losses = 0;
        draws = 0;
        gamesPlayed = 0;
    }

    public void logout() {
        // Keep this player's data. Only clear the active session.
        playerName = null;
        resetStats();

        try {
            Files.deleteIfExists(CURRENT_PROFILE_FILE);
        } catch (IOException e) {
            System.out.println("Unable to remove current profile session.");
        }
    }
}
