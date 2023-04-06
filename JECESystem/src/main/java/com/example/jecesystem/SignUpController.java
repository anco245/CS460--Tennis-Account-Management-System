package com.example.jecesystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
  public static String inputAge = "";
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
    inputfName = fieldFName.getText();
    inputlName = fieldLName.getText();
    inputAge = fieldAge.getText();
    inputAddr = fieldAddress.getText();
    inputPhone = fieldPhone.getText();
    inputEmail = fieldEmail.getText();
    inputEmail = inputEmail.substring(0, inputEmail.length() - 4);
    inputUser = fieldUser.getText();
    inputPass = fieldPass.getText();
    inputConPass = fieldConPass.getText();
    inputShow = securitycheck.isSelected();
    inputCoupon = fieldCoupon.getText();

    if(inputCoupon.equals("abcd"))
    {
      inputOwe=-500;
    }

    if(inputPhone.length() == 10 && inputAge.length() > 0 && inputAge.length() < 4)
    {
      if(inputConPass.equals(inputPass))
      {
        Database.nAccount(inputfName, inputlName, inputAge, inputAddr, inputPhone,
          inputEmail, inputUser, inputPass, inputShow, inputOwe);

        System.out.println("Success");
      } else if(Database.inDatabase(inputUser)) {
        System.out.println("already taken");//text.setText("Username already taken. Enter a new one.");
      } else {
        System.out.println("Username and pass don't match");
        //text.setText("Username and password don't match");
      }
    } else {
      System.out.println("Phone is not 10");
      //text.setText("Phone number isn't 10 characters");
    }
  }
}
