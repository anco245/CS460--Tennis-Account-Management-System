package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPayController implements Initializable {

  Alert info = new Alert(Alert.AlertType.INFORMATION);
  Alert error = new Alert(Alert.AlertType.ERROR);
  Alert con = new Alert(Alert.AlertType.CONFIRMATION);

  @FXML
  private TextField amount;

  @FXML
  private TextArea bankInfo;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    String str = "Amount owed: $" + Database.owe + "\n\n";

    if(!Database.hasBankAccount(Database.memberUser))
    {
      bankInfo.setText(str + "You haven't added a bank yet");
    } else {
      str = str + Database.getBankInfo(Database.memberUser);
      bankInfo.setText(str);
    }
  }

  @FXML
  void onSubmit(ActionEvent event) {
    try {

      int amt = Integer.parseInt(amount.getText());

      if(!Database.hasBankAccount(Database.memberUser))
      {
        error.setTitle("Error");
        error.setContentText("You haven't added a bank account yet");
        error.showAndWait();
      } else if (amount.getText() == null) {
        error.setTitle("Error");
        error.setContentText("You didn't enter an amount.\nTry Again");
        error.showAndWait();
      } else if (!amount.getText().matches(".*\\d+.*")) {
        error.setTitle("Error");
        error.setContentText("You didn't enter a valid number");
        error.showAndWait();
      } else if (Database.owe == 0 || amt > Database.owe) {
        error.setTitle("Error");
        error.setContentText("Either there's nothing to deduct or you're trying to\n" +
          "deduct more than what's owed.\nTry again.");
        error.showAndWait();
      } else {
        Database.addSubOwe(Database.memberUser, amt * (-1));

        if(Database.owe < Database.annual)
        {
          Database.setLate(Database.memberUser, false);
          Database.isLate = false;
        }

        info.setTitle("Success");
        info.setContentText("You've deducted $" + amt);
        info.showAndWait();

        App.setRoot("addpay");
      }
    } catch (Exception e) {
      error.setTitle("Error");
      error.setContentText("An error occurred. Check your inputs and try again.");
    }
  }

  @FXML
  void removeBank(ActionEvent event) throws IOException {
      con.setTitle("Confirmation");
      con.setContentText("Are you sure you want to delete the bank you have?\n" +
        "Press ok to confirm.");

      Optional<ButtonType> result = con.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
        Database.deleteBank(Database.memberUser);
        App.setRoot("addpay");
      }
  }

  @FXML
  void switchToAddBank(ActionEvent event) throws IOException {

    if(Database.hasBankAccount(Database.memberUser))
    {
      error.setTitle("Error");
      error.setContentText("You already have a bank account linked.\n" +
        "If you would like to add a new one, click remove first.");
      error.showAndWait();
    } else {
      App.setRoot("addbank");
    }
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
      App.setRoot("memscreen");
  }
}
