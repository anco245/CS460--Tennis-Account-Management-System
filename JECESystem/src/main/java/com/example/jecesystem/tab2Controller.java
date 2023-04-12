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
import java.util.ResourceBundle;

public class tab2Controller implements Initializable {

  @FXML private ChoiceBox<String> dayOfWeek;

  @FXML private ChoiceBox<String> timeOfRes;

  ObservableList times = FXCollections.observableArrayList();
  ObservableList days = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb) {loadData();}

  private void loadData() {

    /*
    //Sees which times during the day are available in this court
    //and puts them into an array
    times.removeAll(times);
    String[] openTimes = Database.availableSlots();
    for(int i = 0; openTimes != null && openTimes.length > 0; i++)
    {
      times.add(openTimes[i]);
    }
    timeOfRes.getItems().addAll(times);

    //Sees which days are available in this court
    //and puts them into an array
    days.removeAll(days);
    String[] openDays = Database.availableDays();
    for(int i = 0; openDays != null && openDays.length > 0; i++)
    {
      days.add(availableDays[i]);
    }
    dayOfWeek.getItems().addAll(days);
    */
  }

  @FXML
  void submitreservation(ActionEvent event) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Alert con = new Alert(Alert.AlertType.CONFIRMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);

    String day = dayOfWeek.getValue();
    String time = timeOfRes.getValue();

    String slot = "";
    String courtNum = "court2";

    if (day.equals("Monday")) {slot = Database.nextMonday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Tuesday")) {slot = Database.nextTuesday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Wednesday")) {slot = Database.nextWednesday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Thursday")) {slot = Database.nextThursday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Friday")) {slot = Database.nextFriday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Saturday")) {slot = Database.nextSaturday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Sunday")) {slot = Database.nextSunday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Today")) {slot = Database.dateTime.format(formatter) + " " + time;}

    /*
    if (Database.checkIfLimit(courtNum, slot)) {
      error.setTitle("Error");
      error.setContentText("You've already reached the limit of 2 court for today.\nTry another day.");
      error.showAndWait();
    } else if (Database.atSameTime(Database.memberUser)) {
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

  @FXML void switchToTab1(ActionEvent event) throws IOException {App.setRoot("tab1");}
  @FXML void switchToTab3(ActionEvent event) throws IOException {App.setRoot("tab3");}
  @FXML void switchToTab4(ActionEvent event) throws IOException {App.setRoot("tab4");}
  @FXML void switchToTab5(ActionEvent event) throws IOException {App.setRoot("tab5");}
  @FXML void switchToTab6(ActionEvent event) throws IOException {App.setRoot("tab6");}
  @FXML void switchToTab7(ActionEvent event) throws IOException {App.setRoot("tab7");}
  @FXML void switchToTab8(ActionEvent event) throws IOException {App.setRoot("tab8");}
  @FXML void switchToTab9(ActionEvent event) throws IOException {App.setRoot("tab9");}
  @FXML void switchToTab10(ActionEvent event) throws IOException {App.setRoot("tab10");}
  @FXML void switchToTab11(ActionEvent event) throws IOException {App.setRoot("tab11");}
  @FXML void switchToTab12(ActionEvent event) throws IOException {App.setRoot("tab12");}
  @FXML void switchToHome(ActionEvent event) throws IOException {App.setRoot("memscreen");}
}
