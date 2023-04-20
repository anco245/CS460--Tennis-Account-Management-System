package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBankController implements Initializable {

  Alert info = new Alert(Alert.AlertType.INFORMATION);

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
    try{
      String bank = bankNameField.getText();
      String accountNum = numberField.getText();
      String ssn = ssnField.getText();
      String cos = typeAccount.getValue();

      System.out.println("about to add to bank database");
      Database.addBank(Database.memberUser, bank, accountNum, ssn, cos);

      info.setTitle("Success");
      info.setContentText("You've successfully added a bank account");
      info.showAndWait();

      App.setRoot("addpay");

    } catch (Exception e) {

    }
  }

  @FXML
  void switchToPay(ActionEvent event) throws IOException {
    App.setRoot("addpay");
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    App.setRoot("memscreen");
  }
}
