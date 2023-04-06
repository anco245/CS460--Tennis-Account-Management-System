package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CTController implements Initializable {

  @FXML
  private Text welcome;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    String welcomeMessage = "Welcome " + Database.fName + " " + Database.lName + "!";
    welcome.setText(welcomeMessage);
  }

  @FXML
  void addEvent(ActionEvent event) {
    //App.setRoot("addevent");
  }

  @FXML
  void approve(ActionEvent event) {
    //App.setRoot("approve");
  }

  @FXML
  void remove(ActionEvent event) throws IOException {
    //App.setRoot("remove");
  }

  @FXML
  void showTresDir(ActionEvent event) throws IOException {
    App.setRoot("chairdirectory");
  }

  @FXML
  void switchToLogin(ActionEvent event) throws IOException {
    App.setRoot("login");
  }

  @FXML
  void viewInfo(ActionEvent event) throws IOException {
    //App.setRoot("directory");
  }

  /*
  remove.setOnAction(e -> {
      Stage window = new Stage();
      Text text = new Text();
      VBox layout = new VBox(10);
      Scene scene = new Scene(layout, 400, 400);

      window.initModality(Modality.APPLICATION_MODAL);
      window.setTitle("Remove Member From Database");

      TextField username = new TextField();
      username.setPromptText("Enter Account's Username");
      username.setFocusTraversable(false);

      Button submit = new Button("Submit");

      submit.setOnAction(x -> {
        String name = username.getText();

        if (Database.inDatabase(name)) {
          Database.delete(name);
          window.close();
        } else {
          text.setText("This username doesn't exist in this database\nTry again");
        }
      });

      layout.getChildren().addAll(username, text, submit);

      window.setScene(scene);
      window.showAndWait();
    });
   */

}
