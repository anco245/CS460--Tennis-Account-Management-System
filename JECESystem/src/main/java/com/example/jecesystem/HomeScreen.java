package com.example.jecesystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeScreen {

  public static Stage homeWindow = new Stage();
  public static VBox homeLayout = new VBox(10);
  public static Scene homeScene = new Scene(homeLayout, 400, 400);
  public static Text welcome = new Text();
  public static Button viewDirectory = new Button("Look at directory");
  public static Button contactUs = new Button("Contact Us");
  public static Button backToLogin = new Button("Back to Login");

  //Doesn't do anything yet
  public static Button viewInfo = new Button("View Personal Information");

  public static void display() {
    try {

      //Gets the name of the person who just logged in
      //And adds a greeting
      String str = Database.person;
      welcome.setText("Welcome " + str + "!");

      backToLogin.setOnAction(e -> {
        homeWindow.close();

        /*
        Login l = new Login();

        Stage window = new Stage();

        l.start(window);
        */

        display();
      });

      viewInfo.setOnAction(e -> {
        Stage window = new Stage();
        Button exit = new Button("Exit");
        Text text = new Text();
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 400, 400);

        window.setTitle("View Info");

        window.initModality(Modality.APPLICATION_MODAL);

        exit.setOnAction(x -> window.close());

        Database.allString = "";
        Database.all.setLength(0);

        Database.getAll();
        text.setText(Database.allString);

        layout.getChildren().addAll(text, exit);

        window.setScene(scene);
        window.showAndWait();
      });

      //Just prints the directory as a messy printed list (not formatted)
      viewDirectory.setOnAction(e -> {
        Stage window = new Stage();
        Button exit = new Button("Exit");
        Text text = new Text();
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 400, 400);

        window.setTitle("View Directory");

        window.initModality(Modality.APPLICATION_MODAL);

        exit.setOnAction(x -> window.close());


        Database.allString = "";
        Database.all.setLength(0);

        Database.getAll();
        text.setText(Database.allString);

        layout.getChildren().addAll(text, exit);

        window.setScene(scene);
        window.showAndWait();
      });

      //Should view directory as a table
//			viewDirectory.setOnAction(new EventHandler<ActionEvent>() {
//			    @Override public void handle(ActionEvent e) {
//			    	Stage primaryStage = new Stage();
//			    	StackPane root = new StackPane();
//
//			    	TableView table = new TableView();
//			    	table.setEditable(true);
//
//			    	TableColumn name = new TableColumn("Name");
//			    	name.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//			    	TableColumn age = new TableColumn("Age");
//			    	TableColumn address = new TableColumn("Address");
//			    	TableColumn email = new TableColumn("Email");
//			    	TableColumn phone = new TableColumn("Phone Number");
//
//			    	name.setPrefWidth(75);
//			    	address.setPrefWidth(200.0);
//			    	email.setPrefWidth(150.0);
//			    	phone.setPrefWidth(100.0);
//
//			    	table.getColumns().addAll(name, age, address, email, phone);
//
//			    	root.getChildren().add(table);
//			    	primaryStage.setScene(new Scene(root, 650, 400));
//			    	primaryStage.show();
//			    }
//			});

      contactUs.setOnAction(e -> {
        Stage window = new Stage();
        Button exit = new Button("Exit");
        Text text = new Text();
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 400, 400);

        window.setTitle("Contact Us");

        window.initModality(Modality.APPLICATION_MODAL);

        exit.setOnAction(x -> window.close());

        String info = """
          Phone Number for Main Desk: 215-436-2231
          Phone Number for Technical Support: 215-663-2133
          Email for any questions: askquestion@tennis.com
          """;

        text.setText(info);

        layout.getChildren().addAll(text, exit);

        window.setScene(scene);
        window.showAndWait();
      });

      //Checks the email domain of the account that's logged in
      //Depending on which account, it should display different
      //functions
      // if you're a treasurer / chairman you'll have an email with '@tennis.com'
      // if you're an administrator, you'll have an email with '@admin.com'
      // if you're a member, it'll be any other extension
      if(Database.domain.equals("tennis.com"))
      {
        ChairTresScreen.display();
      } else if (Database.domain.equals("admin.com")) {
        //AdminScreen.display();
      } else {
      }

    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
