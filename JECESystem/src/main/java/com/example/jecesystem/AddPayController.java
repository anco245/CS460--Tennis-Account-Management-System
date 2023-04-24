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

    String totalAmountOwedStatement = "Amount owed: $" + (Database.owe + Database.annual)  + "\n\n";

    if(!Database.hasBankAccount(Database.memberUser))
    {
      bankInfo.setText(totalAmountOwedStatement + "You haven't added a bank yet");
    } else {
      totalAmountOwedStatement = totalAmountOwedStatement + Database.getBankInfo(Database.memberUser);
      bankInfo.setText(totalAmountOwedStatement);
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
      } else if (amt > Database.bankBalance) {
        error.setTitle("Error");
        error.setContentText("You don't have enough money in your bank account");
        error.showAndWait();
      } else {

        //If member still hasn't paid off all of annual fee or 1000 initial payment
        if(Database.annual > 0)
        {
          //If member wants to deduct the amount that they owe, set late to false
          if(amt >= Database.annual)
          {
            Database.setLate(Database.memberUser, false);
            Database.isLate = false;
          }

          Database.addSubAnnual(Database.memberUser, amt * (-1));
        } else {

          //else if annual is already paid off, deduct from owe (guest reservations)
          Database.addSubOwe(Database.memberUser, amt * (-1));
        }

        Database.subBank(amt);

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
