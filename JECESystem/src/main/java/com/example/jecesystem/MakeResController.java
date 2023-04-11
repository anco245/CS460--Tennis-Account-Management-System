package com.example.jecesystem;

//import java.util.jar.Attributes.Name;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MakeResController implements Initializable {

  @FXML
  private ChoiceBox<Integer> courtNum;

  @FXML
  private ChoiceBox<String> dayOfWeek;

  @FXML
  private CheckBox nineAM;

  private CheckBox doubleGame;

  @FXML
  private Tab monTab;

  ObservableList day = FXCollections.observableArrayList();
  ObservableList court = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb)
  {
    loadData();
  }

  private void loadData() {

    court.removeAll(court);

    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;
    int e = 5;
    int f = 6;
    int g = 7;
    int h = 8;
    int i = 9;
    int j = 10;
    int k = 11;
    int l = 12;

    court.addAll(a, b, c, d, e, f, g, h, i, j, k, l);
    courtNum.getItems().addAll(court);

    day.removeAll(day);

    String monday = "Monday";
    String tuesday = "Tuesday";
    String wednesday = "Wednesday";
    String thursday = "Thursday";
    String friday = "Friday";
    String saturday = "Saturday";
    String sunday = "Sunday";

    day.addAll(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
    //dayOfWeek.getItems().addAll(day);
  }

  @FXML
  void submitreservation(ActionEvent event) {


    /*
        - Look at which courts are being used at which time and day, and which courts are available.
        - Make / cancel a court reservation
        - Can’t put more than 4 people in a court
        - Any of the people on one court can’t also be reserved on another court at the same time
        - Can’t reserve more than twice a day
     */

    if (monTab.isSelected())
    {

    }

    Alert con = new Alert(Alert.AlertType.CONFIRMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);

    LocalDateTime now = LocalDateTime.now();

    int givenCourt = courtNum.getValue();
    String givenDay = dayOfWeek.getValue();
    String timeOfRes = "";

    boolean isDouble = (doubleGame.isSelected()) ? true : false;



    if (Database.checkRes(givenCourt)) {
      error.setTitle("Error");
      error.setContentText("This court has already been reserved.\nTry Again.");
      error.showAndWait();
    } else if (Database.inReservation(Database.memberUser)) {
      error.setTitle("Error");
      error.setContentText("You've already reserved a court for today.\nTry Again.");
      error.showAndWait();
    } else {
      /*
      if(nineam.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (nine30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (ten.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (ten30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (eleven.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (eleven30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (noon.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (noon30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (one.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (one30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (two.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (two30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (three.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (three30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (four.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (four30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (five.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (five30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (six.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (six30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (seven.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (seven30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (eight.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (eight30.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      else if (ninepm.isSelected()){timeOfRes = "20120618 9:00:00 AM"; Database.makeRes(givenCourt, givenDay, timeOfRes);}
      */
    }
  }
}
