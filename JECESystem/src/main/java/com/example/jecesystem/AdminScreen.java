package com.example.jecesystem;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminScreen extends HomeScreen{
  public static void display() {
    Stage window = new Stage();
    VBox layout = new VBox(10);
    Scene scene = new Scene(layout, 400, 400);

    window.setTitle("Administrator Home Screen");

    Button checkUpdates = new Button("Check for Updates");

    layout.getChildren().addAll(text, checkUpdates, backToLogin);

    //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    window.setScene(scene);
    window.showAndWait();
  }

}
