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

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ResourceBundle;

public class MakeResController implements Initializable {

  @FXML
  private Tab court1;

  @FXML
  private Tab court10;

  @FXML
  private Tab court11;

  @FXML
  private Tab court12;

  @FXML
  private Tab court2;

  @FXML
  private Tab court3;

  @FXML
  private Tab court4;

  @FXML
  private Tab court7;

  @FXML
  private Tab court8;

  @FXML
  private Tab court9;

  @FXML
  private Tab court5;

  @FXML
  private Tab court6;

  @FXML
  private ChoiceBox<String> dayOfWeek;

  @FXML
  private ChoiceBox<String> timeOfRes;

  ObservableList times = FXCollections.observableArrayList();
  ObservableList days = FXCollections.observableArrayList();

  @FXML
  void onSubmit(ActionEvent event) {

  }

  @FXML
  void court10Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court11Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court12Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court1Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court2Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court3Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court4Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court5Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court6Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court7Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court8Change(ActionEvent event) {
    //loadData();
  }

  @FXML
  void court9Change(ActionEvent event) {
    //loadData();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    loadData();
  }

  private void loadData() {

    times.removeAll(times);

    String nine = "09:00:00";
    String nine3 = "09:30:00";
    String ten = "10:00:00";
    String ten3 = "10:30:00";
    String eleven = "11:00:00";
    String eleven3 = "11:30:00";
    String twelve = "12:00:00";
    String twelve3 = "12:30:00";
    String one = "1:00:00";
    String one3 = "1:30:00";
    String two = "2:00:00";
    String two3 = "2:30:00";
    String three = "3:00:00";
    String three3 = "3:30:00";
    String four = "4:00:00";
    String four3 = "4:30:00";
    String five = "5:00:00";
    String five3 = "5:30:00";
    String six = "6:00:00";
    String six3 = "6:30:00";

    times.addAll(nine, nine3, ten, ten3, eleven, eleven3, twelve, twelve3, one, one3, two, two3,
      three, three3, four, four3, five, five3, six, six3);
    timeOfRes.getItems().addAll(times);

    days.removeAll(days);
    String today = "Today";
    String monday = "Monday";
    String tuesday = "Tuesday";
    String wednesday = "Wednesday";
    String thursday = "Thursday";
    String friday = "Friday";
    String saturday = "Saturday";
    String sunday = "Sunday";

    days.addAll(today, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
    dayOfWeek.getItems().addAll(days);
  }

  @FXML
  void submitreservation(ActionEvent event) {

    Alert con = new Alert(Alert.AlertType.CONFIRMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);

    String day = dayOfWeek.getValue();
    String time = timeOfRes.getValue();

    String slot = "";
    String courtNum = "";

    if(court1.isSelected()) {courtNum = "court1";}
    else if(court2.isSelected()) {courtNum = "court2";}
    else if(court3.isSelected()) {courtNum = "court3";}
    else if(court4.isSelected()) {courtNum = "court4";}
    else if(court5.isSelected()) {courtNum = "court5";}
    else if(court6.isSelected()) {courtNum = "court6";}
    else if(court7.isSelected()) {courtNum = "court7";}
    else if(court8.isSelected()) {courtNum = "court8";}
    else if(court9.isSelected()) {courtNum = "court9";}
    else if(court10.isSelected()) {courtNum = "court10";}
    else if(court11.isSelected()) {courtNum = "court11";}
    else if(court12.isSelected()) {courtNum = "court12";}

    if (dayOfWeek.getValue().equals("Monday")) {
      slot = Database.formatMon + " " + time;
    } else if (dayOfWeek.getValue().equals("Tuesday")) {
      slot = Database.formatTues + " " + time;
    } else if (dayOfWeek.getValue().equals("Wednesday")) {
      slot = Database.formatWed + " " + time;
    } else if (dayOfWeek.getValue().equals("Thursday")) {
      slot = Database.formatThur + " " + time;
    } else if (dayOfWeek.getValue().equals("Friday")) {
      slot = Database.formatFri + " " + time;
    } else if (dayOfWeek.getValue().equals("Saturday")) {
      slot = Database.formatSat + " " + time;
    } else if (dayOfWeek.getValue().equals("Sunday")) {
      slot = Database.formatSun + " " + time;
    } else if (dayOfWeek.getValue().equals("Today")) {
      slot = Database.dateTime + " " + time;
    }

    Database.makeRes(courtNum, Database.memberUser, slot);


      /*
          - Can’t put more than 4 people in a court
          - Any of the people on one court can’t also be reserved on another court at the same time
          - Can’t reserve more than twice a day
       */

    LocalDateTime now = LocalDateTime.now();

    //boolean isDouble = (doubleGame.isSelected()) ? true : false;


/*
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

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    App.setRoot("memscreen");
  }
}
