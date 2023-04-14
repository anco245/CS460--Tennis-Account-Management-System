package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactUsController{

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    App.setRoot("login");
  }
}
