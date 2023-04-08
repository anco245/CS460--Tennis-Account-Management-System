package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.time.LocalDateTime;

public class MemController implements Initializable {

  @FXML
  private Text keep;

  @FXML
  private Text lateMessage;

  @FXML
  private Text welcome;

  @FXML
  private Text oweMessage;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    String welcomeMessage = "Welcome " + Database.fName + " " + Database.lName + "!";
    welcome.setText(welcomeMessage);

    if(Database.isLate) {
      String late = "You have overdue payments.";
      lateMessage.setText(late);
    }

    LocalDateTime now = LocalDateTime.now();
    if (now.getMonthValue() == 10 && now.getDayOfMonth() == 1 && now.getHour() >= 0 && now.getMinute() >= 0) {
      String keepMessage = "Do you want to continue your membership?\n" +
        "Go to View Personal Information, and click\nContinue Membership";
      keep.setText(keepMessage);
    }

    if(Database.owe > 0)
    {
      String oweError = "You owe $" + Database.owe;
      oweMessage.setText(oweError);
    }
  }

  @FXML
  void showDir(ActionEvent event) throws IOException {
    App.setRoot("directory");
  }

  @FXML
  void addGuest(ActionEvent event) throws IOException {
    App.setRoot("addguest");
  }

  @FXML
  void contactUs(ActionEvent event) throws IOException {
    App.setRoot("contactus");
  }

  @FXML
  void makeRes(ActionEvent event) throws IOException {
    App.setRoot("makeres");
  }

  @FXML
  void viewInfo(ActionEvent event) throws IOException {
    App.setRoot("info");
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
      App.setRoot("login");
  }
}


