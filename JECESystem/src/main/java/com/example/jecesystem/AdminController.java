package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class AdminController {

  @FXML
  void reset(ActionEvent event) {
      Database.reset();
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    App.setRoot("login");
  }

}
