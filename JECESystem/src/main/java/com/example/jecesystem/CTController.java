package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CTController implements Initializable {

  @FXML
  private TextArea textInfo;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
      textInfo.setText("Chairman / Treasurer Homescreen:\n\nWelcome, " + Database.fName + " " + Database.lName + "!");
  }

  @FXML
  void addSub(ActionEvent event) throws IOException {
      App.setRoot("addsub");
  }

  @FXML
  void approve(ActionEvent event) throws IOException {
    App.setRoot("approve");
  }

  @FXML
  void remove(ActionEvent event) throws IOException {
    App.setRoot("remove");
  }

  @FXML
  void showTresDir(ActionEvent event) throws IOException {
    App.setRoot("chairdirectory");
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    App.setRoot("login");
  }

  @FXML
  void viewBankInfo(ActionEvent event) {

  }

}
