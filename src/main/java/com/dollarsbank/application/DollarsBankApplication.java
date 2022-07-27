package com.dollarsbank.application;

import java.sql.Connection;
import java.sql.SQLException;

import com.dollarsbank.controller.DollarsBankController;
import com.dollarsbank.utility.ColorUtility;

public class DollarsBankApplication {
	
	public static Connection conn;
	
	public static void main(String[] args) {
		
		conn = ConnManagerWithProperties.getConnection();
		
		if (conn == null) {
			System.out.println(ColorUtility.RED_TEXT + "Could not connect to DB");
			System.out.println("Now exiting..." + ColorUtility.TEXT_RESET);
			return;
		}
		
		DollarsBankController ctl = new DollarsBankController();
		ctl.startUp();
		
		try {
			conn.close();
			exitMessage();
		} catch (SQLException e) {
			System.out.println(ColorUtility.RED_TEXT + "Issue closing connection");
			e.printStackTrace();
			System.out.println(ColorUtility.TEXT_RESET);
		}
	}
	
	// Method to print generic greeting message
	public static void greeting() {
		System.out.println(ColorUtility.BLUE_TEXT + "+---------------------------+");
		System.out.println("| DOLLARSBANK Welcomes You! |");
		System.out.println("+---------------------------+" + ColorUtility.TEXT_RESET);
		System.out.println("1. Create New Account");
		System.out.println("2. Login");
		System.out.println("3. Exit\n");
		
		System.out.println(ColorUtility.GREEN_TEXT + "Enter Choice (1, 2, or 3)" + ColorUtility.TEXT_RESET);
	}
	
	// Method to print header of account creation menu
	public static void menuMakeAccount() {
		System.out.println(ColorUtility.BLUE_TEXT + "+-------------------------------+");
		System.out.println("| Enter Details For New Account |");
		System.out.println("+-------------------------------+" + ColorUtility.TEXT_RESET);
		
	}
	
	// Method to print header for account login
	public static void loginMenu() {
		System.out.println(ColorUtility.BLUE_TEXT + "+---------------------+");
		System.out.println("| Enter Login Details |");
		System.out.println("+---------------------+" + ColorUtility.TEXT_RESET);
		
	}
	
	// Method to print header for the main menu
	public static void mainMenu() {
		System.out.println(ColorUtility.BLUE_TEXT + "+---------------------+");
		System.out.println("| WELCOME Customer!!! |");
		System.out.println("+---------------------+" + ColorUtility.TEXT_RESET);
		System.out.println("1. Deposit Amount");
		System.out.println("2. Withdraw Amount");
		System.out.println("3. Funds Transfer");
		System.out.println("4. View 5 Recent Transactions");
		System.out.println("5. Display Customer Information");
		System.out.println("6. Open New Account");
		System.out.println("7. Sign Out\n");
		
		System.out.println(ColorUtility.GREEN_TEXT + "Enter Choice (1, 2, 3, 4, 5, 6, or 7)" + ColorUtility.TEXT_RESET);
	}
	
	public static void exitMessage() {
		System.out.println(ColorUtility.PURPLE_TEXT + "\n################################################");
		System.out.println("# Thank you for using our banking application! #");
		System.out.println("#               See you soon!                  #");
		System.out.println("################################################\n" + ColorUtility.TEXT_RESET);
	}
	
	public static void newAccountMenu() {
		System.out.println(ColorUtility.BLUE_TEXT + "+------------------+");
		System.out.println("| Open New Account |");
		System.out.println("+------------------+" + ColorUtility.TEXT_RESET);
		System.out.println("1. Open Savings Account");
		System.out.println("2. Open Checking Account\n");
		
		System.out.println(ColorUtility.GREEN_TEXT + "Enter Choice (1 or 2)" + ColorUtility.TEXT_RESET);
	}

}
