package com.example.jecesystem;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class Person {

  Button notify = new Button();
  Button denotify = new Button();
  Button approve = new Button();
  Button reject = new Button();
  Button cancel = new Button();
  Button reserve = new Button();

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

  int numOfGuest = 0;

  Alert error = new Alert(Alert.AlertType.ERROR);

  public Person(String name, int age, String address, String phone, String email) {
    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
  }

  public Person(String slot, String s, int c, int g) {

    this.date = slot;
    this.status = s;
    this.userCourt = c;
    this.numOfGuest = g;

    String cNumber = "court" + c;

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
        } else if (Database.available("court1", slot)) {
          error.setTitle("Error");
          error.setContentText("That timeslot is not available.\nTry another.");
          error.showAndWait();
        } else {
          Database.con.setTitle("Confirm");
          Database.con.setContentText("You will be reserving a timeslot for Court " + c + " at\n" + slot +
            "\n Press ok to continue.");

          Optional<ButtonType> result = Database.con.showAndWait();
          if (result.isPresent() && result.get() == ButtonType.OK) {
            Database.makeRes(cNumber, Database.memberUser, date, 1);
            try {
              App.setRoot("court1");
            } catch (IOException ex) {
              throw new RuntimeException(ex);
            }
          }
        }
      } catch (RuntimeException ex) {
        throw ex;
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }

    });

    /*
     else if (dayOfWeek.getValue().equals("Today") && isToday(time) ) {
      error.setTitle("Error");
      error.setContentText("That time slot has already passed.\nTry another.");
      error.showAndWait();
    }
    */
  }

  public Person(int court, String dateTime) {
    this.userCourt = court;
    this.date = dateTime;

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

    notify.setOnAction(e -> {
      try {
        Database.setLate(userUser, true);
        App.setRoot("chairdirectory");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

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

    approve.setOnAction(e -> {
      try {
        Database.setApprove(userUser);
        App.setRoot("approve");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    reject.setOnAction(e -> {
      try {
        Database.deleteFromDb(userUser, "directory");
        App.setRoot("approve");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

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
