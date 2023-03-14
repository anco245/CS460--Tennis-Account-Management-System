package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChairTresScreen extends homeScreen{
	public static void display() {
		Stage window = new Stage();
		VBox layout = new VBox(10);
		Scene scene = new Scene(layout, 400, 400);
		
		window.setTitle("Treasurer / Chairman Home Screen");
		
		Button remove = new Button("Remove Account");
		Button approve = new Button("Approve New Accounts");
		Button addEvent = new Button("Add an Event");
		Button notifyPay = new Button("Notify Members of Late Payment / View Directory");
		
		//Approves all accounts waiting to be verified
		//Need to make it so that you can select which ones you want to verify
		approve.setOnAction(e -> database.approve());
		
		layout.getChildren().addAll(text, notifyPay, addEvent, remove, approve, viewInfo, backToLogin);
		
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
	}
		
}
