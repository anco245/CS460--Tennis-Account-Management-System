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
import java.util.Optional;
import java.util.ResourceBundle;

public class InfoController implements Initializable {

  /*
        if username is changed
        - change username in courtres database

    Phone Number for Main Desk: 215-436-2231
    Phone Number for Technical Support: 215-663-2133
    Email for any questions: askquestion@tennis.com
  */

  @FXML
  private Text disAddress;

  @FXML
  private Text disAge;

  @FXML
  private Text disEmail;

  @FXML
  private Text disGuests;

  @FXML
  private Text disPass;

  @FXML
  private Text disPhone;

  @FXML
  private Text disRes;

  @FXML
  private Text disUser;

  @FXML
  private Text disfname;

  @FXML
  private Text dislname;

  @FXML
  private TextArea forOwe;

  @FXML
  private TextArea forRes;

  @FXML
  private TextArea forGuest;

  @FXML
  private TableColumn<Person, Button> cancel;

  @FXML
  private TableColumn<Person, Integer> court;

  @FXML
  private TableColumn<Person, String> dayTime;

  @FXML
  private TableView<Person> table;

  ObservableList<Person> list = FXCollections.observableArrayList();

  @FXML
  void cancelRes(ActionEvent event) throws IOException {
      App.setRoot("cancelRes");
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    //String str = "$" + Database.owe;
    forOwe.setText("$" + Database.owe);
    //forRes.setText(Database.printReservations(Database.memberUser));
    forGuest.setText("Current Guest Count: " + Database.guests + "\nYou're allowed " + (6 - Database.guests) + " more guests for the month");

    disAddress.setText(Database.addr);
    disAge.setText(String.valueOf(Database.age));
    disEmail.setText(Database.email);
    disPass.setText(Database.memberPass);
    disPhone.setText(Database.phone);
    disUser.setText(Database.memberUser);
    disfname.setText(Database.fName);
    dislname.setText(Database.lName);

    court.setCellValueFactory(new PropertyValueFactory<Person, Integer>("court"));
    dayTime.setCellValueFactory(new PropertyValueFactory<Person, String>("date"));
    cancel.setCellValueFactory(new PropertyValueFactory<Person, Button>("cancel"));

    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      String str = "";

      for (Integer i = 1; i < 13; i++) {
        String court = "court" + i.toString();
        String sql = "select * from " + court + " where username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, Database.memberUser);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          Date d = resultSet.getDate("dayAndTime");
          Time t = resultSet.getTime("dayAndTime");
          String date = d.toString() + " " + t.toString();

          date = date.substring(0, 16);

          Person person = new Person(i, date);

          list.add(person);
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

      if (result.get() == ButtonType.OK){
        Database.deleteFromDir(Database.memberUser);
        App.setRoot("login");
      }
  }

  @FXML
  void switchToHomescreen(ActionEvent event) throws IOException {
      App.setRoot("memscreen");
  }
}
