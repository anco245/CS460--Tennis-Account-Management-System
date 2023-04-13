package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddGuestController implements Initializable {

    @FXML
    private ComboBox<Integer> numOfGuests;

    ObservableList list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
      loadData();
    }

    private void loadData() {
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
    void toSubmit(ActionEvent event) throws IOException {

      int getGuests = numOfGuests.getValue();

      Alert con = new Alert(Alert.AlertType.CONFIRMATION);
      Alert error = new Alert(Alert.AlertType.ERROR);

      //Need to somehow have it reset the first of every month
      if((Database.guests + getGuests) > 6)
      {
        error.setTitle("Reached limit");
        error.setContentText("You're " + ((Database.guests + getGuests) - 6) + " guests over your limit for the month.");
        error.showAndWait();
      } else if (Database.guests == 6) {
        error.setTitle("Reached limit");
        error.setContentText("You can't add any more guests for the month.");
        error.showAndWait();
      } else {
        con.setTitle("Adding a Guest");
        con.setContentText("You will be adding " + getGuests + " guests.\n" +
          "This will add a fee of $" + (getGuests * 10) + " to your account.\n" +
          "You will be allowed " + (6 - getGuests) + " more guests for the month.\n" +
          "Do you want to continue?");

        Optional<ButtonType> result = con.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

          Database.addSubGuests(getGuests);
          Database.addSubOwe(Database.memberUser, getGuests * 10);

          App.setRoot("memscreen");
        }
      }
    }

    @FXML
    void switchToHomescreen(ActionEvent event) throws IOException {
      App.setRoot("memscreen");
    }
}
