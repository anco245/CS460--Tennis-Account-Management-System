package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

public class RemoveController {

  @FXML
  private TextField field;

  @FXML
  void onSubmit(ActionEvent event) throws IOException {
      String name = field.getText();

      Alert con = new Alert(Alert.AlertType.CONFIRMATION);
      Alert error = new Alert(Alert.AlertType.ERROR);

      if(!Database.inDirectory(name))
      {
        error.setTitle("Not Found");
        error.setContentText("This user isn't in our database.\n" +
          "Try again.");
        error.showAndWait();
      } else {
        con.setTitle("Confirmation");
        con.setContentText("To confirm the removal of " + name + ", press ok");

        Optional<ButtonType> result = con.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

          Database.deleteFromDir(name);

          if(Database.inReservation(name))
          {
            Database.deleteFromRes(name);
          }

          App.setRoot("ctscreen");
        }
      }
  }

  @FXML
  void switchToHomeScreen(ActionEvent event) throws IOException {
    App.setRoot("ctscreen");
  }

}