package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

public class Court12Controller implements Initializable {

  @FXML
  private ChoiceBox<String> dayOfWeek;

  @FXML
  private ChoiceBox<Integer> numOfGuests;

  @FXML
  private ChoiceBox<String> timeOfDay;

  @FXML
  private TableView<Person> courtDisplay;

  @FXML
  private Text courtNum;

  @FXML
  private TableColumn<Person, String> status;

  @FXML
  private TableColumn<Person, String> dayAndTime;

  @FXML
  private TableColumn<Person, Button> reserve;

  ObservableList guest = FXCollections.observableArrayList();

  Calendar rightNow = Calendar.getInstance();
  int hour = rightNow.get(Calendar.HOUR_OF_DAY);
  int minute = rightNow.get(Calendar.MINUTE);

  ObservableList<Person> list = FXCollections.observableArrayList();

  Person person;

  @FXML
  private TableColumn<Person, ChoiceBox> guests;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //loadData();

    dayAndTime.setCellValueFactory(new PropertyValueFactory<>("date"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    reserve.setCellValueFactory(new PropertyValueFactory<>("reserve"));
    guests.setCellValueFactory(new PropertyValueFactory<>("guests"));

    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      PreparedStatement preparedStatement = connection.prepareStatement("select * from court12");

      ResultSet resultSet = preparedStatement.executeQuery();

      String dayOfWeek = "";

      while (resultSet.next()) {
        int occ = resultSet.getInt("occupied");
        Timestamp t = resultSet.getTimestamp("dayAndTime");
        //Date d = resultSet.getDate("dayAndTime");

        //Convert numbered days to week names

        if(occ == 0)
        {
          person = new Person(t.toString().substring(0, 19), "Available", 12);
        } else {
          person = new Person(t.toString().substring(0, 19), "Already Taken", 12);
        }

        list.add(person);
      }

      courtDisplay.setItems(list);

      preparedStatement.close();
      resultSet.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  private void loadData() {
    guest.addAll(1, 2, 3);
    numOfGuests.getItems().addAll(guest);
  }

  //Used for something
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
  void onSubmit(ActionEvent event) throws IOException, SQLException {

    String time = timeOfDay.getValue();
    int guests = 0;

    if(numOfGuests.getValue() != null)
    {
      guests = numOfGuests.getValue();
    }

    /*
    if (dayOfWeek.getValue().equals("Today")) {slot = Database.dateTime.format(formatter) + " " + time;}
    else if (dayOfWeek.equals("Monday")) {slot = Database.nextMonday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Tuesday")) {slot = Database.nextTuesday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Wednesday")) {slot = Database.nextWednesday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Thursday")) {slot = Database.nextThursday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Friday")) {slot = Database.nextFriday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Saturday")) {slot = Database.nextSaturday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Sunday")) {slot = Database.nextSunday.format(formatter) + " " + time;}
    */
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    App.setRoot("memscreen");
  }

  @FXML
  void switchToCourt9(ActionEvent event) throws IOException {
    App.setRoot("court9");
  }

  @FXML
  void switchToCourt10(ActionEvent event) throws IOException {
    App.setRoot("court10");
  }

  @FXML
  void switchToCourt11(ActionEvent event) throws IOException {
    App.setRoot("court11");
  }

  @FXML
  void switchToCourt1(ActionEvent event) throws IOException {
    App.setRoot("court1");
  }

  @FXML
  void switchToCourt2(ActionEvent event) throws IOException {
    App.setRoot("court2");
  }

  @FXML
  void switchToCourt3(ActionEvent event) throws IOException {
    App.setRoot("court3");
  }

  @FXML
  void switchToCourt4(ActionEvent event) throws IOException {
    App.setRoot("court4");
  }

  @FXML
  void switchToCourt5(ActionEvent event) throws IOException {
    App.setRoot("court5");
  }

  @FXML
  void switchToCourt6(ActionEvent event) throws IOException {
    App.setRoot("court6");
  }

  @FXML
  void switchToCourt7(ActionEvent event) throws IOException {
    App.setRoot("court7");
  }

  @FXML
  void switchToCourt8(ActionEvent event) throws IOException {
    App.setRoot("court8");
  }
}
