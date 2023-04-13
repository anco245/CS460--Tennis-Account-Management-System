package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.io.IOException;

public class SignUpController {

  public static String inputfName = "";
  public static String inputlName = "";
  public static int inputAge = 0;
  public static String inputUser = "";
  public static String inputPass = "";
  public static String inputConPass = "";
  public static String inputAddr = "";
  public static String inputEmail = "";
  public static String inputPhone = "";
  public static Boolean inputShow = false;
  public static int inputOwe = 1000;
  public static String inputCoupon = "";

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

    try {
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
      - error for invalid email
      - error for pass
      */

      if (Database.getSize() == 1000) {
        if (!fieldCoupon.getText().trim().isEmpty() && !inputCoupon.equals("abcd")) {
          error.setTitle("Error");
          error.setContentText("Not a valid coupon code.\n" +
            "Try again.");
          error.showAndWait();
        } else if (inputCoupon.equals("abcd")) {
          inputOwe = 500;
          Database.insertIntoWait(inputfName, inputlName, inputAge, inputAddr, inputPhone,
            inputEmail, inputUser, inputPass, inputShow, inputOwe);

          info.setTitle("Maximum Occupancy");
          info.setContentText("Unfortunately, we have reached the maximum amount of " +
            "members for our club.\nYou will be added to a wait list and notified by email " +
            "when a position becomes available");
          info.showAndWait();
        } else {
          inputOwe =  1000;

          Database.insertIntoWait(inputfName, inputlName, inputAge, inputAddr, inputPhone,
            inputEmail, inputUser, inputPass, inputShow, inputOwe);

          info.setTitle("Maximum Occupancy");
          info.setContentText("Unfortunately, we have reached the maximum amount of" +
            " members for our club.\nYou will be added to a wait list and notified by email" +
            " when a position becomes available");
          info.showAndWait();
        }

        App.setRoot("login");

      } else {
        if (inputAge < 0 || inputAge > 200) {
          error.setTitle("Error");
          error.setContentText("Age must be between 0 and 200\n" +
            "Try again.");
          error.showAndWait();
        } else if (inputPhone.length() != 10) {
          error.setTitle("Error");
          error.setContentText("Phone number has to be 10 digits\n" +
            "Try again.");
          error.showAndWait();
        } else if (Database.inDirectory(inputUser)) {
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
        } else if (!fieldCoupon.getText().trim().isEmpty() && !inputCoupon.equals("abcd")) {
          error.setTitle("Error");
          error.setContentText("Not a valid coupon code.\n" +
            "Try again.");
          error.showAndWait();
        } else {
          Database.nAccount(inputfName, inputlName, inputAge, inputAddr, inputPhone,
            inputEmail, inputUser, inputPass, inputShow, inputOwe);

          info.setTitle("Success");
          info.setContentText("Your account has been created!\n" +
            "You won't be able to log in until the chairman approves " +
            "your account.");
          info.showAndWait();

          App.setRoot("login");
        }
      }
    } catch(Exception e){
    error.setTitle("Error");
    error.setContentText("Either one or more of the textfields are left blank,\n" +
      "or something wasn't put in the right format.\n" +
      "Check your inputs and try again.");
    error.showAndWait();
    }
  }
}
