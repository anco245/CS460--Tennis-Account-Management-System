package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class homeScreen {
	public static void display() {
		try {
				
			Stage window = new Stage();
			Text text = new Text();
			VBox layout = new VBox(10);
			Scene scene = new Scene(layout, 400, 400);	
				
			Button addToDatabase;
			Button viewDirectory;
			Button editDatabase;
				
			//Gets the name of the person who just logged in
			//And adds a greeting
			String str = database.person;
			text.setText("Welcome " + str + "!");
				
			//Makes it so that you can't click away 
			// from this window. 
			window.initModality(Modality.APPLICATION_MODAL);
				
			addToDatabase = new Button("Add to database");
			viewDirectory = new Button("Look at directory");
			editDatabase = new Button("Edit Database");
				
			viewDirectory.setOnAction(e -> database.display(1));
			editDatabase.setOnAction(e -> database.display(2));
				
			System.out.println(database.domain);
				
			//Checks the email domain of the account that's logged in
			//Depending on which account, it should display different
			//functions
			if(database.domain.equals("tennis"))
			{
				window.setTitle("Treasurer / Chairman Home Screen");
				layout.getChildren().addAll(text, addToDatabase, viewDirectory, editDatabase);
			} else if (database.domain.equals("admin")) {
				window.setTitle("Administrator Home Screen");
				layout.getChildren().addAll(text);
			} else {
				window.setTitle("Member Home Screen");
				layout.getChildren().addAll(text, viewDirectory);
			}
				
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
