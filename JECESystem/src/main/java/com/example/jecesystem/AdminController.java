package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;

public class AdminController implements Initializable {

  @FXML
  private TextArea textInfo;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    if(Database.getVerified() == 0)
    {
      textInfo.setText("Administrator Homescreen:\nWelcome, " + Database.fName + " " + Database.lName + "!\n");
    } else {
      textInfo.setText("Administrator Homescreen:\nWelcome, " + Database.fName + " " + Database.lName + "!\n\n" +
        "You have " + Database.getVerified() + " account(s) waiting\nfor approval.\n");
    }
  }

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
  void showDirectory(ActionEvent event) throws IOException {
    App.setRoot("admindirectory");
  }

  @FXML
  void approve(ActionEvent event) throws IOException {
      App.setRoot("approve");
  }

  @FXML
  void remove(ActionEvent event) throws IOException {
      App.setRoot("remove");
  }

  @FXML
  void showWaiting(ActionEvent event) throws IOException {
    App.setRoot("waiting");
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    App.setRoot("login");
  }
}
