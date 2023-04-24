package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TreasurerDirectoryController implements Initializable {

  @FXML
  private TableColumn<Person, String> address;
  @FXML
  private TableColumn<Person, Button> denotify;
  @FXML
  private TableColumn<Person, String> email;
  @FXML
  private TableColumn<Person, Boolean> late;
  @FXML
  private TableColumn<Person, String> name;
  @FXML
  private TableColumn<Person, Button> notify;
  @FXML
  private TableColumn<Person, String> phone;
  @FXML
  private TableColumn<Person, Integer> owed;
  @FXML
  private TableView<Person> table;

  ObservableList<Person> list = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    address.setCellValueFactory(new PropertyValueFactory<>("address"));
    phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    late.setCellValueFactory(new PropertyValueFactory<>("late"));
    notify.setCellValueFactory(new PropertyValueFactory<>("notify"));
    denotify.setCellValueFactory(new PropertyValueFactory<>("denotify"));
    owed.setCellValueFactory(new PropertyValueFactory<>("owe"));

    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        String first = resultSet.getString("firstName").substring(0, 1).toUpperCase() +
          resultSet.getString("firstName").substring(1);
        String last = resultSet.getString("lastName").substring(0, 1).toUpperCase() +
          resultSet.getString("lastName").substring(1);

        String userName = first + " " + last;
        int userOwe = resultSet.getInt("owe");
        String userAddr = resultSet.getString("address");
        String userPhone = resultSet.getString("phone");
        String userEmail = resultSet.getString("email");
        String userUser = resultSet.getString("username");

        userOwe = userOwe + Database.getAnnual(userUser);

        boolean late = resultSet.getBoolean("late");

        Person person = new Person(userName, userAddr, userPhone, userEmail, late,
          userOwe, userUser);

        list.add(person);
      }

      preparedStatement.close();
      resultSet.close();

      table.setItems(list);

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  @FXML
  void switchToHome(ActionEvent event) throws IOException {
    App.setRoot("treasurer");
  }

}
