package com.ticx;

import com.ticx.ui.LoginScreen;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        LoginScreen loginScreen =
                new LoginScreen();

        loginScreen.show(stage);
    }

    public static void main(String[] args) {

        launch();
    }
}