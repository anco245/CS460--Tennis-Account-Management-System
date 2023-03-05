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
				
			//Gets the name of the person who just logged in
			//And adds a greeting
			String str = database.person;
			text.setText("Welcome " + str + "!");
				
			//Makes it so that you can't click away 
			// from this window. 
			window.initModality(Modality.APPLICATION_MODAL);
			
			Button addToDatabase = new Button("Add to database");
			Button viewDirectory = new Button("Look at directory");
			Button editDatabase = new Button("Edit Database");
			
			viewDirectory.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        Stage window = new Stage();
			        Button exit = new Button("Exit");
			        Text text = new Text();
					VBox layout = new VBox(10);
					Scene scene = new Scene(layout, 400, 400);
					
					window.initModality(Modality.APPLICATION_MODAL);
					
					exit.setOnAction(x -> window.close());
					
			        database.all = "";
					
					database.getData("0");
					text.setText(database.all);
					
					layout.getChildren().addAll(text, exit);
					
					window.setScene(scene);
					window.showAndWait();
			    }
			});
			
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
