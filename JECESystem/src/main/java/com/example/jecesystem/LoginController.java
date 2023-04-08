package com.example.jecesystem;

import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController {

  public static String name = "";
  public static String pass = "";

  @FXML
  private Button login;
  @FXML
  private Button signup;
  @FXML
  private TextField usernameBox;
  @FXML
  private TextField passwordBox;
  @FXML
  private Text text;

  @FXML
  void switchToSignUp(ActionEvent event) throws IOException {
      App.setRoot("signup");
  }

  @FXML
  void toContactUs(ActionEvent event) throws IOException {
      App.setRoot("contactus");
  }

  @FXML
  void toLogin(ActionEvent event) throws IOException {
      //Takes what's in the username and password textfields
      //and puts them into the name and pass variables.
      name = usernameBox.getText();
      pass = passwordBox.getText();

      Alert info = new Alert(Alert.AlertType.INFORMATION);
      Alert error = new Alert(Alert.AlertType.ERROR);

      //If the username and password match an account
      //in the database then check if that entry is verified
      if(Database.login(name, pass))
      {
         //if verified, show homescreen
          if(Database.verified(name))
          {
            if(Database.domain.equals("tennis.com")) {
              App.setRoot("ctscreen");
            } else if (Database.domain.equals("admin.com")) {
              App.setRoot("adminscreen");
            } else {
              App.setRoot("memscreen");
            }

         } else {
            info.setTitle("Need to be Verified");
            info.setContentText("Your account hasn't been verified yet.\n You have to " +
              "wait until the chairman approves your account before logging in.");
            info.showAndWait();
         }
      } else {
        error.setTitle("Cannot log in");
        error.setContentText("Wrong username or password.\n" +
          "Try again.");
        error.showAndWait();
      }
  }
}
