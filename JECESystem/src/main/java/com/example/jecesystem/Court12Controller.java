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
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Court12Controller implements Initializable {

  @FXML
  private TableView<Person> courtDisplay;
  @FXML
  private TableColumn<Person, String> status;
  @FXML
  private TableColumn<Person, Button> reserve;
  @FXML
  private TableColumn<Person, String> dayAndTime;
  @FXML
  private TableColumn<Person, ChoiceBox> guests;
  @FXML
  private TableColumn<Person, ChoiceBox> singleDouble;

  ObservableList<Person> list = FXCollections.observableArrayList();
  Person person;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    dayAndTime.setCellValueFactory(new PropertyValueFactory<>("date"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    reserve.setCellValueFactory(new PropertyValueFactory<>("reserve"));
    guests.setCellValueFactory(new PropertyValueFactory<>("guests"));
    singleDouble.setCellValueFactory(new PropertyValueFactory<>("singleDouble"));

    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      PreparedStatement preparedStatement = connection.prepareStatement("select * from court12");
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        int occ = resultSet.getInt("occupied");
        Timestamp t = resultSet.getTimestamp("dayAndTime");

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
