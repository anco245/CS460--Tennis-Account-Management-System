package com.example.jecesystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.canvas.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MemberScreen extends HomeScreen{

  public static Text lateFeeError = new Text();


  //use login.name to get username
  //display courts and times
  //maybe in two panels, left court, right times
  //When user presses on a specific time (as a button), username is placed in database
  public void makeReservation(){

  }
  public void cancelReservation(){

  }
  public void cancelMembership(){

  }
  public void addGuest(){

  }
  public void financialInformation(){

  }
  public void viewClubHours(){

  }

  public static void display() {
    homeWindow.setTitle("Member Home Screen");

    Button makeRes = new Button("Make a Reservation");
    Button addGuest = new Button("Add Guest");

    makeRes.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
        Stage window = new Stage();
        Button exit = new Button("Exit");
        Text text = new Text();
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 400, 400);

        window.setTitle("Make Reservation");

        window.initModality(Modality.APPLICATION_MODAL);

        exit.setOnAction(x -> window.close());


        //layout.getChildren().addAll();

        window.setScene(scene);
        window.showAndWait();
      }
    });

    addGuest.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
        Stage window = new Stage();
        Button exit = new Button("Exit");
        Text text = new Text();
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 400, 400);

        window.setTitle("add a guest");

        window.initModality(Modality.APPLICATION_MODAL);

        exit.setOnAction(x -> window.close());

        //layout.getChildren().addAll();

        window.setScene(scene);
        window.showAndWait();
      }
    });

    homeLayout.getChildren().addAll(welcome, lateFeeError, viewDirectory, makeRes, addGuest, viewInfo,
      contactUs, backToLogin);

    //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    homeWindow.setScene(homeScene);
    homeWindow.showAndWait();
  }
}
