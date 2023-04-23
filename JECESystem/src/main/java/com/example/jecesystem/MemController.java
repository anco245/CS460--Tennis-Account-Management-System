package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.LocalDateTime;

public class MemController implements Initializable {

  @FXML
  private Text keep;

  @FXML
  private Text lateMessage;

  @FXML
  private Text welcome;

  /*
    Initializes all variables needed when using the homescreen.
    For example, if there's a message that needs to be displayed,
    the corresponding variable is given that text.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {

    Alert con = new Alert(Alert.AlertType.CONFIRMATION);
    LocalDateTime now = LocalDateTime.now();

    String welcomeMessage = "Member Homescreen:\nWelcome " + Database.fName + " " + Database.lName + "!";
    welcome.setText(welcomeMessage);

    if(now.getDayOfMonth() == 1)
    {
      if(Database.isLate)
      {
        Database.addSubOwe(Database.memberUser, Database.monthly + 50);
      }

      Database.setGuests(0);
    }

    //Keep confirm just allows the "do you want to keep your account" message to appear
    //once. Otherwise, it'll come up every time they log in on this day
    if (now.getMonthValue() == 10 && now.getDayOfMonth() == 1 && !Database.keepConfirm) {

      con.setTitle("Keep Account");
      con.setContentText("Do you want to keep your Account?\n" +
        "Press ok to confirm.");

      Optional<ButtonType> result = con.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK){
        Database.setKeep(Database.memberUser, true);
        Database.keep = true;

        Database.setConfirm(true);
        Database.keepConfirm = true;

        try {
          App.setRoot("memscreen");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } else {
        Database.setKeep(Database.memberUser, false);
        Database.keep = false;

        Database.setConfirm(true);
        Database.keepConfirm = true;

        try {
          App.setRoot("memscreen");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }

    if (now.getMonthValue() == 1 || now.getMonthValue() == 2) {
      String keepMessage = "Your annual payment is due by March 1st";
      keep.setText(keepMessage);
    }

    if(Database.isLate) {
      String late = """
        You haven't paid your monthly
        fee. If you don't pay by April
        1st, your account will be
        removed.""";
      lateMessage.setText(late);
    }
  }

  @FXML
  void addPayment(ActionEvent event) throws IOException {
    App.setRoot("addpay");
  }

  @FXML
  void showDir(ActionEvent event) throws IOException {
    App.setRoot("directory");
  }

  @FXML
  void contactUs(ActionEvent event) throws IOException {
    App.setRoot("contactus");
  }

  @FXML
  void makeRes(ActionEvent event) throws IOException {
    App.setRoot("court1");
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


