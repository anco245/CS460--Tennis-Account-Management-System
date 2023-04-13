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

public class DirectoryController implements Initializable {

  @FXML
  private TableColumn<Person, String> address;

  @FXML
  private TableColumn<Person, Integer> age;

  @FXML
  private TableColumn<Person, String> email;

  @FXML
  private TableColumn<Person, String> name;

  @FXML
  private TableColumn<Person, String> phone;

  @FXML
  private TableView<Person> table;

  ObservableList<Person> list = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    name.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
    age.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
    address.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
    phone.setCellValueFactory(new PropertyValueFactory<Person, String>("phone"));
    email.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

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
        int userAge = resultSet.getInt("age");
        String userAddr = resultSet.getString("address");
        String userPhone = resultSet.getString("phone");
        String userEmail = resultSet.getString("email");;

        boolean v = resultSet.getBoolean("verified");
        boolean shown = resultSet.getBoolean("shown");

        if(shown)
        {
          Person person = new Person(userName, userAge, userAddr, userPhone, userEmail);
          list.add(person);
        }
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
    App.setRoot("memscreen");
  }
}
