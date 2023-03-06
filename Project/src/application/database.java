package application;

import java.sql.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.Text; 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class database {
	
	public static String domain = "";
	
	public static String all = "";
	public static String person = "";
	
	public static String username = "root";
	public static String password = "sqlpass";
	public static String url = "jdbc:mysql://localhost:3306/courtsystem";
	
	
	//Doesn't work yet
	public static void approve()
	{
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
				PreparedStatement preparedStatement = 
						connection.prepareStatement("SELECT * FROM directory");
					
				ResultSet resultSet = preparedStatement.executeQuery();
					
				while(resultSet.next()) {
					String uname = resultSet.getString("username");
					Boolean v = resultSet.getBoolean("verified");
					
					System.out.println("boolean: " + v);
					
					if(v==false)
					{
						System.out.println("entered");
						PreparedStatement preparedStatement2 = 
								connection.prepareStatement("UPDATE directory SET verified = ? "
								+ "WHERE username = ?");
						
						preparedStatement2.setBoolean(1, true);
						preparedStatement2.setString(2, uname);
						preparedStatement2.close();
					}
				}
						
				preparedStatement.close();
				resultSet.close();
				connection.close();
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
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
		
			Boolean v = false;
			
			if(resultSet.next())
				v = resultSet.getBoolean("verified");
			
			preparedStatement.close();
			resultSet.close();
			connection.close();
			
			return v;
			
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	
	//Removes a person's information from the database
	//Only available to chairman
	public static void delete(String u)
	{
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			//Not sure if this works yet
			PreparedStatement preparedStatement = 
			connection.prepareStatement("DELETE FROM directory WHERE username = ?");
			
			preparedStatement.setString(1, u);
		
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	
	//str should = variable name in database if specific or 0 if everything
	public static void getData(String userName)
	{	
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			if(userName == "0")
			{
				PreparedStatement preparedStatement = 
						connection.prepareStatement("SELECT * FROM directory");
					
				ResultSet resultSet = preparedStatement.executeQuery();
					
				while(resultSet.next()) {
					String fname = resultSet.getString("firstName");
					String lname = resultSet.getString("lastName");
					String age = resultSet.getString("age");
					String addr = resultSet.getString("address");
					String phone = resultSet.getString("phone");
					String email = resultSet.getString("email") + ".com";
					String p = resultSet.getString("pword");
						
						all = all + fname + " " + lname + "  " + age + "  " + addr + "  " + 
						phone + "  " + email + "\n";	
				}
				
				preparedStatement.close();
				resultSet.close();
				connection.close();
			} else {
				PreparedStatement preparedStatement = 
						connection.prepareStatement("SELECT * FROM directory WHERE username = ?");
				
				preparedStatement.setString(1, userName);
					
				ResultSet resultSet = preparedStatement.executeQuery();
					
				while(resultSet.next()) {
					String fname = resultSet.getString("firstName");
					String lname = resultSet.getString("lastName");
					String age = resultSet.getString("age");
					String addr = resultSet.getString("address");
					String phone = resultSet.getString("phone");
					String email = resultSet.getString("email") + ".com";
					String p = resultSet.getString("pword");
						
						all = all + fname + " " + lname + "  " + age + "  " + addr + "  " + 
						phone + "  " + email + "\n";	
				}
				
				preparedStatement.close();
				resultSet.close();
				connection.close();
			}
			
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	
	//Creates a new Account
	public static void nAccount(String fname, String lname, String age, String addr, 
			String phone, String email, String u, String p) {
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			PreparedStatement preparedStatement = 
					connection.prepareStatement("INSERT INTO directory "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
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
			
			preparedStatement.executeUpdate();

			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	
	//Not sure how to do this yet
	//Should take username, the value you want to change, and the original value
	public static void editData(String fname, String lname, String age, String addr, 
			String phone, String u, String p) {
		
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			
			PreparedStatement preparedStatement = 
					connection.prepareStatement("UPDATE directory SET age = ? "
							+ "WHERE username = ?");
			
			//preparedStatement.setString(1, age);
			//preparedStatement.setString(2, u);
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			connection.close();
			
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	
	//Loops through database to find where both username and password are used together
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
					domain = em.substring(em.lastIndexOf("@") + 1);
					
					person = resultSet.getString("firstName") + " " + 
							resultSet.getString("lastName");
					
					return true;
				}
			}
			
			preparedStatement.close();
			resultSet.close();
			connection.close();
			
			return false;

		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
}