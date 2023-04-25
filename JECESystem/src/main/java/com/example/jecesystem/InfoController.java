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
import java.util.Optional;
import java.util.ResourceBundle;

public class InfoController implements Initializable {

  @FXML
  private Text disAddress;
  @FXML
  private Text disAge;
  @FXML
  private Text disEmail;
  @FXML
  private Text disPass;
  @FXML
  private Text disPhone;
  @FXML
  private Text disUser;
  @FXML
  private Text disfname;
  @FXML
  private Text dislname;
  @FXML
  private TextArea forOwe;
  @FXML
  private TextArea forGuest;
  @FXML
  private TableColumn<Person, Button> cancel;
  @FXML
  private TableColumn<Person, Integer> court;
  @FXML
  private TableColumn<Person, String> dayTime;
  @FXML
  private TableColumn<Person, Integer> guestCol;
  @FXML
  private TableColumn<Person, String> typeCol;
  @FXML
  private TableView<Person> table;
  @FXML
  private TextArea annualPayment;

  ObservableList<Person> list = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    forOwe.setText("$" + (Database.owe + Database.annual));
    forGuest.setText("Current Guest Count: " + Database.guestsFromDatabase + "\nYou're allowed " + (6 - Database.guestsFromDatabase) + " more \nguests for the month");

    int ann;

    if(Database.age < 18)
    {
      ann = 250;
    } else if (Database.age < 65) {
      ann = 400;
    } else {
      ann = 300;
    }

    annualPayment.setText("$" + ann);

    disAddress.setText(Database.addr);
    disAge.setText(String.valueOf(Database.age));
    disEmail.setText(Database.email);
    disPass.setText(Database.memberPass);
    disPhone.setText(Database.phone);
    disUser.setText(Database.memberUser);
    disfname.setText(Database.fName);
    dislname.setText(Database.lName);

    court.setCellValueFactory(new PropertyValueFactory<>("court"));
    dayTime.setCellValueFactory(new PropertyValueFactory<>("date"));
    cancel.setCellValueFactory(new PropertyValueFactory<>("cancel"));
    typeCol.setCellValueFactory(new PropertyValueFactory<>("gameType"));
    guestCol.setCellValueFactory(new PropertyValueFactory<>("numOfGuests"));

    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      Date d;
      Time t;
      String date;
      int gs;
      String typeGame;

      //Loops through each court
      for (int i = 1; i < 13; i++) {
        String court = "court" + i;
        String sql = "select * from " + court + " where username = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, Database.memberUser);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          d = resultSet.getDate("dayAndTime");
          t = resultSet.getTime("dayAndTime");

          date = d.toString() + " " + t.toString();

          //If they have a reservation but it's already passed
          //don't display it
          if(!Database.hasPassed(date))
          {
            date = date.substring(0, 16);
            gs = resultSet.getInt("occupied") - 1;

            if(resultSet.getBoolean("typeGame"))
            {
              typeGame = "Double";
            } else {
              typeGame = "Single";
            }

            Person person = new Person(i, date, typeGame, gs);

            list.add(person);
          }
        }

        table.setItems(list);

        preparedStatement.close();
        resultSet.close();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

    // cancel membership
  public void cancelMembership(ActionEvent event) throws IOException {

      Alert con = new Alert(Alert.AlertType.CONFIRMATION);
      con.setTitle("Cancel Membership");
      con.setContentText("Do you want to cancel your membership");
      Optional<ButtonType> result = con.showAndWait();

      if (result.isPresent() && result.get() == ButtonType.OK){
        Database.deleteFromCourts(Database.memberUser);
        Database.deleteFromDb(Database.memberUser, "directory");
        Database.addFromWait();
        App.setRoot("login");
      }
  }

  @FXML
  void switchToHomescreen(ActionEvent event) throws IOException {

    System.out.println(Database.extension);

      if(Database.extension.equals("tennis.com"))
      {
        App.setRoot("ctscreen");
      } else {
        App.setRoot("memscreen");
      }
  }
}
