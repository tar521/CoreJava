package com.dollarsbank.application;

import java.sql.Connection;
import java.sql.SQLException;

import com.dollarsbank.controller.DollarsBankController;

public class DollarsBankApplication {
	
	public static Connection conn;
	
	public static void main(String[] args) {
		
		conn = ConnManagerWithProperties.getConnection();
		
		if (conn == null) {
			System.out.println("Could not connect to DB");
			System.out.println("Now exiting...");
			return;
		}
		
		DollarsBankController ctl = new DollarsBankController();
		ctl.startUp();
		
		try {
			conn.close();
			exitMessage();
		} catch (SQLException e) {
			System.out.println("Issue closing connection");
			e.printStackTrace();
		}
	}
	
	// Method to print generic greeting message
	public static void greeting() {
		System.out.println("+---------------------------+");
		System.out.println("| DOLLARSBANK Welcomes You! |");
		System.out.println("+---------------------------+");
		System.out.println("1. Create New Account");
		System.out.println("2. Login");
		System.out.println("3. Exit\n");
		// UPDATE FOR COLORED MENU
		System.out.println("Enter Choice (1, 2, or 3)");
	}
	
	// Method to print header of account creation menu
	public static void menuMakeAccount() {
		System.out.println("+-------------------------------+");
		System.out.println("| Enter Details For New Account |");
		System.out.println("+-------------------------------+");
		
		// CALL TO CREATE A NEW CUSTOMER AND ACCOUNT
	}
	
	// Method to print header for account login
	public static void loginMenu() {
		System.out.println("+---------------------+");
		System.out.println("| Enter Login Details |");
		System.out.println("+---------------------+");
		
		// CALL TO LOGIN AND CHECK USER CREDENTIALS
	}
	
	// Method to print header for the main menu
	public static void mainMenu() {
		// Add 'add account' feature?
		System.out.println("+---------------------+");
		System.out.println("| WELCOME Customer!!! |");
		System.out.println("+---------------------+");
		System.out.println("1. Deposit Amount");
		System.out.println("2. Withdraw Amount");
		System.out.println("3. Funds Transfer");
		System.out.println("4. View 5 Recent Transactions");
		System.out.println("5. Display Customer Information");
		System.out.println("6. Sign Out\n");
		// UPDATE FOR COLORED MENU
		System.out.println("Enter Choice (1, 2, 3, 4, 5, or 6)");
	}
	
	public static void exitMessage() {
		System.out.println("\n################################################");
		System.out.println("# Thank you for using our banking application! #");
		System.out.println("#               See you soon!                  #");
		System.out.println("################################################\n");
	}

}
