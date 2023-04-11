package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class InfoController implements Initializable {

  /*
        if username is changed
        - change username in courtres database

    Phone Number for Main Desk: 215-436-2231
    Phone Number for Technical Support: 215-663-2133
    Email for any questions: askquestion@tennis.com
  */

  @FXML
  private Text disAddress;

  @FXML
  private Text disAge;

  @FXML
  private Text disEmail;

  @FXML
  private Text disGuests;

  @FXML
  private Text disOwed;

  @FXML
  private Text disPass;

  @FXML
  private Text disPhone;

  @FXML
  private Text disRes;

  @FXML
  private Text disUser;

  @FXML
  private Text disfname;

  @FXML
  private Text dislname;

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    /*
    Alert con = new Alert(Alert.AlertType.CONFIRMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);

    fNameField.setPromptText(Database.fName);
    lNameField.setPromptText(Database.lName);
    ageField.setPromptText(Database.age);
    addrField.setPromptText(Database.addr);
    phoneField.setPromptText(Database.phone);
    emailField.setPromptText(Database.email);
    userField.setPromptText(Database.memberUser);
    passField.setPromptText(Database.memberPass);
    verifiedField.setPromptText(Database.verified);

    late.setText(Database.isLate);
    owe.setText(Database.owe);
    guests.setText(Database.guests);
    */
  }

  /*
  public void changeShown(ActionEvent event) {
      Database.changeShown()
      App.setRoot("info");
  }
  */


  // cancel membership
  public void cancelMembership(ActionEvent event){

      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Cancel Membership");
      alert.setContentText("Do you want to cancel your membership");
      Optional<ButtonType> result = alert.showAndWait();

      if(result.isEmpty()){
          System.out.println("Closed");
      } else if (result.get() == ButtonType.OK){
        Database.deleteFromDir(Database.memberUser);
        Database.deleteFromRes(Database.memberUser);

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
