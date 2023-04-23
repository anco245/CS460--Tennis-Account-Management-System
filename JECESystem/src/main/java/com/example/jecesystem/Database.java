package com.example.jecesystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Calendar;

public class Database {

  //Variables needed for accessing the database
  static String username = "root";
  static String password = "sqlpass";
  static String url = "jdbc:mysql://localhost:3306/courtsystem";

  //To hold current user's information
  public static String fName = "";
  public static String lName = "";
  public static String extension = "";
  public static int age = 0;
  public static String addr = "";
  public static String phone = "";
  public static String email = "";
  public static String memberUser = "";
  public static String memberPass = "";
  static boolean isShown = false;
  static boolean verified = false;
  public static boolean isLate = false;
  public static int owe = 0;
  public static int guestsFromDatabase = 0;
  public static boolean keep = true;
  public static boolean penalized = false;
  public static boolean keepConfirm = false;
  public static int annual = 0;

  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  static String[] full = new String[160];

  static String[] times = {"09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00",
                           "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00",
                            "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00",
                           "18:00:00", "18:30:00"};

  static LocalDateTime dateTime = LocalDateTime.now();
  static LocalDateTime nextMonday = dateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
  static LocalDateTime nextTuesday = dateTime.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
  static LocalDateTime nextWednesday = dateTime.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
  static LocalDateTime nextThursday = dateTime.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
  static LocalDateTime nextFriday = dateTime.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
  static LocalDateTime nextSaturday = dateTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
  static LocalDateTime nextSunday = dateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

  static String formatMon = nextMonday.format(formatter);
  static String formatTues = nextTuesday.format(formatter);
  static String formatWed = nextWednesday.format(formatter);
  static String formatThur = nextThursday.format(formatter);
  static String formatFri = nextFriday.format(formatter);
  static String formatSat = nextSaturday.format(formatter);
  static String formatSun = nextSunday.format(formatter);
  static String formatDay = dateTime.format(formatter);

  //Used for reseting the amount of guests for a user in the database
  public static void resetGuests() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement stmt =
        connection.prepareStatement("UPDATE directory set guests = ? WHERE username = ?");
      stmt.setInt(1, 0);
      stmt.setString(2, memberUser);

      stmt.executeUpdate();

