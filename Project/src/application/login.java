package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class login extends Application implements EventHandler<ActionEvent>{
	
	Stage window;
	public static String name = "";
	public static String pass = "";
	
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			VBox layout = new VBox(10);
			Scene scene = new Scene(layout, 400, 400);
			Text text = new Text();
			
			window = primaryStage;
			
			TextField username = new TextField();
			username.setPromptText("Username");
			username.setFocusTraversable(false);
			
			TextField password = new TextField();
			password.setPromptText("Password");
			password.setFocusTraversable(false);
			
			Button submit = new Button("Submit");
			
			//text.setText("You need to enter both a username and password");
			
			submit.setOnAction(e -> {
				name = username.getText();
				pass = password.getText();
							
				if(database.login(name, pass))
				{
					window.close();
					homeScreen.display();
				} else {
					text.setText("Wrong Username and Password");
				}
				
				layout.getChildren().add(text);
			});
			
			Button newAccount = new Button("Make a new Account");
			newAccount.setOnAction(e -> database.display(4));
			
			Button resetPass = new Button("Reset password");
			resetPass.setOnAction(e -> database.display(0));
			
			layout.getChildren().addAll(username, password, submit, newAccount, resetPass);
			
			primaryStage.setTitle("Login");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handle(ActionEvent primaryStage) {
		// TODO Auto-generated method stub
		homeScreen.display();
	}
	
	//This makes it the first screen that you see; what everything else is based on
	public static void main(String[] args) {
		launch(args);
	}

}
