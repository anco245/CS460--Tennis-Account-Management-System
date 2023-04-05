package com.example.jecesystem;

public class Person {

  String userName = "";
  String userAge = "";
  String userAddress = "";
  String userPhone = "";
  String userEmail = "";
  boolean isVerified = false;
  boolean isShown = false;

  public Person(String name, String age, String address, String phone, String email) {

    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
  }

  public Person(String name, String age, String address, String phone, String email, boolean v, boolean s) {

    this.userName = name;
    this.userAge = age;
    this.userAddress = address;
    this.userPhone = phone;
    this.userEmail = email;
    this.isVerified = v;
    this.isShown = s;
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



}
