package com.example.jecesystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class Database {

  //For accessing the database
  public static String username = "root";
  public static String password = "sqlpass";
  public static String url = "jdbc:mysql://localhost:3306/courtsystem";

  //To hold current user's information
  public static String fName = "";
  public static String lName = "";
  public static String domain = "";
  public static int age = 0;
  public static String addr = "";
  public static String phone = "";
  public static String email = "";
  public static String memberUser = "";
  public static String memberPass = "";
  public static boolean isShown = false;
  public static boolean verified = false;
  public static boolean isLate = false;
  public static int owe = 0;
  public static int guests = 0;


  //Resets the database to initial values
  public static void reset()
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      // Read the SQL script file
      //need to change on your own system
      FileReader fileReader = new FileReader("C:\\Users\\johnc\\OneDrive\\Documents\\GitHub\\CS460-Project\\SQL\\createDatabase.sql");
      FileReader fileReader2 = new FileReader("C:\\Users\\johnc\\OneDrive\\Documents\\GitHub\\CS460-Project\\SQL\\insertValues.sql");

      // Create a Statement object from the database connection
      Statement statement = connection.createStatement();

      // Execute the SQL statements in the script
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {

        //if current line is blank, skip it,
        //otherwise will get cannot read empty statement error
        if(!line.equals("")) {
          statement.execute(line);
        }
      }

      BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
      while ((line = bufferedReader2.readLine()) != null) {

        //if current line is blank, skip it,
        //otherwise will get cannot read empty statement error
        if(!line.equals("")) {
          statement.execute(line);
        }
      }

      // Close the Statement and database connection
      statement.close();
      connection.close();

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void addSubGuests(int num) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      System.out.println(memberUser);
      System.out.println(num);

      while(resultSet.next()) {
        String uname = resultSet.getString("username");
        guests = resultSet.getInt("guests");

        if(uname.equals(memberUser))
        {
          PreparedStatement p2 =
            connection.prepareStatement("UPDATE directory SET guests = ? WHERE username = ?");

          guests = guests + num;

          System.out.println(guests);

          p2.setInt(1, guests);
          p2.setString(2, memberUser);

          p2.executeUpdate();
          p2.close();
        }
      }

      preparedStatement.close();
      resultSet.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //adds or subtracts amount owed
  public static void addSubOwe(String user, int amount) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        String uname = resultSet.getString("username");
        owe = resultSet.getInt("owe");

        if(uname.equals(user))
        {
          PreparedStatement p2 =
            connection.prepareStatement("UPDATE directory SET owe = ? WHERE username = ?");

          owe = owe + amount;

          p2.setInt(1, owe);
          p2.setString(2, memberUser);

          p2.executeUpdate();
          p2.close();
        }
      }

      preparedStatement.close();
      resultSet.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Changes a member's late status to either true or false
  public static void late(String user, boolean isLate)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET late = ? WHERE username = ?");

      if(isLate)
      {
        preparedStatement.setBoolean(1, true);
      } else {
        preparedStatement.setBoolean(1, false);
      }

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

  //Sets a user's verified status to true
  public static void approve(String u)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET verified = ? WHERE username = ?");

      preparedStatement.setBoolean(1, true);
      preparedStatement.setString(2, u);


      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Checks if account has been verified given a member's username
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

  //Creates a new Account
  public static void nAccount(String fname, String lname, int age, String addr,
                              String phone, String email, String u, String p,
                              boolean sho, int o) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("INSERT INTO directory "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

      //Just makes the first letter of the person's first and last name capital
      String first = fname.substring(0, 1).toUpperCase() + fname.substring(1);
      String last = lname.substring(0, 1).toUpperCase() + lname.substring(1);

      preparedStatement.setString(1, first);
      preparedStatement.setString(2, last);
      preparedStatement.setInt(3, age);
      preparedStatement.setString(4, addr);
      preparedStatement.setString(5, phone);
      preparedStatement.setString(6, email);
      preparedStatement.setString(7, u);
      preparedStatement.setString(8, p);
      preparedStatement.setBoolean(9, false);
      preparedStatement.setBoolean(10, sho);
      preparedStatement.setBoolean(11, false);
      preparedStatement.setInt(12, 1000);
      preparedStatement.setInt(13, 0);

      //if(coupon) preparedStatement.setInt(12, 500) else preparedStatement.setInt(12, 1000);

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
          memberUser = user;

          //makes it so that the first letters of the first and last name are capital
          fName = resultSet.getString("firstName").substring(0, 1).toUpperCase() +
            resultSet.getString("firstName").substring(1);
          lName = resultSet.getString("lastName").substring(0, 1).toUpperCase() +
            resultSet.getString("lastName").substring(1);

          //Used for extracting the domain from the given email in the database
          //will give back gmail.com, admin.com, tennis.com
          email = resultSet.getString("email");
          domain = email.substring(email.lastIndexOf("@") + 1);

          isShown = resultSet.getBoolean("shown");
          isLate = resultSet.getBoolean("late");
          owe = resultSet.getInt("owe");
          verified = resultSet.getBoolean("verified");
          age = resultSet.getInt("age");
          addr = resultSet.getString("address");
          phone = resultSet.getString("phone");
          guests = resultSet.getInt("guests");

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

  public static void setFirstName(String first) {
      fName = first;

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET firstName = ? WHERE username = ?");

      preparedStatement.setString(1, first);
      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setLastName(String last) {
    lName = last;

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET lastName = ? WHERE username = ?");

      preparedStatement.setString(1, last);
      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setAge(int x) {
    age = x;

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET age = ? WHERE username = ?");

      preparedStatement.setInt(1, age);
      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setAddress(String a) {
    addr = a;

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory address age = ? WHERE username = ?");

      preparedStatement.setString(1, a);
      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setPhone(String p) {
    phone = p;

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET phone = ? WHERE username = ?");

      preparedStatement.setString(1, p);
      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setUser(String u) {
    memberUser = u;
    //change username in makeres database

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET username = ? WHERE username = ?");

      preparedStatement.setString(1, u);
      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();

      PreparedStatement preparedStatement2 =
        connection.prepareStatement("UPDATE reservation SET username = ? WHERE username = ?");

      preparedStatement2.setString(1, u);
      preparedStatement2.setString(2, memberUser);
      preparedStatement2.executeUpdate();
      preparedStatement2.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setPass(String p) {
    memberPass = p;

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET pword = ? WHERE username = ?");

      preparedStatement.setString(1, p);
      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }
}
