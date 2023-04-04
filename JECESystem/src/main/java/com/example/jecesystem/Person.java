package com.example.jecesystem;

public class Person {

  private String userName = "";
  private String userAge = "";
  private String userAddr = "";
  private String userPhone = "";
  private String userEmail = "";
  private boolean isVerified = false;
  private boolean isShown = false;

  public Person(String name, String age, String addr, String phone, String email) {

    userName = name;
    userAge = age;
    userAddr = addr;
    userPhone = phone;
    userEmail = email;
  }

  public Person(String name, String age, String addr, String phone, String email, boolean v, boolean s) {

    userName = name;
    userAge = age;
    userAddr = addr;
    userPhone = phone;
    userEmail = email;
    isVerified = v;
    isShown = s;
  }

  public void setName(String name) {
    userName = name;
  }

  public void setAge(String age) {
    userAge = age;
  }

  public void setAddr(String addr) {
    userAddr = addr;
  }

  public void setPhone(String phone) {
    userPhone = phone;
  }

  public void setEmail(String email) {
    userEmail = email;
  }



}
