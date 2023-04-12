package com.example.jecesystem;

//import java.util.jar.Attributes.Name;

//import javafx.fxml.FXML;
import java.time.LocalDateTime;
import java.sql.Timestamp;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.TextField;

//import java.io.IOException;
//import java.util.Optional;

public class MakeResController {

    void onSubmit(String name, int num, LocalDateTime time){


        if(Database.checkRes(num))
        {
            System.out.println("Court is already reserved");
            //show this in fxml
            //error.setTitle("Reservation failed");
            //error.setContentText("Court is already reserved");
            //error.showAndWait();

        }
        else if (Database.inReservation(name)) 
        {
            System.out.println("You have already reserved a court");
            //error.setTitle("Reservation failed");
            //error.setContentText("You have already reserved a court"+"try again");
            //error.showAndWait();
            //show this in fxml
        } 
        else
        {
            Timestamp newtime = Timestamp.valueOf(time);
            Database.makeRes(num, name, newtime);
        }
    }
}