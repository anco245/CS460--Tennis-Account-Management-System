package com.example.jecesystem;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Scene;

public class LoginController {

  @FXML
  private void switchToSignIn() throws IOException {
    App.setRoot("signin");
  }

  public static String name = "";
  public static String pass = "";

  @FXML
  private Button login;

  @FXML
  private TextField passwordBox;

  @FXML
  private Button signup;

  @FXML
  private Text text;

  @FXML
  private TextField usernameBox;

  @FXML
  void switchToSignIn(ActionEvent event) {

    //Takes what's in the username and password textfields
    //and puts them into the name and pass variables.
    name = usernameBox.getText();
    pass = passwordBox.getText();

    //If the username and password match an account
    //in the database then check if that entry is verified
    if(Database.login(name, pass))
    {
      //if verified, show homescreen
      if(Database.verified(name))
      {
        //loginWindow.close();

        //HomeScreen.display();
      } else {
        text.setText("Your account hasn't been verified by the chairmen yet.");
      }
    } else {
      text.setText("Wrong Username and Password");
    }
  }
}
