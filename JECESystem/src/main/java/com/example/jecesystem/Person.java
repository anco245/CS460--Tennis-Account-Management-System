package com.example.jecesystem;

import javafx.scene.control.Button;

public class Person {

  String userName = "";
  String userAge = "";
  String userAddress = "";
  String userPhone = "";
  String userEmail = "";
  String userUser = "";
  String userPass = "";

  //boolean isVerified = false;
  boolean isShown = false;
  boolean isLate = false;

  Button notify = new Button();

  public Person(String name, String age, String address, String phone, String email) {

    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
  }

  public Person(String name, String age, String address, String phone, String email, boolean s, boolean l, String user, String pass) {

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

    notify.setOnAction(e -> Database.makeLate(userUser));
  }

  public void setName(String name) {
    userName = name;
  }
  public String getName() { return userName; }

  public void setAge(String age) {
    userAge = age;
  }
  public String getAge() { return userAge; }

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

  public void setNotify(Button b) {
    this.notify = b;
    this.notify.setOnAction(e -> Database.makeLate(this.userUser));
  }
  public Button getNotify() { return notify; }

  public void setUser(String u) { userUser = u; }
  public String getUser() { return userUser; }

  public void setPass(String p) { userPass = p; }
  public String getPass() { return userPass; }

}
