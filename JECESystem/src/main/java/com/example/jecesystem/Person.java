package com.example.jecesystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class Person {

  Alert error = new Alert(Alert.AlertType.ERROR);
  Alert con = new Alert(Alert.AlertType.CONFIRMATION);

  Button notify = new Button();
  Button denotify = new Button();
  Button approve = new Button();
  Button reject = new Button();
  Button cancel = new Button();
  Button reserve = new Button();

  ChoiceBox guests = new ChoiceBox();

  ChoiceBox singleDouble = new ChoiceBox();

  String userName = "";
  String userAddress = "";
  String userPhone = "";
  String userEmail = "";
  String userUser = "";
  String userPass = "";
  boolean isShown = false;
  boolean isLate = false;
  boolean penalized = false;
  boolean keep = true;
  int userAge = 0;
  int owe = 0;
  String status = "";
  int userCourt = 1;
  String date = "";

  ObservableList guest = FXCollections.observableArrayList();
  ObservableList sd = FXCollections.observableArrayList();

  public void loadData() {
    guest.addAll(0, 1, 2, 3);
    guests.getItems().addAll(guest);

    sd.addAll("Single", "Double");
    singleDouble.getItems().addAll(sd);
  }

  public Person(String name, int age, String address, String phone, String email) {
    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
  }

  public Person(String slot, String s, int c) {

    loadData();

    this.date = slot;
    this.status = s;
    this.userCourt = c;

    String cNumber = "court" + c;
    reserve.setPrefWidth(80.0);

    this.reserve.setOnAction(e -> {
      try {
        if (Database.sameTimeOtherCourt(Database.memberUser, slot, cNumber)) {
          error.setTitle("Error");
          error.setContentText("You've already reserved another court at this time");
          error.showAndWait();
        } else if (Database.exceededResLimit(slot)) {
          error.setTitle("Error");
          error.setContentText("You can only reserve 2 courts for any day.\nTry another day.");
          error.showAndWait();
        } else if (Database.hasPassed(slot)) {
          error.setTitle("Error");
          error.setContentText("That time slot has already passed.\nTry another.");
          error.showAndWait();
        } else if (Database.available(cNumber, slot)) {
          error.setTitle("Error");
          error.setContentText("That timeslot is not available.\nTry another.");
          error.showAndWait();
        } else if (guests.getValue() != null && singleDouble == null) {
          error.setTitle("Error");
          error.setContentText("You need to pick either single or double.");
          error.showAndWait();
        } else if (guests.getValue() != null && singleDouble != null &&
                  singleDouble.getValue().toString().equals("Double") &&
                  Integer.parseInt(guests.getValue().toString()) == 1) {
          error.setTitle("Error");
          error.setContentText("Not enough players for double");
          error.showAndWait();
        } else {
          con.setTitle("Confirm");

          if(singleDouble.getValue() != null)
          {
            if(guests.getValue() != null)
            {
              con.setContentText("You will be reserving a " + singleDouble.getValue().toString()
                + " game for Court " + c + " at\n" + slot +
                "\nYou'll be bringing " + guests.getValue() + " guests, which will cost $" +
                Integer.parseInt(guests.getValue().toString()) * 10 +
                "\nPress ok to continue.");
            } else {
              con.setContentText("You will be reserving a " + singleDouble.getValue().toString()
                + " game for Court " + c + " at\n" + slot +
                "\nPress ok to continue.");
            }
          } else {
            con.setContentText("You will be reserving a timeslot for Court " + c + " at\n" + slot +
              "\nPress ok to continue.");
          }

          Optional<ButtonType> result = con.showAndWait();
          if (result.isPresent() && result.get() == ButtonType.OK) {

            if(guests.getValue() != null)
            {
              Database.addSubGuests(Integer.parseInt(guests.getValue().toString()));
              Database.addSubOwe(Database.memberUser, Integer.parseInt(guests.getValue().toString()) * 10);
              Database.makeRes(cNumber, Database.memberUser, date, 1 + Integer.parseInt(guests.getValue().toString()));
            } else {
              Database.makeRes(cNumber, Database.memberUser, date, 1);
            }

            try {
              App.setRoot(cNumber);
            } catch (IOException ex) {
              throw new RuntimeException(ex);
            }
          }
        }
      } catch (RuntimeException ex) {

      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  public Person(int court, String dateTime) {
    this.userCourt = court;
    this.date = dateTime;

    cancel.setPrefWidth(100.0);
    this.cancel.setOnAction(e -> {
      Database.cancelReservation(this.userCourt, this.date);
      try {
        App.setRoot("info");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  public Person(String name, int age, String address, String phone, String email, boolean s,
                boolean l, boolean p, int o, String user, String pass, boolean k) {

    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
    this.isShown = s;
    this.isLate = l;
    this.userUser = user;
    this.userPass = pass;
    this.notify = new Button("notify");
    this.denotify = new Button("denotify");
    this.penalized = p;
    this.keep = k;
    this.owe = o;

    notify.setPrefWidth(100.0);
    notify.setOnAction(e -> {
      try {
        Database.setLate(userUser, true);
        App.setRoot("chairdirectory");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    denotify.setPrefWidth(100.0);
    denotify.setOnAction(e -> {
      try {
        Database.setLate(userUser, false);
        App.setRoot("chairdirectory");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  public Person(String name, int age, String address, String phone, String email, String u, String p, boolean s) {

    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
    this.userUser = u;
    this.userPass = p;
    this.isShown = s;

    approve.setPrefWidth(100.0);
    approve.setOnAction(e -> {
      try {
        Database.setApprove(userUser);
        App.setRoot("approve");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    reject.setPrefWidth(100.0);
    reject.setOnAction(e -> {
      try {
        Database.deleteFromDb(userUser, "directory");
        App.setRoot("approve");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  public void setGuests(ChoiceBox g) {guests = g;}
  public ChoiceBox getGuests() {return guests;}

  public void setSingleDouble(ChoiceBox x) {singleDouble = x;}
  public ChoiceBox getSingleDouble() {return singleDouble;}

  public void setStatus(String s) {status = s;}
  public String getStatus() {return status;}

  public void setCourt(int court) {userCourt = court;}
  public int getCourt() {return userCourt;}

  public void setDate(String d) {date = d;}
  public String getDate() {return this.date;}

  public void setName(String name) {
    userName = name;
  }
  public String getName() { return userName; }

  public void setAge(int age) {
    userAge = age;
  }
  public int getAge() { return userAge; }

  public void setAddress(String address) {
    userAddress = address;
  }
  public String getAddress() { return userAddress; }

  public void setPhone(String phone) {
    userPhone = phone;
  }
  public String getPhone() { return userPhone; }

  public void setEmail(String email) { userEmail = email; }
  public String getEmail() { return userEmail; }

  public void setShown(boolean s) { isShown = s; }
  public boolean getShown() { return isShown; }

  public void setLate(boolean l) { isLate = l; }
  public boolean getLate() { return isLate; }

  public void setUser(String u) { userUser = u; }
  public String getUser() { return userUser; }

  public void setPass(String p) { userPass = p; }
  public String getPass() { return userPass; }

  public void setPenalized(boolean p) { penalized = p; }
  public boolean getPenalized() { return penalized; }

  public void setKeep(boolean k) { keep = k; }
  public boolean getKeep() { return keep; }

  public void setOwe(int o) { owe = o; }
  public int getOwe() { return owe; }

  public void setNotify(Button b) {
    this.notify = b;
    this.notify.setOnAction(e -> Database.setLate(this.userUser, true));
  }

  public Button getCancel() { return cancel; }
  public void setCancel(Button b) {
    this.cancel = b;
    this.cancel.setOnAction(e -> Database.cancelReservation(this.userCourt, this.date));
  }

  public Button getReserve() { return reserve; }
  public void setReserve(Button b) {
    this.reserve = b;
  }
  public Button getNotify() { return notify; }

  public void setDenotify(Button b) {
    this.denotify = b;
    this.denotify.setOnAction(e -> Database.setLate(this.userUser, false));
  }
  public Button getDenotify() { return denotify; }

  public void setApprove(Button b) {
    this.approve = b;
    this.approve.setOnAction(e -> Database.setApprove(this.userUser));
  }
  public Button getApprove() { return approve; }

  public void setReject(Button b) {
    this.reject = b;
    this.reject.setOnAction(e -> {
      Database.deleteFromDb(this.userUser, "directory");
      Database.addFromWait();
    });
  }
  public Button getReject() { return reject; }
}
