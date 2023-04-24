package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TreasurerController implements Initializable {
  @FXML
  private TextArea textInfo;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    textInfo.setText("Treasurer Homescreen:\n\nWelcome, " + Database.fName + " " + Database.lName + "!");
  }

  @FXML
  void addSub(ActionEvent event) throws IOException {
    App.setRoot("addsub");
  }

  @FXML
  void showTresDir(ActionEvent event) throws IOException {
    App.setRoot("treasurerdirectory");
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    App.setRoot("login");
  }

  @FXML
  void viewBankInfo(ActionEvent event) throws IOException {
    //App.setRoot("bankDisplay");
  }



}
