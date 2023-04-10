package com.example.jecesystem;

//import java.util.jar.Attributes.Name;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MakeResController implements Initializable {

  @FXML
  private ChoiceBox<?> courtNum;

  @FXML
  private CheckBox nineAM;

  ObservableList list = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb)
  {
    loadData();
  }

  private void loadData() {
    list.removeAll(list);

    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;
    int e = 5;
    int f = 6;
    int g = 7;
    int h = 8;
    int i = 9;
    int j = 10;
    int k = 11;
    int l = 12;

    list.addAll(a, b, c, d, e, f, g, h, i, j, k, l);

    //courtNum.getItems().addAll(list);
  }

  @FXML
  void submitreservation(ActionEvent event) {

    //System.out.println(num);
    /*
    if(Database.checkRes(num))
    {
      System.out.println("Court is already reserved");
      //show this in fxml
    } else if (Database.inReservation(name)) {
      System.out.println("You have already reserved a court");
      //show this in fxml
    } else {
      //Database.makeRes(num, name, time);
    }
    */
  }
}
