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
			
			Button viewDirectory = new Button("Look at directory");
			Button contactUs = new Button("Contact Us");
			Button exit = new Button("Exit");
			
			//Buttons don't do anything yet
			Button approve = new Button("Approve New Accounts");
			Button backToLogin = new Button("Back to Login");
			Button makeRes = new Button("Make a Reservation");
			Button addGuest = new Button("Add Guest");
			Button remove = new Button("Remove Account");
			Button notifyPay = new Button("Notify Members of Late Payment / View Directory");
			Button viewInfo = new Button("View Peronal Information");
			Button checkUpdates = new Button("Check for Updates");
			Button addEvent = new Button("Add an Event");
			
			//Somehow need to show the login screen
			backToLogin.setOnAction(e -> {
				
			});
			
			approve.setOnAction(e -> database.approve());
			
			viewDirectory.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        Stage window = new Stage();
			        Button exit = new Button("Exit");
			        Text text = new Text();
					VBox layout = new VBox(10);
					Scene scene = new Scene(layout, 400, 400);
					
					window.setTitle("View Directory");
					
					window.initModality(Modality.APPLICATION_MODAL);
					
					exit.setOnAction(x -> window.close());
					
			        database.all = "";
					
					database.getAll();
					text.setText(database.all);
					
					layout.getChildren().addAll(text, exit);
					
					window.setScene(scene);
					window.showAndWait();
			    }
			});
			
			contactUs.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        Stage window = new Stage();
			        Button exit = new Button("Exit");
			        Text text = new Text();
					VBox layout = new VBox(10);
					Scene scene = new Scene(layout, 400, 400);
					
					window.setTitle("Contact Us");
					
					window.initModality(Modality.APPLICATION_MODAL);
					
					exit.setOnAction(x -> window.close());
					
			        String info = "Phone Number for Main Desk: 215-436-2231\n"
			        		    + "Phone Number for Technical Support: 215-663-2133\n"
			        		    + "Email for any questions: askquestion@tennis.com\n";
	
					text.setText(info);
					
					layout.getChildren().addAll(text, exit);
					
					window.setScene(scene);
					window.showAndWait();
			    }
			});	
				
			//Checks the email domain of the account that's logged in
			//Depending on which account, it should display different
			//functions
			// if you're a tresurer / chairman you'll have an email with '@tennis.com'
			// if you're an administrator, you'll have an email with '@admin.com'
			// if you're a member, it'll be any other extension
			if(database.domain.equals("tennis.com"))
			{
				window.setTitle("Treasurer / Chairman Home Screen");
				layout.getChildren().addAll(text, notifyPay, addEvent, remove, approve, viewInfo, 
						backToLogin);
				
			} else if (database.domain.equals("admin.com")) {
				window.setTitle("Administrator Home Screen");
				layout.getChildren().addAll(text, checkUpdates, backToLogin);
				
			} else {
				window.setTitle("Member Home Screen");
				layout.getChildren().addAll(text, viewDirectory, makeRes, addGuest, viewInfo, contactUs, 
						backToLogin);
			}
				
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.showAndWait();
				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
