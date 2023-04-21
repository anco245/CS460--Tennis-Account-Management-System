package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ContactUsMem {

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    App.setRoot("memscreen");
  }
}
