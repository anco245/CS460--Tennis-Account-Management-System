package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ChairDirectoryController implements Initializable {

  @FXML
  private TableColumn<Person, String> address;

  @FXML
  private TableColumn<Person, String> age;

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
  private TableColumn<Person, String> pass;

  @FXML
  private TableColumn<Person, String> phone;

  @FXML
  private TableColumn<Person, Boolean> shown;

  @FXML
  private TableView<Person> table;

  @FXML
  private TableColumn<Person, String> user;

  ObservableList<Person> list = FXCollections.observableArrayList();

  @FXML
  void backToHomescreen(ActionEvent event) throws IOException {
      App.setRoot("ctscreen");
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
    age.setCellValueFactory(new PropertyValueFactory<Person, String>("age"));
    address.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
    phone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
    email.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
    user.setCellValueFactory(new PropertyValueFactory<Person, String>("user"));
    pass.setCellValueFactory(new PropertyValueFactory<Person, String>("pass"));
    late.setCellValueFactory(new PropertyValueFactory<Person, Boolean>("late"));
    shown.setCellValueFactory(new PropertyValueFactory<Person, Boolean>("shown"));
    notify.setCellValueFactory(new PropertyValueFactory<Person, Button>("notify"));
    denotify.setCellValueFactory(new PropertyValueFactory<Person, Button>("denotify"));

    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        System.out.println(list.isEmpty());
        String first = resultSet.getString("firstName").substring(0, 1).toUpperCase() +
          resultSet.getString("firstName").substring(1);
        String last = resultSet.getString("lastName").substring(0, 1).toUpperCase() +
          resultSet.getString("lastName").substring(1);

        String userName = first + " " + last;
        String userAge = resultSet.getString("age");
        String userAddr = resultSet.getString("address");
        String userPhone = resultSet.getString("phone");
        String userEmail = resultSet.getString("email") + ".com";;
        String userUser = resultSet.getString("username");
        String userPass = resultSet.getString("pword");

        boolean v = resultSet.getBoolean("verified");
        boolean shown = resultSet.getBoolean("shown");
        boolean late = resultSet.getBoolean("late");

        Person person = new Person(userName, userAge, userAddr, userPhone, userEmail, shown, late, userUser, userPass);

        person.notify.setOnAction(e -> table.refresh());
        person.denotify.setOnAction(e -> table.refresh());

        list.add(person);
      }

      preparedStatement.close();
      resultSet.close();

      table.setItems(list);

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

}