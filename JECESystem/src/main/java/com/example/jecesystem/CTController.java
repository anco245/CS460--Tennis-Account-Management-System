package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class CTController {

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
  void viewInfo(ActionEvent event) throws IOException {
    App.setRoot("info");
  }
}
