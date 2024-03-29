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

public class ApproveController implements Initializable {

  @FXML
  private TableColumn<Person, String> address;
  @FXML
  private TableColumn<Person, Integer> age;
  @FXML
  private TableColumn<Person, Button> approve;
  @FXML
  private TableColumn<Person, String> email;
  @FXML
  private TableColumn<Person, String> name;
  @FXML
  private TableColumn<Person, String> password;
  @FXML
  private TableColumn<Person, String> phone;
  @FXML
  private TableColumn<Person, Button> reject;
  @FXML
  private TableColumn<Person, Boolean> shown;
  @FXML
  private TableColumn<Person, String> username;
  @FXML
  private TableView<Person> table;

  ObservableList<Person> list = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    //.setCellValueFactory needed for populating table
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    age.setCellValueFactory(new PropertyValueFactory<>("age"));
    address.setCellValueFactory(new PropertyValueFactory<>("address"));
    phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    username.setCellValueFactory(new PropertyValueFactory<>("user"));
    password.setCellValueFactory(new PropertyValueFactory<>("pass"));
    shown.setCellValueFactory(new PropertyValueFactory<>("shown"));


    approve.setCellValueFactory(new PropertyValueFactory<>("approve"));
    reject.setCellValueFactory(new PropertyValueFactory<>("reject"));

    //Gets each person's information in the directory and makes it into a person object
    try (Connection connection = DriverManager.getConnection(Database.url, Database.username, Database.password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        boolean v = resultSet.getBoolean("verified");

        if (!v) {
          String first = resultSet.getString("firstName").substring(0, 1).toUpperCase() +
            resultSet.getString("firstName").substring(1);
          String last = resultSet.getString("lastName").substring(0, 1).toUpperCase() +
            resultSet.getString("lastName").substring(1);

          String userName = first + " " + last;
          int userAge = resultSet.getInt("age");
          String userAddr = resultSet.getString("address");
          String userPhone = resultSet.getString("phone");
          String userEmail = resultSet.getString("email");
          String userUser = resultSet.getString("username");
          String userPass = resultSet.getString("pword");
          boolean s = resultSet.getBoolean("shown");

          Person person = new Person(userName, userAge, userAddr, userPhone, userEmail, userUser, userPass, s);

          list.add(person);
        }
      }

      preparedStatement.close();
      resultSet.close();
      table.setItems(list);

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @FXML
  void switchToHomescreen(ActionEvent event) throws IOException {
    App.setRoot("adminscreen");
  }
}
