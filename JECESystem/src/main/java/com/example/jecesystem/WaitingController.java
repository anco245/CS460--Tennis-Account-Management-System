package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class WaitingController implements Initializable {

  @FXML
  private TableColumn<Person, String> address;
  @FXML
  private TableColumn<Person, Integer> age;
  @FXML
  private TableColumn<Person, String> email;
  @FXML
  private TableColumn<Person, String> name;
  @FXML
  private TableColumn<Person, String> pass;
  @FXML
  private TableColumn<Person, String> phone;
  @FXML
  private TableColumn<Person, Boolean> shown;
  @FXML
  private TableColumn<Person, Integer> owe;
  @FXML
  private TableView<Person> table;
  @FXML
  private TableColumn<Person, String> user;

  ObservableList<Person> list = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    age.setCellValueFactory(new PropertyValueFactory<>("age"));
    address.setCellValueFactory(new PropertyValueFactory<>("address"));
    phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    user.setCellValueFactory(new PropertyValueFactory<>("user"));
    pass.setCellValueFactory(new PropertyValueFactory<>("pass"));
    shown.setCellValueFactory(new PropertyValueFactory<>("shown"));
    owe.setCellValueFactory(new PropertyValueFactory<>("owe"));

    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM waiting");

      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        String first = resultSet.getString("firstName").substring(0, 1).toUpperCase() +
          resultSet.getString("firstName").substring(1);
        String last = resultSet.getString("lastName").substring(0, 1).toUpperCase() +
          resultSet.getString("lastName").substring(1);

        String userName = first + " " + last;
        int userAge = resultSet.getInt("age");
        int userOwe = resultSet.getInt("owe");
        String userAddr = resultSet.getString("address");
        String userPhone = resultSet.getString("phone");
        String userEmail = resultSet.getString("email");
        String userUser = resultSet.getString("username");
        String userPass = resultSet.getString("pword");

        userOwe = userOwe + Database.getAnnual(userUser);

        boolean shown = resultSet.getBoolean("shown");

        Person person = new Person(userName, userAge, userAddr, userPhone, userEmail, shown,
          userOwe, userUser, userPass);

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
  void backToHomescreen(ActionEvent event) throws IOException {
    App.setRoot("adminscreen");
  }

}
