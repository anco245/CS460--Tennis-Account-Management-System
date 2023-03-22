package application;

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

public class MemberScreen extends homeScreen{
	
	//use login.name to get username 
	//display courts and times
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
    	Stage window = new Stage();
		VBox layout = new VBox(10);
		Scene scene = new Scene(layout, 400, 400);
		
		window.setTitle("Member Home Screen");
		
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
				
				window.setTitle("Make Reservation");
				
				window.initModality(Modality.APPLICATION_MODAL);
				
				exit.setOnAction(x -> window.close());
				
				//layout.getChildren().addAll();
				
				window.setScene(scene);
				window.showAndWait();
		    }
		});
		
		layout.getChildren().addAll(text, viewDirectory, makeRes, addGuest, viewInfo, 
				contactUs, backToLogin);
		
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
    }
}