      stmt.close();

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }


  //Removes people from directory who opted to
  //not keep their account when asked on 1/1
  public static void removeNonKeeps() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT username FROM directory WHERE keepAccount = false");

      ResultSet resultSet = preparedStatement.executeQuery();

      String user = "";

      while (resultSet.next()) {
        user = resultSet.getString("username");
        deleteFromDb(user, "directory");
        deleteFromCourts(user);
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
      //Need to change path to the sql scripts on your own system
      FileReader fileReader = new FileReader("C:\\Users\\johnc\\OneDrive\\Documents\\GitHub\\CS460-Project\\SQL\\createDatabase.sql");
      FileReader fileReader2 = new FileReader("C:\\Users\\johnc\\OneDrive\\Documents\\GitHub\\CS460-Project\\SQL\\insertValues.sql");

      // Create a Statement object from the database connection
      Statement statement = connection.createStatement();

      // Reads each line in the sql script into "line" variable
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
      throw new IllegalStateException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  //Takes user's information in waitlist and adds them to database
  public static void addFromWait() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM waiting LIMIT 0,1");

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

        Database.deleteFromDb(uname, "waiting");
        Database.nAccount(fname, lname, age, address, phone, email, uname, p, s, o);
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

      int total = 0;

      Statement stmt = connection.createStatement();
      ResultSet result = stmt.executeQuery("SELECT COUNT(*) AS total FROM directory");

      while(result.next()) {
        total = result.getInt("total");
      }

      stmt.close();
      result.close();

      return total;

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void insertIntoWait(String fname, String lname, int age, String addr,
                                    String phone, String email, String u, String p, boolean s, int o) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement preparedStatement =
        connection.prepareStatement("INSERT INTO waiting (firstName, lastName, age, address, phone, " +
          "email, username, pword, shown, owe) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

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

  //Sets whether the user has opted to keep their account
  //when asked on 1/1
  public static void setKeep(String user, boolean b) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET keepAccount = ? WHERE username = ?");

      if(user.equals(memberUser))
      {
        keep = b;
      }

      preparedStatement.setBoolean(1, b);
      preparedStatement.setString(2, user);
      preparedStatement.executeUpdate();

      preparedStatement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static boolean hasPassed(String slot) {
    Calendar rightNow = Calendar.getInstance();
    int hour = rightNow.get(Calendar.HOUR_OF_DAY);
    int minute = rightNow.get(Calendar.MINUTE);

    String day = slot.substring(0, 10);
    String time = slot.substring(11, slot.length() - 1);

    int resMin = Integer.parseInt(time.substring(3, 5));
    int resHour;

    if(day.equals(formatDay))
    {
      if(time.charAt(0) == '0')
      {
        resHour = Integer.parseInt(time.substring(1, 2));
      } else {
        resHour = Integer.parseInt(time.substring(0, 2));
      }
    } else {
      return false;
    }

    return (resHour != hour || minute >= resMin) && hour >= resHour;
  }

  public static boolean isUpdated() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      boolean isUp = false;

      String sql = "select count(*) as count from court1 where date(dayAndTime) < date(NOW())";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()) {
        isUp = rs.getInt("count") == 0;
      }

      preparedStatement.close();

      return isUp;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void updateCourts()
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      for(int i = 1; i < 13; i++)
      {
        String court = "court" + i;
        String sql = "delete from " + court + " where date(dayAndTime) < date(NOW())";
        PreparedStatement preparedStatement =
          connection.prepareStatement(sql);
        preparedStatement.executeUpdate();

        toArray();

        String sql2 = "";

        for(int j = 140; j < 160; j++)
        {
          sql2 = "INSERT INTO " + court + " (dayAndTime, occupied) VALUES (?, ?)";
          PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

          preparedStatement2.setTimestamp(1, Timestamp.valueOf(full[j]));
          preparedStatement2.setInt(2, 0);
          preparedStatement2.executeUpdate();
        }

        preparedStatement.close();
      }

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void addSubGuests(int num) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory where username = ?");
      preparedStatement.setString(1, memberUser);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        guestsFromDatabase = resultSet.getInt("guests");
        guestsFromDatabase = guestsFromDatabase + num;

        PreparedStatement p2 =
          connection.prepareStatement("UPDATE directory SET guests = ? WHERE username = ?");

        p2.setInt(1, guestsFromDatabase);
        p2.setString(2, memberUser);

        p2.executeUpdate();
        p2.close();
      }

      preparedStatement.close();
      resultSet.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static String getBankInfo(String user)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * from bank where username = ?");

      preparedStatement.setString(1, user);

      ResultSet rs = preparedStatement.executeQuery();

      String bName = "";
      String aNum = "";
      String ssn = "";
      String type = "";

      while(rs.next())
      {
        bName = rs.getString("bankName");
        aNum = rs.getString("accountNum");
        ssn = rs.getString("ssn");
        type = rs.getString("accountType");
      }

      preparedStatement.close();

      return "Name of Bank: " + bName + "\nAccount Number: " + aNum +
        "\nSocial Security Number: " + ssn + "\nAccount Type: " + type;

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static boolean deleteBank(String user)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("DELETE from bank where username = ?");

      preparedStatement.setString(1, user);
      preparedStatement.executeUpdate();
      preparedStatement.close();

      return false;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static boolean addBank(String user, String bankName, String accNum, String social, String typeAcc)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("INSERT INTO bank VALUES (?, ?, ?, ?, ?)");

      preparedStatement.setString(1, user);
      preparedStatement.setString(2, bankName);
      preparedStatement.setString(3, accNum);
      preparedStatement.setString(4, social);
      preparedStatement.setString(5, typeAcc);

      preparedStatement.executeUpdate();

      preparedStatement.close();

      return false;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static boolean hasBankAccount(String user)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("select count(*) from bank where username = ?");

      preparedStatement.setString(1, user);
      ResultSet rs = preparedStatement.executeQuery();

      boolean x = false;

      while(rs.next()) {
        x = rs.getInt("count(*)") != 0;
      }

      preparedStatement.close();

      return x;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //adds or subtracts amount owed
  public static void addSubOwe(String user, int priceOfGuests) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT * FROM directory");

      ResultSet resultSet = preparedStatement.executeQuery();

      String uname = "";
      int amtOwed = 0;

      while (resultSet.next()) {
        uname = resultSet.getString("username");
        amtOwed = resultSet.getInt("owe");

        if (uname.equals(user)) {
          PreparedStatement p2 =
            connection.prepareStatement("UPDATE directory SET owe = ? WHERE username = ?");

          amtOwed = amtOwed + priceOfGuests;

          if(user.equals(memberUser))
          {
            owe = amtOwed;
          }

          p2.setInt(1, amtOwed);
          p2.setString(2, user);

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
  public static void setLate(String user, boolean isLate) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("UPDATE directory SET late = ? WHERE username = ?");

      preparedStatement.setBoolean(1, isLate);

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
        connection.prepareStatement("SELECT EXISTS (SELECT * FROM directory WHERE username = ?) AS result");

      preparedStatement.setString(1, user);

      ResultSet resultSet = preparedStatement.executeQuery();

      boolean res = false;

      while (resultSet.next()) {
        res = resultSet.getBoolean("result");
      }

      preparedStatement.close();
      resultSet.close();
      connection.close();

      return res;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }


  public static int getOccupied(int c, String time)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      String court = "court" + c;

      String sql = "Select occupied from "+ court +" WHERE DayAndTime = \"" + time + "\"";
      PreparedStatement preparedStatement =
        connection.prepareStatement(sql);

      ResultSet rs = preparedStatement.executeQuery();;

      int result = 0;

      while(rs.next())
      {
        if(rs.getInt("occupied") != 0)
        {
          result = rs.getInt("occupied") - 1;
        }
      }

      preparedStatement.close();

      return result;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Sets a user's verified status to true
  public static void setApprove(String u) {
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

      boolean v = false;

      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT verified FROM directory WHERE username = ?");

      preparedStatement.setString(1, u);
      ResultSet resultSet = preparedStatement.executeQuery();

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

  //Removes a user from the directory
  public static void deleteFromDb(String u, String db) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      String sql = "DELETE FROM " + db + " WHERE username = ?";
      PreparedStatement preparedStatement =
        connection.prepareStatement(sql);

      preparedStatement.setString(1, u);

      preparedStatement.executeUpdate();
      preparedStatement.close();

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Creates a new Account and sets global variables for all user's information
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

      int x = 1000;
      if(age < 18)
      {
        annual = 250;
        x = x + 250;
      } else if (age < 65) {
        annual = 400;
        x = x + 400;
      } else {
        annual = 300;
        x = x + 300;
      }

      preparedStatement.setInt(13, x);
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

  //Sees if the given username and password belong to any single entry
  //Saves all information stores in database into global variables
  public static boolean login(String user, String pass) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      PreparedStatement ps =
        connection.prepareStatement("SELECT * FROM directory WHERE username = ? AND pword = ?");
      ps.setString(1, user);
      ps.setString(2, pass);

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        memberUser = user;

        //makes it so that the first letters of the first and last name are capital
        fName = resultSet.getString("firstName").substring(0, 1).toUpperCase() +
          resultSet.getString("firstName").substring(1);
        lName = resultSet.getString("lastName").substring(0, 1).toUpperCase() +
          resultSet.getString("lastName").substring(1);

        //Used for extracting the extension from the given email in the database
        //Gives back gmail.com, admin.com, tennis.com
        email = resultSet.getString("email");
        extension = email.substring(email.lastIndexOf("@") + 1);

        isShown = resultSet.getBoolean("shown");
        isLate = resultSet.getBoolean("late");
        owe = resultSet.getInt("owe");
        verified = resultSet.getBoolean("verified");
        age = resultSet.getInt("age");
        addr = resultSet.getString("address");
        phone = resultSet.getString("phone");
        guestsFromDatabase = resultSet.getInt("guests");
        penalized = resultSet.getBoolean("penalized");
        keep = resultSet.getBoolean("keepAccount");
        keepConfirm = resultSet.getBoolean("keepConfirm");

        if(age < 18)
        {
          annual = 250;
        } else if (age < 65) {
          annual = 400;
        } else {
          annual = 300;
        }

        return true;
      }

      ps.close();
      resultSet.close();
      connection.close();
      return false;

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

  //Used for determining if the court tables have been populated
  public static boolean beenPopulated() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
      PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT count(*) as count from court1");

      ResultSet resultSet = preparedStatement.executeQuery();

      boolean x = false;

      while(resultSet.next())
      {
        x = (resultSet.getInt("count") != 0);
      }

      return x;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void cancelReservation(int c, String date) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      String court = "court" + c;

      String sql = "UPDATE " + court + " SET username = null, occupied = 0 WHERE dayAndTime = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      //date = YYYY-MM-DD HH:MM:SS
      if(date.length() != 19)
      {
        date = date + ":00";
      }

      preparedStatement.setTimestamp(1, Timestamp.valueOf(date));
      preparedStatement.executeUpdate();

      preparedStatement.close();
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static boolean exceededResLimit(String dayTime) throws SQLException {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      int x = 0;
      String court = "";
      String sql = "";

      for (int j = 1; j < 13; j++) {
        court = "court" + j;

        //date has to be wrapped in quotes for some reason
        sql = "SELECT count(*) AS count from " + court + " where username = ? AND date(dayAndTime) = date(\"" + dayTime + "\")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, memberUser);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          if (resultSet.getInt("count") > 2) {
            preparedStatement.close();
            return true;
          } else {
            x = x + resultSet.getInt("count");
          }
        }
      }

      return x > 1;
    }
  }

  //Sees if timeslot at given court has been filled
  public static boolean available(String courtNum, String time) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      boolean occ = false;

      String sql = "SELECT occupied FROM " + courtNum + " where dayAndTime = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, time);

      ResultSet resultSet = preparedStatement.executeQuery();

      while(resultSet.next()) {
        occ = (resultSet.getInt("occupied") > 0);
      }

      resultSet.close();

      return occ;

    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  //Used for when the app is first launched on a system. Loads all the
  //times for the sql tables for the courts in an array
  public static void toArray() {
    String[] exactDays = new String[8];

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    for (int i = 0; i < 8; i++) {
      if (i == 0) {formatDay = dateTime.format(formatter);
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
      } else {
        formatSun = nextSunday.format(formatter);
        exactDays[i] = formatSun;
      }
    }

    Arrays.sort(exactDays);

    String str = "";
    int count = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 20; j++) {
        str = exactDays[i] + " " + times[j];
        full[count] = str;
        count++;
      }
    }
  }

  //Assigns a given time slot in a given court to a username.
  public static void makeRes(String pendingNum, String memberName, String slot, int total) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      String sql = "UPDATE " + pendingNum + " SET username = ?, occupied = ? WHERE dayAndTime = ?";

      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, memberName);
      preparedStatement.setInt(2, total);
      preparedStatement.setTimestamp(3, Timestamp.valueOf(slot));
      preparedStatement.executeUpdate();

      preparedStatement.close();
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static boolean sameTimeOtherCourt(String member, String slot, String court)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      String cnum = "";
      String sql = "";

     for(int i = 1; i < 12; i++)
     {
       cnum = "court" + i;
       sql = "SELECT username AS user FROM " + cnum + " WHERE dayAndTime = \"" + slot + "\"";

       PreparedStatement preparedStatement = connection.prepareStatement(sql);
       ResultSet rs = preparedStatement.executeQuery();

       while(rs.next())
       {
          if(rs.getString("user") != null && rs.getString("user").equals(member) && !cnum.equals(court))
          {
            rs.close();
            preparedStatement.close();
            return true;
          }
       }
     }

     return false;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void deleteFromCourts(String user)
  {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      String court = "";
      String sql = "";
      String date = "";

      for(int i = 1; i < 13; i++)
      {
        court = "court" + i;

        sql = "SELECT dayAndTime FROM " + court + " WHERE username = ?";
        PreparedStatement p = connection.prepareStatement(sql);
        p.setString(1, user);

        ResultSet rs = p.executeQuery();

        while(rs.next())
        {
          date = String.valueOf(rs.getTimestamp("dayAndTime"));
          date = date.substring(0, date.length() - 2);

          cancelReservation(i, date);
        }

        p.close();

      }
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to the database!", e);
    }
  }

  public static void populateCourts() {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {

      toArray();

      String court = "";
      String sql = "";

      for (int i = 1; i < 13; i++) {
        for (int j = 0; j < 160; j++) {
          court = "court" + i;

          sql = "INSERT INTO " + court + " (dayAndTime, occupied) VALUES (?, ?)";
          PreparedStatement preparedStatement = connection.prepareStatement(sql);

          preparedStatement.setTimestamp(1, Timestamp.valueOf(full[j]));
          preparedStatement.setInt(2, 0);
          preparedStatement.executeUpdate();

          preparedStatement.close();
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
