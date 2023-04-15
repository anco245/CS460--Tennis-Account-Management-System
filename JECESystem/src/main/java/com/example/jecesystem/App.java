package com.example.jecesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

  //For start method
  private static Scene scene;

  @Override
  public void start(Stage stage) throws IOException {

    //checks to see if the court tables have been populated or not
    //if not, days monday - sunday from times 9-630 are inserted
    if(!Database.beenPopulated())
    {
      Database.populateCourts();
    }

    scene = new Scene(loadFXML("login"), 1280, 720);
    stage.setScene(scene);
    stage.show();
  }

  static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args){launch();}
}
