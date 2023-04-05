package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class ContactUsController {

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    if(Database.domain.equals("tennis.com"))
    {
      App.setRoot("ctscreen");
    } else if (Database.domain.equals("admin.com")){
      //App.setRoot("adminscreen");
    } else {
      App.setRoot("memscreen");
    }
  }

}
