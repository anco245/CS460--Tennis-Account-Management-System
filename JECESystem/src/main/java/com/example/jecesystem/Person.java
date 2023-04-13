package com.example.jecesystem;

import javafx.scene.control.Button;

import java.io.IOException;

public class Person {

  Button notify = new Button();
  Button denotify = new Button();
  Button approve = new Button();
  Button reject = new Button();
  Button cancel = new Button();

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

  int userCourt = 1;
  String date = "";

  public Person(String name, int age, String address, String phone, String email) {
    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
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
        Database.late(userUser, true);
        App.setRoot("chairdirectory");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    denotify.setOnAction(e -> {
      try {
        Database.late(userUser, false);
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
        Database.approve(userUser);
        App.setRoot("approve");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    reject.setOnAction(e -> {
      try {
        Database.deleteFromRes(userUser);
        App.setRoot("approve");
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

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
    this.notify.setOnAction(e -> Database.late(this.userUser, true));
  }
  public Button getCancel() { return cancel; }


  public void setCancel(Button b) {
    this.cancel = b;
    this.cancel.setOnAction(e -> Database.cancelReservation(this.userCourt, this.date));
  }
  public Button getNotify() { return notify; }

  public void setDenotify(Button b) {
    this.denotify = b;
    this.denotify.setOnAction(e -> Database.late(this.userUser, false));
  }
  public Button getDenotify() { return denotify; }

  public void setApprove(Button b) {
    this.approve = b;
    this.approve.setOnAction(e -> Database.approve(this.userUser));
  }
  public Button getApprove() { return approve; }

  public void setReject(Button b) {
    this.reject = b;
    this.reject.setOnAction(e -> {
      Database.deleteFromDir(this.userUser);
      Database.addFromWait();
    });
  }
  public Button getReject() { return reject; }
}
