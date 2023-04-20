package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
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

    String str = "Amount owed: " + Database.owe;

    if(!Database.hasBankAccount(Database.memberUser))
    {
      bankInfo.setText(str + "\n\nYou haven't added a bank yet");
    } else {
      bankInfo.setText(str);
      //String str = Database.getBankInfo();
      //bankInfo.setText(str);
    }
  }

  @FXML
  void onSubmit(ActionEvent event) {
    try {

      if(!Database.hasBankAccount(Database.memberUser))
      {
        System.out.println("here");
        error.setTitle("Error");
        error.setContentText("You haven't added a bank account yet");
        error.showAndWait();
      } else {
        int amt = Integer.parseInt(amount.getText());

        Database.setLate(Database.memberUser, false);
        Database.addSubOwe(Database.memberUser, amt*(-1));

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
      //Database.removeBank();

      App.setRoot("addpay");
  }

  @FXML
  void switchToAddBank(ActionEvent event) throws IOException {
      App.setRoot("addbank");
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
      App.setRoot("memscreen");
  }
}
