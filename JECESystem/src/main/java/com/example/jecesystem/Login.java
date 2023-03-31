package com.example.jecesystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application implements EventHandler<ActionEvent>{

  public static String name = "";
  public static String pass = "";
  public static String inputfName = "";
  public static String inputlName = "";
  public static String inputUser = "";
  public static String inputPass = "";
  public static String inputAddr = "";
  public static String inputEmail = "";
  public static String inputPhone = "";
  public static String inputAge = "";
  public static Boolean inputShow = false;

  public void start(Stage primaryStage) {
    try {
      VBox layout = new VBox(10);
      Scene loginScene = new Scene(layout, 400, 400);
      Text text = new Text();
      Stage loginWindow = primaryStage;

      TextField username = new TextField();
      username.setPromptText("Username");
      username.setFocusTraversable(false);

      TextField password = new TextField();
      password.setPromptText("Password");
      password.setFocusTraversable(false);

      Button submit = new Button("Submit");

      submit.setOnAction(e -> {
        //Takes what's in the username and password textfields
        //and puts them into the name and pass variables.
        name = username.getText();
        pass = password.getText();

        //If the username and password match an account
        //in the database then check if that entry is verified
        if(Database.login(name, pass))
        {
          //if verified, show homescreen
          if(Database.verified(name))
          {
            loginWindow.close();

            HomeScreen.display();
          } else {
            text.setText("Your account hasn't been verified by the chairmen yet.");
          }
        } else {
          text.setText("Wrong Username and Password");
        }

        layout.getChildren().add(text);
      });

      Button newAccount = new Button("Make a new Account");
      newAccount.setOnAction(e -> {
        Stage window = new Stage();
        Button submit1 = new Button("Submit and Exit");
        VBox layout1 = new VBox(10);
        Scene scene = new Scene(layout1, 400, 400);

        CheckBox box = new CheckBox("Check this box if you would like to be "
          + "shown in the directory");

        TextField fieldfName = new TextField();
        fieldfName.setPromptText("Enter first name");
        //makes it so that you can see the prompt if hovering over it
        fieldfName.setFocusTraversable(false);

        TextField fieldlName = new TextField();
        fieldlName.setPromptText("Enter last name");
        fieldlName.setFocusTraversable(false);

        TextField fieldAge = new TextField();
        fieldAge.setPromptText("Enter age");
        fieldAge.setFocusTraversable(false);

        TextField fieldEmail = new TextField();
        fieldEmail.setPromptText("Enter e-mail");
        fieldEmail.setFocusTraversable(false);

        TextField fieldUsername = new TextField();
        fieldUsername.setPromptText("Enter username");
        fieldUsername.setFocusTraversable(false);

        TextField fieldPass = new TextField();
        fieldPass.setPromptText("Enter password");
        fieldPass.setFocusTraversable(false);

        TextField fieldAddr = new TextField();
        fieldAddr.setPromptText("Enter address");
        fieldAddr.setFocusTraversable(false);

        TextField fieldPhone = new TextField();
        fieldPhone.setPromptText("Enter phone number");
        fieldPhone.setFocusTraversable(false);

        window.setTitle("New Account");

        //Need to create a function to check if username is
        //already taken. Error message if already taken.
        //Message saying to enter another one

        submit1.setOnAction(x -> {
          inputfName = fieldfName.getText();
          inputlName = fieldlName.getText();
          inputAge = fieldAge.getText();
          inputAddr = fieldAddr.getText();
          inputPhone = fieldPhone.getText();
          inputEmail = fieldEmail.getText();
          inputEmail = inputEmail.substring(0, inputEmail.length() - 4);
          inputUser = fieldUsername.getText();
          inputPass = fieldPass.getText();
          inputShow = box.isSelected();

          Database.nAccount(inputfName, inputlName, inputAge, inputAddr, inputPhone,
            inputEmail, inputUser, inputPass, inputShow);

          window.close();
        });

        layout1.getChildren().addAll(fieldfName, fieldlName, fieldAge, fieldAddr,
          fieldPhone, fieldEmail, fieldUsername, fieldPass, box, submit1);

        window.setScene(scene);
        window.showAndWait();
      });

      layout.getChildren().addAll(username, password, submit, newAccount);

      primaryStage.setTitle("Login");
      primaryStage.setScene(loginScene);
      primaryStage.show();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void handle(ActionEvent primaryStage) {
    // TODO Auto-generated method stub
  }

  //This makes it so that this login class is the first screen you see;
  //what everything else is based on
  public static void main(String[] args) {
    launch(args);
  }


}
