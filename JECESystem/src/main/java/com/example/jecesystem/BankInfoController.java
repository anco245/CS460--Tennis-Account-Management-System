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

public class BankInfoController implements Initializable {

  @FXML
  private TableColumn<Person, String> accountNum;

  @FXML
  private TableColumn<Person, String> accountType;

  @FXML
  private TableColumn<Person, String> bankName;

  @FXML
  private TableColumn<Person, String> name;

  @FXML
  private TableView<Person> table;

  ObservableList<Person> list = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb) {

    name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    bankName.setCellValueFactory(new PropertyValueFactory<>("bankName"));
    accountType.setCellValueFactory(new PropertyValueFactory<>("accountType"));
    accountNum.setCellValueFactory(new PropertyValueFactory<>("accountNum"));

    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM bank");

      ResultSet resultSet = preparedStatement.executeQuery();

      String full;
      String bName;
      String aNum;
      String aType;

      while(resultSet.next()) {

        full = Database.getName(resultSet.getString("username"));
        bName = resultSet.getString("bankName");
        aNum = resultSet.getString("accountNum");
        aType = resultSet.getString("accountType");

        Person person = new Person(full, bName, aNum, aType);

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
