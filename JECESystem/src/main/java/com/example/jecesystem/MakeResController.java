package com.example.jecesystem;

import java.util.jar.Attributes.Name;

import javafx.fxml.FXML;
import java.time.LocalDateTime;

public class MakeResController {

    void onSubmit(String name, int num, LocalDateTime time){


        if(Database.checkRes(num))
        {
            System.out.println("Court is already reserved");
            //show this in fxml
        }
        else if (Database.inReservation(name)) 
        {
            System.out.println("You have already reserved a court");
            //show this in fxml
        } 
        else
        {
            Database.makeRes(num, name, time);
        }
    }
}