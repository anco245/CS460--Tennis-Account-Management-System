package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddGuestController implements Initializable {

    @FXML
    private ComboBox<Integer> numOfGuests;

    @FXML
    private Button submit;

    ObservableList list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      loadData();
    }
    private void loadData() {
      list.removeAll(list);

      int a = 1;
      int b = 2;
      int c = 3;
      int d = 4;
      int e = 5;
      int f = 6;

      list.addAll(a, b, c, d, e, f);
      numOfGuests.getItems().addAll(list);
    }

    @FXML
    void toSubmit(ActionEvent event) {

      int guests = numOfGuests.getValue();

      Database.addSubGuests(Database.memberUser, guests);
    }

    @FXML
    void switchToHomescreen(ActionEvent event) throws IOException {
      App.setRoot("memscreen");

    }
}
