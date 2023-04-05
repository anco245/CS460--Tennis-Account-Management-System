package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CTController implements Initializable {

  @FXML
  private Text welcome;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    String welcomeMessage = "Welcome " + Database.person + "!";
    welcome.setText(welcomeMessage);
  }

  @FXML
  void addEvent(ActionEvent event) {
    //App.setRoot("addevent");
  }

  @FXML
  void approve(ActionEvent event) {
    //App.setRoot("approve");
  }

  @FXML
  void remove(ActionEvent event) throws IOException {
    //App.setRoot("remove");
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
  void viewInfo(ActionEvent event) throws IOException {
    //App.setRoot("directory");
  }

}
