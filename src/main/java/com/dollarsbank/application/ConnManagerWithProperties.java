package com.dollarsbank.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.dollarsbank.utility.ColorUtility;

// TO FIRST: Create a properties file before anything so that it is written out, an example
// 			 is shown here in this project. Steps:
//			 1. Right click on the project and select New -> Folder, name it "resources"
//			 2. Right click on new resources folder and select New -> File, 
//			    name it "config.properties"
//			 3. Check the config.properties file in this project to see what needs to be written.
//			    There are some slight changes with the properties values if on Windows or Mac OS.
public class ConnManagerWithProperties {
	
	// set connection to null right away
	private static Connection connection = null;
	
	private static void makeConnection() {
		
		// Properties will be used to access our properties file and read its values 
		Properties props = new Properties();
		
		// use the FileInputStream to load in the values from the file to props
		try {
			props.load(new FileInputStream("src/main/resources/config.properties"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// save the values to the variables, use the same name as what is written in the
		// file to get the values
		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		
		
		// finally, establish the connection
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			
		} catch(SQLException e) {
			System.out.println(ColorUtility.RED_TEXT);
			e.printStackTrace();
			System.out.println(ColorUtility.TEXT_RESET);
		} catch(ClassNotFoundException e) {
			System.out.println(ColorUtility.RED_TEXT);
			e.printStackTrace();
			System.out.println(ColorUtility.TEXT_RESET);
		}
		
	}
	
	// will establish the connection if it it is not already made and return connection
	// object, making this a singleton design
	public static Connection getConnection() {
		
		if(connection == null) {
			makeConnection();
		}
		
		return connection;
	}
}