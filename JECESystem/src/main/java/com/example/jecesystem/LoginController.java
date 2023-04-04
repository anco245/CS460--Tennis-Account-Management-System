package com.example.jecesystem;

import javafx.event.*;
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
  void toLogin(ActionEvent event) throws IOException {
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
              App.setRoot("memscreen");

         } else {
            //text.setText("Your account hasn't been verified by the chairmen yet.");
         }
      } else {
          //text.setText("Wrong Username and Password");
      }
  }
}
