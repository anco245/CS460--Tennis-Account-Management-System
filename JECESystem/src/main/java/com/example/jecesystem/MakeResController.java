package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.time.*;

public class MakeResController implements Initializable {

  @FXML
  private ChoiceBox<String> dayOfWeek;

  @FXML
  private ChoiceBox<String> timeOfRes;

  @FXML
  private ChoiceBox<String> numOfCourt;

  ObservableList times = FXCollections.observableArrayList();
  ObservableList days = FXCollections.observableArrayList();
  ObservableList court = FXCollections.observableArrayList();

  Calendar rightNow = Calendar.getInstance();
  int hour = rightNow.get(Calendar.HOUR_OF_DAY);
  int minute = rightNow.get(Calendar.MINUTE);

  int resHour = 0;
  int resMin = 0;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    loadData();
  }
/*
      times.addAll(Database.times[0], Database.times[1], Database.times[2], Database.times[3], Database.times[4],
  Database.times[5], Database.times[6], Database.times[7], Database.times[8], Database.times[9],
  Database.times[10], Database.times[11]);
  */
  private void loadData() {

    times.removeAll(times);

    for(int i = 0; i < Database.times.length; i++)
    {
      resMin = Integer.parseInt(Database.times[i].substring(3, 5));

      if(Database.times[i].charAt(0) == '0')
      {
        resHour = Integer.parseInt(Database.times[i].substring(1, 2));
      } else {
        resHour = Integer.parseInt(Database.times[i].substring(0, 2));
      }

      if((resHour == hour && minute < resMin) || hour < resHour)
      {
          times.add(Database.times[i]);
      }
    }

    timeOfRes.getItems().addAll(times);

    //Sees which days are available in this court
    //and puts them into an array
    days.removeAll(days);

    days.addAll("Today", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    dayOfWeek.getItems().addAll(days);

    //Sees which days are available in this court
    //and puts them into an array
    court.removeAll(court);

    court.addAll("Court 1", "Court 2", "Court 3", "Court 4", "Court 5", "Court 6", "Court 7",
                    "Court 8", "Court 9", "Court 10", "Court 11", "Court 12");
    numOfCourt.getItems().addAll(court);
  }

  @FXML
  void submitreservation(ActionEvent event) throws IOException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Alert con = new Alert(Alert.AlertType.CONFIRMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);
    Alert info = new Alert(Alert.AlertType.INFORMATION);

    String day = dayOfWeek.getValue();
    String time = timeOfRes.getValue();

    String slot = "";
    String courtNum = "";

    if(numOfCourt.getValue().equals("Court 1")) {courtNum = "court1";}
    else if(numOfCourt.getValue().equals("Court 2")) {courtNum = "court2";}
    else if(numOfCourt.getValue().equals("Court 3")) {courtNum = "court3";}
    else if(numOfCourt.getValue().equals("Court 4")) {courtNum = "court4";}
    else if(numOfCourt.getValue().equals("Court 5")) {courtNum = "court5";}
    else if(numOfCourt.getValue().equals("Court 6")) {courtNum = "court6";}
    else if(numOfCourt.getValue().equals("Court 7")) {courtNum = "court7";}
    else if(numOfCourt.getValue().equals("Court 8")) {courtNum = "court8";}
    else if(numOfCourt.getValue().equals("Court 9")) {courtNum = "court9";}
    else if(numOfCourt.getValue().equals("Court 10")) {courtNum = "court10";}
    else if(numOfCourt.getValue().equals("Court 11")) {courtNum = "court11";}
    else if(numOfCourt.getValue().equals("Court 12")) {courtNum = "court12";}

    if (dayOfWeek.getValue().equals("Today")) {slot = Database.dateTime.format(formatter) + " " + time;}
    else if (day.equals("Monday")) {slot = Database.nextMonday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Tuesday")) {slot = Database.nextTuesday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Wednesday")) {slot = Database.nextWednesday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Thursday")) {slot = Database.nextThursday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Friday")) {slot = Database.nextFriday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Saturday")) {slot = Database.nextSaturday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Sunday")) {slot = Database.nextSunday.format(formatter) + " " + time;}

    if (Database.exceededResLimit()) {
      error.setTitle("Error");
      error.setContentText("You've already reached the limit of 2 court for today.\nTry another day.");
      error.showAndWait();
    }  else {
      Database.makeRes(courtNum, Database.memberUser, slot);
      info.setContentText("You've made a reservation for " + slot.substring(0, 15));
      info.showAndWait();
      App.setRoot("courtreservation");
    }

    /*
    else if (Database.atSameTime(Database.memberUser)) {
      error.setTitle("Error");
      error.setContentText("You're already booked in another court at that time.\nTry another day.");
      error.showAndWait();
    } else {
      Database.makeRes(courtNum, Database.memberUser, slot);
    }
     */

     /*
       - Can’t put more than 4 people in a court
       - Any of the people on one court can’t also be reserved on another court at the same time
       - Can’t reserve more than twice a day
     */
  }

  @FXML void switchToHome(ActionEvent event) throws IOException {App.setRoot("memscreen");}
}
