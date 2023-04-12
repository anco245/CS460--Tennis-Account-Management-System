package com.example.jecesystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;


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

  public static boolean keep = true;

  public static boolean penalized = false;

  public static boolean keepConfirm = false;

  public static String[] full = new String[160];

  String today = "Today";
  String monday = "Monday";
  String tuesday = "Tuesday";
  String wednesday = "Wednesday";
  String thursday = "Thursday";
  String friday = "Friday";
  String saturday = "Saturday";
  String sunday = "Sunday";
  static String[] days = {"Today", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
                          "Saturday", "Sunday"};

  static String[] times = {"09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00",
                           "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00",
                            "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00",
                           "18:00:00", "18:30:00"};

  public static LocalDateTime dateTime = LocalDateTime.now();
  public static LocalDateTime nextMonday = dateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
  public static LocalDateTime nextTuesday = dateTime.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
  public static LocalDateTime nextWednesday = dateTime.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
  public static LocalDateTime nextThursday = dateTime.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
  public static LocalDateTime nextFriday = dateTime.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
  public static LocalDateTime nextSaturday = dateTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
  public static LocalDateTime nextSunday = dateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

  public static String formatMon = "";
  public static String formatTues = "";
  public static String formatWed = "";
  public static String formatThur = "";
  public static String formatFri = "";
  public static String formatSat = "";
  public static String formatSun = "";
  public static String formatDay = "";

  public static void removeNonKeeps() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT username FROM directory WHERE keepAccount = false");

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String user = resultSet.getString("username");
        deleteFromDir(user);
        deleteFromRes(user);
      }

      preparedStatement.close();
      resultSet.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Resets the database to initial values
  public static void reset() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      //Reads the SQL script file
      //need to change path to the sql scripts on your own system
      FileReader fileReader = new FileReader("C:\\Users\\johnc\\OneDrive\\Documents\\GitHub\\CS460-Project\\SQL\\createDatabase.sql");
      FileReader fileReader2 = new FileReader("C:\\Users\\johnc\\OneDrive\\Documents\\GitHub\\CS460-Project\\SQL\\insertValues.sql");

      // Create a Statement object from the database connection
      Statement statement = connection.createStatement();

      // Execute the SQL statements in the script
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {

        //if current line is blank, this skips it,
        //otherwise will get "cannot read empty statement" error
        if (!line.equals("")) {
          statement.execute(line);
        }
      }

      //This is for reading second script
      BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
      while ((line = bufferedReader2.readLine()) != null) {

        //if current line is blank, this skip it,
        //otherwise will get "cannot read empty statement" error
        if (!line.equals("")) {
          statement.execute(line);
        }
      }

      // Close the Statement and database connection
      statement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("SQL scripts need to be formatted so that each statement is one line", e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void addFromWait() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM waiting WHERE priority = 1");

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String uname = resultSet.getString("username");
        String fname = resultSet.getString("firstName");
        String lname = resultSet.getString("lastName");
        int age = resultSet.getInt("age");
        String address = resultSet.getString("address");
        String phone = resultSet.getString("phone");
        String email = resultSet.getString("email");
        String p = resultSet.getString("pword");
        boolean s = resultSet.getBoolean("shown");
        int o = resultSet.getInt("owe");

        Database.deleteFromRes(uname);
        Database.nAccount(fname, lname, age, address, phone, email, uname, p, s, owe);
      }

      preparedStatement.close();
      resultSet.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //returns the amount of elements for table "directory"
  public static int getSize() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      Statement stmt = connection.createStatement();
      ResultSet result = stmt.executeQuery("SELECT COUNT(*) AS total FROM directory");

      while (result.next()) {
        int total = result.getInt("total");
        stmt.close();

        return total;
      }

      return 0;

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void insertIntoWait(String fname, String lname, int age, String addr,
                                    String phone, String email, String u, String p, boolean s, int o) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("INSERT INTO waiting (firstName, lastName, age, address, phone, " +
          "email, username, pword, shown, owe) "
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

      //Just makes the first letter of the person's first and last name capital
      String first = fname.substring(0, 1).toUpperCase() + fname.substring(1);
      String last = lname.substring(0, 1).toUpperCase() + lname.substring(1);

      //"default"
      preparedStatement.setString(1, first);
      preparedStatement.setString(2, last);
      preparedStatement.setInt(3, age);
      preparedStatement.setString(4, addr);
      preparedStatement.setString(5, phone);
      preparedStatement.setString(6, email);
      preparedStatement.setString(7, u);
      preparedStatement.setString(8, p);
      preparedStatement.setBoolean(9, s);
      preparedStatement.setInt(10, o);

      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setKeep(String user, boolean b) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET keepAccount = ? WHERE username = ?");

      preparedStatement.setBoolean(1, b);
      preparedStatement.setString(2, user);
      preparedStatement.executeUpdate();

      preparedStatement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setPenalized(String user, boolean b) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET penalized = ? WHERE username = ?");

      preparedStatement.setBoolean(1, b);
      preparedStatement.setString(2, user);
      preparedStatement.executeUpdate();

      preparedStatement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void addSubGuests(int num) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String uname = resultSet.getString("username");
        guests = resultSet.getInt("guests");

        if (uname.equals(memberUser)) {
          PreparedStatement p2 =
            connection.prepareStatement("UPDATE directory SET guests = ? WHERE username = ?");

          guests = guests + num;

          p2.setInt(1, guests);
          p2.setString(2, memberUser);

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

  //adds or subtracts amount owed
  public static void addSubOwe(String user, int amount) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String uname = resultSet.getString("username");
        owe = resultSet.getInt("owe");

        if (uname.equals(user)) {
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
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Changes a member's late status to either true or false
  public static void late(String user, boolean isLate) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET late = ? WHERE username = ?");

      if (isLate) {
        preparedStatement.setBoolean(1, true);
      } else {
        preparedStatement.setBoolean(1, false);
      }

      preparedStatement.setString(2, user);
      preparedStatement.executeUpdate();

      preparedStatement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //checks to see if a given username exists in the database
  public static boolean inDirectory(String user) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String uname = resultSet.getString("username");

        if (uname.equals(user)) {
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
  public static void approve(String u) {
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
  public static boolean verified(String u) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT verified FROM directory WHERE username = ?");

      preparedStatement.setString(1, u);
      ResultSet resultSet = preparedStatement.executeQuery();

      boolean v = false;
      if (resultSet.next())
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
  public static void deleteFromDir(String u) {
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
          + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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
      preparedStatement.setBoolean(12, false);
      preparedStatement.setInt(13, 1000);
      preparedStatement.setInt(14, 0);
      preparedStatement.setBoolean(15, true);
      preparedStatement.setBoolean(16, false);


      //if(coupon) preparedStatement.setInt(12, 500) else preparedStatement.setInt(12, 1000);

      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Loops through the database to find where both username and password are used together
  //Also saves the domain of their email, to later determine which type of account this is
  public static boolean login(String user, String pass) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String uname = resultSet.getString("username");
        String pword = resultSet.getString("pword");

        if (uname.equals(user) && pword.equals(pass)) {
          memberUser = user;

          //makes it so that the first letters of the first and last name are capital
          fName = resultSet.getString("firstName").substring(0, 1).toUpperCase() +
            resultSet.getString("firstName").substring(1);
          lName = resultSet.getString("lastName").substring(0, 1).toUpperCase() +
            resultSet.getString("lastName").substring(1);

          //Used for extracting the extension from the given email in the database
          //Gives back gmail.com, admin.com, tennis.com
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
          penalized = resultSet.getBoolean("penalized");
          keep = resultSet.getBoolean("keepAccount");
          keepConfirm = resultSet.getBoolean("keepConfirm");

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
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void changeShown() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET shown = ? WHERE username = ?");

      if (isShown) {
        preparedStatement.setBoolean(1, false);
      } else {
        preparedStatement.setBoolean(1, true);
      }

      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();

      preparedStatement.close();
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
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setUser(String u) {
    memberUser = u;

    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      //For directory database
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET username = ? WHERE username = ?");

      preparedStatement.setString(1, u);
      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      connection.close();

      //For reservation database
      PreparedStatement preparedStatement2 =
        connection.prepareStatement("UPDATE reservation SET username = ? WHERE username = ?");

      preparedStatement2.setString(1, u);
      preparedStatement2.setString(2, memberUser);
      preparedStatement2.executeUpdate();
      preparedStatement2.close();
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
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void setConfirm(boolean b) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET keepConfirm = ? WHERE username = ?");

      preparedStatement.setBoolean(1, b);
      preparedStatement.setString(2, memberUser);
      preparedStatement.executeUpdate();

      preparedStatement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static boolean exceededResLimit(String num, String dateAndTime) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      //int
      for (Integer i = 0; i < 12; i++) {
        String court = "court" + i.toString(i);

        String sql = "SELECT occupied FROM " + court + " WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, memberUser);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          String dayandTime = String.valueOf(resultSet.getTimestamp("dayAndTime"));

          //str = str = dayandTime + "\n";
        }
        preparedStatement.close();
      }

      return false;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //function to check if court at specific time is reserved
  public static boolean checkRes(String num, String dateAndTime) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      //String sql = "UPDATE " + pendingNum + " SET username = ?, occupied = ? WHERE dayAndTime = ?";

      for (Integer i = 0; i < 12; i++) {
        String court = "court" + i.toString(i);

        String sql = "SELECT occupied FROM " + court + " WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, court);


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          String dayandTime = String.valueOf(resultSet.getTimestamp("dayAndTime"));

          //str = str = dayandTime + "\n";
        }
        preparedStatement.close();
      }

      return false;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //supposed to return string[]
  public static void available(String courtNum, boolean b, String day) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      //gets size
      /*
      String sql = "SELECT count(dayAndTime) as num FROM " + courtNum + " WHERE occupied = false AND" +
                    "dayAndTime LIKE '" + + "%'";


      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      ResultSet resultSet = preparedStatement.executeQuery();



      int num = 0;
      while (resultSet.next()) {
        num = resultSet.getInt("num");
      }

      String[] open = new String[num + 1];

      //Gets results
      String sql2 = "SELECT dayAndTime FROM " + courtNum + " WHERE occupied = false";
      PreparedStatement p2 = connection.prepareStatement(sql2);
      ResultSet rs = p2.executeQuery();

      int count = 0;
      while (resultSet.next()) {
        Timestamp t = rs.getTimestamp("dayAndTime");
        String time = t.toString();

        if(b) {
          time = time.substring(time.length() - 5, time.length());
        } else {
          time = time.substring(0, 10);
        }

        open[count] = time;
        count++;
      }
      preparedStatement.close();
       */

      //return open
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Used for when the app is first launched on a system. Loads all of the
  //times for the sql tables for the courts
  public static void toArray() {
    String[] exactDays = new String[8];

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (int i = 0; i < 8; i++) {
      if (i == 0) {
        formatDay = dateTime.format(formatter);
        exactDays[i] = formatDay;
      } else if (i == 1) {
        formatMon = nextMonday.format(formatter);
        exactDays[i] = formatMon;
      } else if (i == 2) {
        formatTues = nextTuesday.format(formatter);
        exactDays[i] = formatTues;
      } else if (i == 3) {
        formatWed = nextWednesday.format(formatter);
        exactDays[i] = formatWed;
      } else if (i == 4) {
        formatThur = nextThursday.format(formatter);
        exactDays[i] = formatThur;
      } else if (i == 5) {
        formatFri = nextFriday.format(formatter);
        exactDays[i] = formatFri;
      } else if (i == 6) {
        formatSat = nextSaturday.format(formatter);
        exactDays[i] = formatSat;
      } else if (i == 7) {
        formatSun = nextSunday.format(formatter);
        exactDays[i] = formatSun;
      }
    }

    Arrays.sort(exactDays);

    int count = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 20; j++) {
        String str = exactDays[i] + " " + times[j];
        full[count] = str;
        count++;
      }
    }
  }

  public static void clearRes() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String usr = resultSet.getString("username");
        int amt = resultSet.getInt("amountOfRes");

        if (amt > 0) {
          PreparedStatement p2 =
            connection.prepareStatement("UPDATE directory SET amountOfRes = 0 WHERE username = ?");

          p2.setString(1, usr);

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

  //function to update the reservation
  public static void makeRes(String pendingNum, String memberName, String slot) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      String sql = "UPDATE " + pendingNum + " SET username = ?, occupied = ? WHERE dayAndTime = ?";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, memberName);
      preparedStatement.setBoolean(2, true);
      preparedStatement.setTimestamp(3, Timestamp.valueOf(slot));
      preparedStatement.executeUpdate();

      preparedStatement.close();
      connection.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void deleteFromRes(String u) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("DELETE FROM reservation WHERE username = ?");

      preparedStatement.setString(1, u);

      preparedStatement.executeUpdate();
      preparedStatement.close();

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static boolean inReservation(String user) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM reservation");

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String uname = resultSet.getString("username");

        if (uname == null) {
          return false;
        } else if (uname.equals(user)) {
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

  public static void populateCourts() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      toArray();

      for (Integer i = 1; i < 13; i++) {
        for (int j = 0; j < 160; j++) {
          String court = "court" + i.toString(i);

          String sql = "INSERT INTO " + court + " (dayAndTime, occupied) VALUES (?, false)";
          PreparedStatement preparedStatement =
            connection.prepareStatement(sql);
          preparedStatement.setTimestamp(1, Timestamp.valueOf(full[j]));
          preparedStatement.executeUpdate();
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
