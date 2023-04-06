package com.example.jecesystem;

import java.sql.*;

public class Database {

  public static String domain = "";

  //all and allString are used to hold strings from database in variables
  public static StringBuilder all = new StringBuilder();
  public static String allString;

  public static String person = "";
  public static String username = "root";
  public static String password = "sqlpass";
  public static String url = "jdbc:mysql://localhost:3306/courtsystem";

  public static String age = "";
  public static String addr = "";
  public static String phone = "";
  public static String email = "";
  public static boolean isShown = false;
  public static boolean verified = false;
  public static boolean isLate = false;


  //If we need to edit data in the database, we'll need to overload this method for types datetime, int,
  // and string, because this should be used for each piece of information in the database.
  //This is just used for boolean values, for example for "verified" and "approved"
  public static void edit(String toUpdate, boolean updateValue, String key)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void makeLate(String user)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET late = ? WHERE username = ?");

      preparedStatement.setBoolean(1, true);
      preparedStatement.setString(2, user);

      preparedStatement.executeUpdate();

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //checks to see if a given username exists in the database
  public static boolean inDatabase (String user)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        String uname = resultSet.getString("username");

        if(uname.equals(user))
        {
          preparedStatement.close();
          resultSet.close();
          connection.close();

          return true;
        }
      }

      preparedStatement.close();
      resultSet.close();
      connection.close();

      return false;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }


  //I just have it so that when pressed, it will approve all accounts
  //Need to make it so that you can select which ones you want to approve
  public static void approve(String u)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        String uname = resultSet.getString("username");
        boolean v = resultSet.getBoolean("verified");

        if(uname.equals(u))
        {
          PreparedStatement p2 =
            connection.prepareStatement("UPDATE directory SET verified = ? WHERE username = ?");

          p2.setBoolean(1, true);
          p2.setString(2, uname);

          p2.executeUpdate();
          p2.close();
        }
      }

      preparedStatement.close();
      resultSet.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Checks if account has been verified
  public static boolean verified(String u)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT verified FROM directory WHERE username = ?");

      preparedStatement.setString(1, u);

      ResultSet resultSet = preparedStatement.executeQuery();

      boolean v = false;

      if(resultSet.next())
        v = resultSet.getBoolean("verified");

      preparedStatement.close();
      resultSet.close();
      connection.close();

      return v;

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }


  //Removes a person's information from the database
  public static void delete(String u)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("DELETE FROM directory WHERE username = ?");

      preparedStatement.setString(1, u);

      preparedStatement.executeUpdate();
      preparedStatement.close();

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Gets all the data from each account, and puts it into a string
  public static void getAll()
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }


  //Creates a new Account
  public static void nAccount(String fname, String lname, String age, String addr,
                              String phone, String email, String u, String p, boolean sho) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("INSERT INTO directory "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

      //Just makes the first letter of the person's first and last name capital
      String first = fname.substring(0, 1).toUpperCase() + fname.substring(1);
      String last = lname.substring(0, 1).toUpperCase() + lname.substring(1);

      preparedStatement.setString(1, first);
      preparedStatement.setString(2, last);
      preparedStatement.setString(3, age);
      preparedStatement.setString(4, addr);
      preparedStatement.setString(5, phone);
      preparedStatement.setString(6, email);
      preparedStatement.setString(7, u);
      preparedStatement.setString(8, p);
      preparedStatement.setBoolean(9, false);
      preparedStatement.setBoolean(10, sho);
      preparedStatement.setBoolean(11, false);

      preparedStatement.executeUpdate();
      preparedStatement.close();

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }


  //Loops through the database to find where both username and password are used together
  //Also saves the domain of their email, to later determine which type of account this is
  public static boolean login(String user, String pass)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        String uname = resultSet.getString("username");
        String pword = resultSet.getString("pword");

        if(uname.equals(user) && pword.equals(pass))
        {
          String em = resultSet.getString("email");

          //Used for extracting the domain from the given email in the database
          //will give back @gmail.com, @admin.com, @tennis.com
          domain = em.substring(em.lastIndexOf("@") + 1);

          isLate = resultSet.getBoolean("late");

          //makes it so that the first letters of the first and last name are capital
          String first = resultSet.getString("firstName").substring(0, 1).toUpperCase() +
            resultSet.getString("firstName").substring(1);
          String last = resultSet.getString("lastName").substring(0, 1).toUpperCase() +
            resultSet.getString("lastName").substring(1);

          boolean latePay = resultSet.getBoolean("late");

          person = first + " " + last;

          return true;
        }
      }
      preparedStatement.close();
      resultSet.close();
      connection.close();

      return false;

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }
}
