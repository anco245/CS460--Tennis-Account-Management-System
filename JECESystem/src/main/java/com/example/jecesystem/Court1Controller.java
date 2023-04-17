package com.example.jecesystem;

  import javafx.collections.FXCollections;
  import javafx.collections.ObservableList;
  import javafx.event.ActionEvent;
  import javafx.fxml.FXML;
  import javafx.fxml.Initializable;
  import javafx.scene.control.*;
  import javafx.scene.control.cell.PropertyValueFactory;
  import javafx.scene.text.Text;

  import java.io.IOException;
  import java.net.URL;
  import java.sql.*;
  import java.time.LocalDateTime;
  import java.time.format.DateTimeFormatter;
  import java.util.Calendar;
  import java.util.Optional;
  import java.util.ResourceBundle;

public class Court1Controller implements Initializable {

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

  ObservableList times = FXCollections.observableArrayList();
  ObservableList days = FXCollections.observableArrayList();
  ObservableList guest = FXCollections.observableArrayList();

  Calendar rightNow = Calendar.getInstance();
  int hour = rightNow.get(Calendar.HOUR_OF_DAY);
  int minute = rightNow.get(Calendar.MINUTE);

  ObservableList<Person> list = FXCollections.observableArrayList();

  Person person;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loadData();

    dayAndTime.setCellValueFactory(new PropertyValueFactory<>("date"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    status.setCellValueFactory(new PropertyValueFactory<>("reserve"));

    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      PreparedStatement preparedStatement = connection.prepareStatement("select * from court1");

      ResultSet resultSet = preparedStatement.executeQuery();

      String dayOfWeek = "";

      while (resultSet.next()) {
        int occ = resultSet.getInt("occupied");
        Timestamp t = resultSet.getTimestamp("dayAndTime");
        //Date d = resultSet.getDate("dayAndTime");

        //Convert numbered days to week names

        if(occ == 0)
        {
          person = new Person(t.toString(), "Available", 1, Database.reservedGuests);
        } else {
          person = new Person(t.toString(), "Already Taken", 1);
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
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Alert con = new Alert(Alert.AlertType.CONFIRMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);

    String time = timeOfDay.getValue();
    int guests = 0;

    if(numOfGuests.getValue() != null)
    {
      guests = numOfGuests.getValue();
    }

    String slot = "";
    String courtNum = "";

    if (dayOfWeek.getValue().equals("Today")) {slot = Database.dateTime.format(formatter) + " " + time;}
    else if (dayOfWeek.equals("Monday")) {slot = Database.nextMonday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Tuesday")) {slot = Database.nextTuesday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Wednesday")) {slot = Database.nextWednesday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Thursday")) {slot = Database.nextThursday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Friday")) {slot = Database.nextFriday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Saturday")) {slot = Database.nextSaturday.format(formatter) + " " + time;}
    else if (dayOfWeek.getValue().equals("Sunday")) {slot = Database.nextSunday.format(formatter) + " " + time;}

    if (dayOfWeek.getValue() == null) {
      error.setTitle("Error");
      error.setContentText("A day of the week hasn't been selected");
      error.showAndWait();
    } else if (dayOfWeek.getValue() == null) {
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
    } else if (Database.available("court1", slot)) {
      error.setTitle("Error");
      error.setContentText("That timeslot is not available.\nTry another.");
      error.showAndWait();
    } else if(Database.guests + guests > 6) {
      error.setTitle("Reached limit");
      error.setContentText("You're " + ((Database.guests + guests) - 6) + " guests over your limit for the month.");
      error.showAndWait();
    } else if (numOfGuests.getValue() == null) {
      con.setContentText("You're making a reservation for " + slot.substring(0, 16) + ".\n" +
        "Do you want to continue?");

      Optional<ButtonType> result = con.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {
        Database.addSubOwe(Database.memberUser, guests*10);

        Database.makeRes("court1", Database.memberUser, slot, guests+1);
        App.setRoot("courtreservation");
      }
    } else {
      con.setContentText("You're making a reservation for Court 1 at " + slot.substring(0, 16) + "." +
        "\nYou will have to pay $" + (guests * 10) + " for " + guests + " guests\n" +
        "and you will have " + (6 - guests) + " guests left for the month.\n" +
        "Do you want to continue?");

      Optional<ButtonType> result = con.showAndWait();
      if (result.isPresent() && result.get() == ButtonType.OK) {

        Database.addSubGuests(numOfGuests.getValue());
        Database.addSubOwe(Database.memberUser, numOfGuests.getValue() * 10);
        Database.makeRes("court1", Database.memberUser, slot, guests);
        App.setRoot("court1");
      }
    }
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    App.setRoot("memscreen");
  }

  @FXML
  void switchToCourt11(ActionEvent event) throws IOException {
      App.setRoot("court11");
  }

  @FXML
  void switchToCourt12(ActionEvent event) throws IOException {
    App.setRoot("court12");
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

  @FXML
  void switchToCourt9(ActionEvent event) throws IOException {
    App.setRoot("court9");
  }
}
