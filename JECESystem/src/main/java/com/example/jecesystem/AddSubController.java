package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

public class AddSubController {

  @FXML
  private TextField amtField;

  @FXML
  private TextField userField;

  @FXML
  void onSubmit(ActionEvent event) {
      if(amtField == null || userField == null)
      {
        Database.error.setTitle("Error");
        Database.error.setContentText("One or both of the textfields are blank.");
        Database.error.showAndWait();
      } else {
        try {
          String user = userField.getText();
          int amt = Integer.parseInt(amtField.getText());

          Database.con.setTitle("Confirmation");
          Database.con.setContentText("You will be applying $" + amt +  " to " + user + "'s account.\n Press ok to confirm.");

          Optional<ButtonType> result = con.showAndWait();
          if (result.isPresent() && result.get() == ButtonType.OK) {
            Database.addSubOwe(user, amt);

            App.setRoot("addsub");
          }
        } catch (Exception e) {
          error.setTitle("Error");
          error.setContentText("You may not have entered something in the right format.\nTry again.");
          error.showAndWait();
        }
      }
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
      App.setRoot("ctscreen");
  }
}
