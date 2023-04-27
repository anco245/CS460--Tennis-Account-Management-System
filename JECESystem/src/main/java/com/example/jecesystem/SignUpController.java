package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.*;

public class SignUpController implements Initializable {

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
  public static String inputZipcode = "";
  public static String inputCity = "";

  @FXML
  private ChoiceBox<String> state;
  @FXML
  private TextField fieldAddress;
  @FXML
  private TextField fieldAge;
  @FXML
  private TextField fieldConPass;
  @FXML
  private TextField fieldCoupon;
  @FXML
  private TextField fieldCity;
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
  private TextField fieldZipcode;
  @FXML
  private CheckBox securitycheck;

  ObservableList usStates = FXCollections.observableArrayList();

  public void initialize(URL url, ResourceBundle rb) {
    loadData();
  }

  private void loadData() {

    usStates.addAll("AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL",
      "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV",
      "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
      "UT", "VT", "VA", "WA", "WV", "WI", "WY");
    state.getItems().addAll(usStates);
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    App.setRoot("login");
  }

  @FXML
  void toSubmit(ActionEvent event) {

    Alert info = new Alert(Alert.AlertType.INFORMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);

    Pattern emailPattern = Pattern.compile("^[\\w\\d]+@[a-zA-Z]+\\.com$");

    try {
      inputfName = fieldFName.getText();
      inputlName = fieldLName.getText();
      inputAge = Integer.parseInt(fieldAge.getText());
      inputAddr = fieldAddress.getText();
      inputPhone = fieldPhone.getText();
      inputEmail = fieldEmail.getText();
      inputUser = fieldUser.getText();
      inputPass = fieldPass.getText();
      inputConPass = fieldConPass.getText();
      inputShow = securitycheck.isSelected();
      inputCoupon = fieldCoupon.getText();
      inputZipcode = fieldZipcode.getText();
      inputCity = fieldCity.getText();

      Matcher matcher = emailPattern.matcher(inputEmail);

      if (Database.getSize() == 1000) {
        if (!fieldCoupon.getText().trim().isEmpty() && !inputCoupon.equals("abcd")) {
          error.setTitle("Error");
          error.setContentText("Not a valid coupon code.\n" +
            "Try again.");
          error.showAndWait();
        } else if (inputCoupon.equals("abcd")) {
          inputOwe = 500;

          inputAddr = inputAddr + ",\n" + inputCity + ", " + state.getValue() + ",\n" + inputZipcode;

          Database.insertIntoWait(inputfName, inputlName, inputAge, inputAddr, inputPhone,
            inputEmail, inputUser, inputPass, inputShow, inputOwe);

          info.setTitle("Maximum Occupancy");
          info.setContentText("Unfortunately, we have reached the maximum amount of " +
            "members for our club.\nYou will be added to a wait list and notified by email " +
            "when a position becomes available");
          info.showAndWait();
        } else {
          inputOwe =  1000;

          inputAddr = inputAddr + ",\n" + inputCity + "," + state.getValue() + ",\n" + inputZipcode;

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
        } else if (fieldCity.getText() == null) {
          error.setTitle("Error");
          error.setContentText("City / Town was left blank." +
            "Try again.");
          error.showAndWait();
        } else if (state.getValue() == null) {
          error.setTitle("Error");
          error.setContentText("State text field was left blank" +
            "Try again.");
          error.showAndWait();
        } else if (!inputZipcode.matches("[0-9]{5}")) {
          error.setTitle("Error");
          error.setContentText("Zipcode needs to be five numbers\n" +
            "Try again.");
          error.showAndWait();
        } else if (!matcher.matches()) {
          error.setTitle("Error");
          error.setContentText("Your email address isn't in the right format.\n" +
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

          inputAddr = inputAddr + ",\n" + inputCity + ", " + state.getValue() + ",\n" + inputZipcode;

          Database.nAccount(inputfName, inputlName, inputAge, inputAddr, inputPhone,
            inputEmail, inputUser, inputPass, inputShow, inputOwe);

          info.setTitle("Success");
          info.setContentText("Your account has been created!\n" +
            "You won't be able to log in until your " +
            "account is  approved.");
          info.showAndWait();

          App.setRoot("login");
        }
      }
    } catch(Exception e){
    error.setTitle("Error");
    error.setContentText("""
      Either one or more of the textfields are left blank,
      or something wasn't put in the right format.
      Check your inputs and try again.""");
    error.showAndWait();
    }
  }
}
