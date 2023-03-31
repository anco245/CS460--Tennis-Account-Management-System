package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

  public static String name = "";
  public static String pass = "";
  public static String inputfName = "";
  public static String inputlName = "";
  public static String inputUser = "";
  public static String inputPass = "";
  public static String inputAddr = "";
  public static String inputEmail = "";
  public static String inputPhone = "";
  public static String inputAge = "";
  public static Boolean inputShow = false;

  @FXML
  private Button login;

  @FXML
  private TextField passwordBox;

  @FXML
  private Button signup;

  @FXML
  private TextField usernameBox;

  @FXML
  private Text text;

  @FXML
  void funToLog(ActionEvent event) {

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
        //HomeScreen.display();
      } else {
        text.setText("Your account hasn't been verified by the chairmen yet.");


      }
    } else {
      text.setText("Wrong Username and Password");
    }
  }

  @FXML
  void switchToSignIn(ActionEvent event) {

  }

}
