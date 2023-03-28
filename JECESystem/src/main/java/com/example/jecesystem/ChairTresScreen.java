package com.example.jecesystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChairTresScreen extends HomeScreen{

  public static void display() {

    homeWindow.setTitle("Treasurer / Chairman Home Screen");

    Button remove = new Button("Remove Account");
    Button approve = new Button("Approve New Accounts");
    Button addEvent = new Button("Add an Event");
    Button notifyPay = new Button("Notify Members of Late Payment / View Directory");

    //Approves all accounts waiting to be verified
    //Need to make it so that you can select which ones you want to verify
    approve.setOnAction(e -> Database.approve());

    //Just prints the directory as a messy printed list
    notifyPay.setOnAction(e -> {
      Stage window = new Stage();
      Button exit = new Button("Exit");
      Text text = new Text();
      VBox layout = new VBox(10);
      Scene scene = new Scene(layout, 400, 400);

      window.setTitle("View Directory and Notify Members");

      window.initModality(Modality.APPLICATION_MODAL);

      exit.setOnAction(x -> window.close());

      Database.allString = "";

      Database.getAll();
      text.setText(Database.allString);

      layout.getChildren().addAll(text, exit);

      window.setScene(scene);
      window.showAndWait();
    });

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

    homeLayout.getChildren().addAll(welcome, notifyPay, addEvent, remove, approve, viewInfo, backToLogin);

    //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    homeWindow.setScene(homeScene);
    homeWindow.showAndWait();
  }
}
