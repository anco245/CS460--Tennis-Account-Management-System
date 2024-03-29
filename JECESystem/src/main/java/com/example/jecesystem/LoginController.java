package com.example.jecesystem;

import javafx.event.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

  public static String name = "";
  public static String pass = "";

  @FXML
  private TextField usernameBox;
  @FXML
  private TextField passwordBox;

  @FXML
  void switchToSignUp(ActionEvent event) throws IOException {
      App.setRoot("signup");
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    LocalDateTime now = LocalDateTime.now();
    if(now.getMonthValue() == 1 && now.getDayOfMonth() == 1)
    {
      Database.removeNonKeeps();
    }
  }

  @FXML
  void toContactUs(ActionEvent event) throws IOException {
      App.setRoot("contactus");
  }

  @FXML
  void toLogin(ActionEvent event) throws IOException {

    Alert info = new Alert(Alert.AlertType.INFORMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);

    name = usernameBox.getText();
    pass = passwordBox.getText();

    //If the username and password match an account
    //in the database then check if that entry is verified
    if(Database.login(name, pass))
    {
      //if verified, show homescreen
      if(Database.verified(name))
      {
        if(Database.extension.equals("tennis.com")) {
          App.setRoot("treasurer");
        } else if (Database.extension.equals("admin.com")) {
          App.setRoot("adminscreen");
        } else {

          //If they haven't paid their annual payment by april 1,
          //their account is removed.
          LocalDateTime now = LocalDateTime.now();
          if(now.getMonthValue() == 4 && now.getDayOfMonth() == 1) {
            if (Database.isLate) {
                Database.deleteFromDb(name, "directory");
            }
          } else {
            App.setRoot("memscreen");
          }
        }
      } else {
        info.setTitle("Need to be Verified");
        info.setContentText("Your account hasn't been verified yet.\nYou have to " +
          "wait until the chairman approves your account before logging in.");
        info.showAndWait();
      }
    } else {
      error.setTitle("Error");
      error.setContentText("""
        Either the username and password is wrong, or
        one or both of the textfields are empty.
        Try again.""");
      error.showAndWait();
    }
  }
}
