package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MemController implements Initializable {
  public static String message = "Welcome " + Database.person + "!";

  @FXML
  private Text lateMessage;

  @FXML
  private Text welcome;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    String message = "Welcome " + Database.person + "!";
    welcome.setText(message);
  }

  @FXML
  void showDir(ActionEvent event) throws IOException {
    App.setRoot("directory");
  }



}


