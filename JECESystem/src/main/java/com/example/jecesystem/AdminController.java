package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.util.Optional;

public class AdminController {

  @FXML
  void reset(ActionEvent event) {
    Alert con = new Alert(Alert.AlertType.CONFIRMATION);
    con.setContentText("Are you sure you want to reset the database?\n" +
      "Select ok to confirm.");

    //if ok is pressed,
    Optional<ButtonType> result = con.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      Database.reset();
    }
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    App.setRoot("login");
  }

}
