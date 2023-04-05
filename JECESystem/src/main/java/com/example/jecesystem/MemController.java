package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MemController implements Initializable {
  public static String welcomeMessage = "Welcome " + Database.person + "!";

  @FXML
  private Text lateMessage;

  @FXML
  private Text welcome;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    welcome.setText(welcomeMessage);

    if(Database.isLate) {
      String late = "You have overdue payments. Go to your Personal Information page for more info.";
      lateMessage.setText(late);
    }
  }

  @FXML
  void showDir(ActionEvent event) throws IOException {
    App.setRoot("directory");
  }

  @FXML
  void addGuest(ActionEvent event) {
    //App.setRoot("addguest");
  }

  @FXML
  void contactUs(ActionEvent event) throws IOException {
    App.setRoot("contactus");
  }

  @FXML
  void makeRes(ActionEvent event) {
    //App.setRoot("makeres");
  }

  @FXML
  void viewInfo(ActionEvent event) {
    //App.setRoot("info");

    /*
       - cancel reservation
       - cancel membership
       - view hours
     */
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
      App.setRoot("login");
  }
}


