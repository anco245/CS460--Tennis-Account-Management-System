package com.example.jecesystem;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminScreen extends HomeScreen{
  public static void display() {

    homeWindow.setTitle("Administrator Home Screen");

    Button checkUpdates = new Button("Check for Updates");

    homeLayout.getChildren().addAll(welcome, checkUpdates, backToLogin);

    //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    homeWindow.setScene(homeScene);
    homeWindow.showAndWait();
  }

}
