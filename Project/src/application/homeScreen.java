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
	//Need to make this a global variable, because btn is 
		//used in both the start and handle methods
		public static Button btn1;
		public static Button btn2;
		public static Button btn3;
		public static Button btn4;
		//Button btn4;
		
		public static void display() {
			try {
				
				Stage window = new Stage();
				Text text = new Text();
				VBox layout = new VBox(10);
				Scene scene = new Scene(layout, 400, 400);	
				
				String str = database.person;
				
				text.setText("Welcome " + str + "!");
				
				window.initModality(Modality.APPLICATION_MODAL);
				
				/*
				btn1 = new Button("Click");
				root.getChildren().add(btn1);
				btn1.setTranslateY(-50.0);
				*/
				
				btn1 = new Button("Add to database");
				btn2 = new Button("Look at directory");
				btn3 = new Button("Edit Database");
				btn4 = new Button("Look at information");
				
				//using "this" because the handle method is in this class
				//otherwise you would put the class where the handle 
				//method is. Whenever the button is clicked, "handle" is
				//called.
				//btn.setOnAction(this);
				
				//btn1.setOnAction(e -> AlertBox.display("Error", "There was an error!"));
				btn1.setOnAction(e -> database.display(1));
				btn2.setOnAction(e -> database.display(2));
				btn3.setOnAction(e -> database.display(3));
				btn4.setOnAction(e -> database.display(2));
				
				System.out.println(database.domain);
				
				if(database.domain.equals("tennis"))
				{
					window.setTitle("Treasurer / Chairman Home Screen");
					layout.getChildren().addAll(text, btn1, btn2, btn3);
				} else if (database.domain.equals("admin")) {
					window.setTitle("Administrator Home Screen");
					layout.getChildren().addAll(text);
				} else {
					window.setTitle("Member Home Screen");
					layout.getChildren().addAll(text, btn2, btn4);
				}
				
				//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				window.setScene(scene);
				window.showAndWait();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
}
