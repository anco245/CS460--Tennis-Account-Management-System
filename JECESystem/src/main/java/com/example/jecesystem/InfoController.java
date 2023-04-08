package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.util.Optional;

public class InfoController {

  /*
    displays and can change:
        - first name
        - last name
        - age
        - address
        - email
        - phone
        - username
        - password

        if username is changed
        - change username in courtres database

    cancel membership
    view hours
    contact us

    Phone Number for Main Desk: 215-436-2231
    Phone Number for Technical Support: 215-663-2133
    Email for any questions: askquestion@tennis.com

    if(Database.owe > 0)
      {
        String oweError = "You owe $" + Database.owe;
        oweMessage.setText(oweError);
      }

   */


  // cancel membership
  public void cancelMembership(ActionEvent event){

      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Cancel Membership");
      alert.setContentText(" Do you want to cancel your membership");
      Optional<ButtonType> result = alert.showAndWait();

      if(result.isEmpty()){
          System.out.println("Closed");
      } else if (result.get() == ButtonType.OK){
          System.out.println(" You have canceled your membership");
      } else if (result.get()== ButtonType.CANCEL){
          System.out.println("No");
      }
  }

  @FXML
  void switchToHomescreen(ActionEvent event) throws IOException {
      App.setRoot("memscreen");
  }
}
