package com.ticx.util;

import javafx.scene.Scene;

public class ThemeManager {

    private static boolean darkMode = false;

    private static final String CSS_FILE =
            "/css/ticx.css";

    public static void applyTheme(Scene scene) {

        if (!scene.getStylesheets().contains(
                getCssUrl())) {

            scene.getStylesheets().add(
                    getCssUrl()
            );
        }

        updateTheme(scene);
    }

    public static void toggleTheme(Scene scene) {

        darkMode = !darkMode;

        updateTheme(scene);
    }

    private static void updateTheme(Scene scene) {

        if (darkMode) {

            if (!scene.getRoot()
                    .getStyleClass()
                    .contains("dark-theme")) {

                scene.getRoot()
                        .getStyleClass()
                        .add("dark-theme");
            }

        } else {

            scene.getRoot()
                    .getStyleClass()
                    .remove("dark-theme");
        }
    }

    private static String getCssUrl() {

        return ThemeManager.class
                .getResource(CSS_FILE)
                .toExternalForm();
    }

    public static boolean isDarkMode() {

        return darkMode;
    }
}