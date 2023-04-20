package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPayController implements Initializable {

  @FXML
  private TextField amtWithdrawalField;
  @FXML
  private TextField bankNameField;
  @FXML
  private TextField numberField;
  @FXML
  private TextField ssnField;

  @FXML
  private ChoiceBox<String> typeAccount;

  ObservableList type = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    type.addAll("Checking", "Saving");
    typeAccount.getItems().addAll(type);
  }

  @FXML
  void onSubmit(ActionEvent event) {

    //make new co;imn in directory

    //set late boolean to false

    //if username associated with bank account in table
    //show pay screen along with bank info . one bank at a time
    //can remove bank account
    //display amount owed and enter into textbox how much you want to pay
    //else show add a bank account screen
    try{
      int amt = Integer.parseInt(amtWithdrawalField.getText());
      String bank = bankNameField.getText();
      String accountNum = numberField.getText();
      String ssn = ssnField.getText();
      String cos = typeAccount.getValue();

      //Database.addBank(Database.memberUser, bank, accountNum, cos, ssn);

    } catch (Exception e) {

    }
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    App.setRoot("memscreen");
  }
}
