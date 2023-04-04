package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class DirectoryController {

  @FXML
  private TableColumn<Person, String> addrCol;

  @FXML
  private TableColumn<Person, String> ageCol;

  @FXML
  private TableColumn<Person, String> emailCol;

  @FXML
  private TableColumn<Person, String> nameCol;

  @FXML
  private TableColumn<Person, String> phoneCol;

  @FXML
  private TableView<Person> tableView;

  public void initialize() {
    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      //Need to use resultSet to iterate through each entry
      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {

        String first = resultSet.getString("firstName").substring(0, 1).toUpperCase() +
          resultSet.getString("firstName").substring(1);
        String last = resultSet.getString("lastName").substring(0, 1).toUpperCase() +
          resultSet.getString("lastName").substring(1);

        String userName = first + " " + last;
        String userAge = resultSet.getString("age");
        String userAddr = resultSet.getString("address");
        String userPhone = resultSet.getString("phone");
        String userEmail = resultSet.getString("email") + ".com";;

        boolean v = resultSet.getBoolean("verified");
        boolean s = resultSet.getBoolean("shown");

        // Set cell value factories for each column
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        addrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        if(Database.domain.equals("@gmail.com"))
        {
          ObservableList<Person> people = FXCollections.observableArrayList(
            new Person(userName, userAge, userAddr, userPhone, userEmail)
          );

          tableView.setItems(people);
        } else {

        }
      }

      preparedStatement.close();
      resultSet.close();

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }
}
