package com.example.jecesystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class SignUpController {

  public static String inputfName = "";
  public static String inputlName = "";
  public static String inputUser = "";
  public static String inputPass = "";
  public static String inputConPass = "";
  public static String inputAddr = "";
  public static String inputEmail = "";
  public static String inputPhone = "";
  public static int inputAge = 0;
  public static Boolean inputShow = false;

  public static int inputOwe = 1000;

  public static String inputCoupon = "";

  @FXML
  private Button cancel;
  @FXML
  private Button submit;
  @FXML
  private Text text;
  @FXML
  private TextField fieldAddress;
  @FXML
  private TextField fieldAge;
  @FXML
  private TextField fieldConPass;
  @FXML
  private TextField fieldCoupon;
  @FXML
  private TextField fieldEmail;
  @FXML
  private TextField fieldFName;
  @FXML
  private TextField fieldLName;
  @FXML
  private TextField fieldPass;
  @FXML
  private TextField fieldPhone;
  @FXML
  private TextField fieldUser;
  @FXML
  private CheckBox securitycheck;


  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    App.setRoot("login");
  }

  @FXML
  void toSubmit(ActionEvent event) throws IOException {

    Alert info = new Alert(Alert.AlertType.INFORMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);

    inputfName = fieldFName.getText();
    inputlName = fieldLName.getText();
    inputAge = Integer.parseInt(fieldAge.getText());
    inputAddr = fieldAddress.getText();
    inputPhone = fieldPhone.getText();
    inputEmail = fieldEmail.getText();
    inputEmail = inputEmail.substring(0, inputEmail.length() - 4);
    inputUser = fieldUser.getText();
    inputPass = fieldPass.getText();
    inputConPass = fieldConPass.getText();
    inputShow = securitycheck.isSelected();
    inputCoupon = fieldCoupon.getText();

    /*
      - need valid coupon code
      - need error for wrong coupon code
      - error for invalid email
      - error for pass
     */


    if(inputAge < 0 || inputAge > 200)
    {
      error.setTitle("Error");
      error.setContentText("Age must be between 0 and 200\n" +
        "Try again.");
      error.showAndWait();
    } else if (inputPhone.length() != 10) {
      error.setTitle("Error");
      error.setContentText("Phone number has to be 10 digits\n" +
        "Try again.");
      error.showAndWait();
    } else if (Database.inDatabase(inputUser)) {
      error.setTitle("Error");
      error.setContentText("Username already taken.\n" +
        "Try again.");
      error.showAndWait();
    } else if (inputPass.length() < 4) {
      error.setTitle("Error");
      error.setContentText("Password needs to be at least 4 characters.\n" +
        "Try again.");
      error.showAndWait();
    } else if (!inputConPass.equals(inputPass)) {
      error.setTitle("Error");
      error.setContentText("Passwords don't match.\n" +
        "Try again.");
      error.showAndWait();
    } else {
      Database.nAccount(inputfName, inputlName, inputAge, inputAddr, inputPhone,
        inputEmail, inputUser, inputPass, inputShow, inputOwe);

      info.setTitle("Success");
      info.setContentText("Your account has been created!\n" +
        "You won't be able to log in until the chairman approves" +
        "your account.");
      info.showAndWait();

      App.setRoot("login");
    }
  }
}
