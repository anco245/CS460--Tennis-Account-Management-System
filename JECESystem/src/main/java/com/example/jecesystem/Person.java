package com.example.jecesystem;

public class Person {

  String userName = "";
  String userAge = "";
  String userAddress = "";
  String userPhone = "";
  String userEmail = "";

  //boolean isVerified = false;
  boolean isShown = false;
  boolean isLate = false;

  public Person(String name, String age, String address, String phone, String email) {

    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
  }

  public Person(String name, String age, String address, String phone, String email, boolean s, boolean l) {

    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
    this.isShown = s;
    this.isLate = l;
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

}
