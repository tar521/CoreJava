package com.dollarsbank.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.dollarsbank.application.DollarsBankApplication;
import com.dollarsbank.dao.AccountDAO;
import com.dollarsbank.dao.AccountDAOClass;
import com.dollarsbank.dao.CustomerDAO;
import com.dollarsbank.dao.CustomerDAOClass;
import com.dollarsbank.dao.TransactionDAO;
import com.dollarsbank.dao.TransactionDAOClass;
import com.dollarsbank.exceptions.IllegalOptionException;
import com.dollarsbank.exceptions.PasswordIncorrectException;

public class DollarsBankController {
	
	public static Scanner sc = new Scanner(System.in);
	private AccountDAO accDAO = new AccountDAOClass();
	private TransactionDAO transDAO = new TransactionDAOClass();
	private CustomerDAO custDAO = new CustomerDAOClass();
	
	public void startUp() {
		
		do {
			// Print header for app opening
			int login = 0;
			DollarsBankApplication.greeting();
			try {
				// Input for startUp options
				int option = sc.nextInt();
				sc.nextLine();
				
				switch (option) {
					case 1: // create user and account
						newUser();
						break;
					case 2: // login
						login = existingUser();
						break;
					case 3: // exit
						break;
					default:
						throw new IllegalOptionException();
				}
				
			
			if (option == 3) {
				return;
			}
			
			
			
			
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Not a listed option. Please input a valid option.\n");
			}
			catch (IllegalOptionException e) {
				System.out.println("Not a listed option. Please input a valid option.\n");
			}
			
		}while(true);
	}
	
	// create account and user
	private void newUser() {
		
	}
	
	private int existingUser() {
		do {
			DollarsBankApplication.loginMenu();
			System.out.println("[Input 'exit' to return to previous menu]");
			System.out.println("Username:");
			String username = sc.nextLine();
			if (username.equalsIgnoreCase("exit")) {
				return -1;
			}
			
			System.out.println("Password:");
			String password = sc.nextLine();
			if (password.equalsIgnoreCase("exit")) {
				return -1;
			}
			
			try {
				if (!custDAO.login(username, password)) {
					throw new PasswordIncorrectException();
				}
				
				
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Connection Error Occurred: Please try again later.\n");
				return 0;
			} catch(PasswordIncorrectException e) {
				System.out.println("Invalid Credentials. Try Again!");
				continue;
			}
			
			
		}while (true);
	}

}
