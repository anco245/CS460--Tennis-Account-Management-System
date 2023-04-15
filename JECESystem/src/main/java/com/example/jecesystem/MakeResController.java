package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class MakeResController implements Initializable {

  @FXML
  private ChoiceBox<String> dayOfWeek;
  @FXML
  private ChoiceBox<String> timeOfRes;
  @FXML
  private ChoiceBox<String> numOfCourt;
  @FXML
  private ChoiceBox<Integer> amtOfGuests;

  ObservableList times = FXCollections.observableArrayList();
  ObservableList days = FXCollections.observableArrayList();
  ObservableList court = FXCollections.observableArrayList();
  ObservableList guest = FXCollections.observableArrayList();

  Calendar rightNow = Calendar.getInstance();
  int hour = rightNow.get(Calendar.HOUR_OF_DAY);
  int minute = rightNow.get(Calendar.MINUTE);

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    loadData();
  }

  private void loadData() {

    times.addAll(Arrays.asList(Database.times).subList(0, 20));
    timeOfRes.getItems().addAll(times);

    //Sees which days are available in this court
    //and puts them into an array
    days.addAll("Today", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    dayOfWeek.getItems().addAll(days);

    //Sees which days are available in this court
    //and puts them into an array
    court.addAll("Court 1", "Court 2", "Court 3", "Court 4", "Court 5", "Court 6", "Court 7",
                    "Court 8", "Court 9", "Court 10", "Court 11", "Court 12");
    numOfCourt.getItems().addAll(court);

    guest.addAll(1, 2, 3);
    amtOfGuests.getItems().addAll(guest);
  }

  boolean isToday(String time)
  {
    int resMin = Integer.parseInt(time.substring(3, 5));
    int resHour;

    if(time.charAt(0) == '0')
    {
      resHour = Integer.parseInt(time.substring(1, 2));
    } else {
      resHour = Integer.parseInt(time.substring(0, 2));
    }

    return (resHour != hour || minute >= resMin) && hour >= resHour;
  }


  @FXML
  void submitreservation(ActionEvent event) throws IOException, SQLException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Alert con = new Alert(Alert.AlertType.CONFIRMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);

    String day = dayOfWeek.getValue();
    String time = timeOfRes.getValue();
    int guests = 0;

    if(amtOfGuests.getValue() != null)
    {
      guests = amtOfGuests.getValue();
    }

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

    if(numOfCourt.getValue() == null)
    {
      error.setTitle("Error");
      error.setContentText("A court hasn't been selected");
      error.showAndWait();
    } else if (dayOfWeek.getValue() == null) {
      error.setTitle("Error");
      error.setContentText("A day of the week hasn't been selected");
      error.showAndWait();
    } else if (timeOfRes.getValue() == null) {
      error.setTitle("Error");
      error.setContentText("A time hasn't been selected");
      error.showAndWait();
    } else if (Database.sameTimeOtherCourt(Database.memberUser, slot, courtNum)) {
      error.setTitle("Error");
      error.setContentText("You've already reserved another court at this time");
      error.showAndWait();
    } else if (Database.exceededResLimit(slot)) {
      error.setTitle("Error");
      error.setContentText("You can only reserve 2 courts for any day.\nTry another day.");
      error.showAndWait();
    } else if (dayOfWeek.getValue().equals("Today") && isToday(time) ) {
      error.setTitle("Error");
      error.setContentText("That time slot has already passed.\nTry another.");
      error.showAndWait();
    } else if (Database.available(courtNum, slot)) {
      error.setTitle("Error");
      error.setContentText("That timeslot is not available.\nTry another.");
      error.showAndWait();
    } else if(Database.guests + guests > 6) {
      error.setTitle("Reached limit");
      error.setContentText("You're " + ((Database.guests + guests) - 6) + " guests over your limit for the month.");
      error.showAndWait();
    } else if (amtOfGuests.getValue() == null) {
      con.setContentText("You're making a reservation for " + slot.substring(0, 16) + ".\n" +
        "Do you want to continue?");

      Optional<ButtonType> result = con.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
        Database.makeRes(courtNum, Database.memberUser, slot, guests+1);
        App.setRoot("courtreservation");
      }
    } else {
      con.setContentText("You're making a reservation for " + slot.substring(0, 16) + "." +
        "\nYou will have to pay $" + (guests * 10) + " for " + guests + " guests\n" +
        "and you will have " + (6 - guests) + " guests left for the month.\n" +
        "Do you want to continue?");

      Optional<ButtonType> result = con.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {

        Database.addSubGuests(amtOfGuests.getValue());
        Database.addSubOwe(Database.memberUser, amtOfGuests.getValue() * 10);
        Database.makeRes(courtNum, Database.memberUser, slot, guests);
        App.setRoot("courtreservation");
      }
    }
  }

  @FXML void switchToHome(ActionEvent event) throws IOException {App.setRoot("memscreen");}
}
