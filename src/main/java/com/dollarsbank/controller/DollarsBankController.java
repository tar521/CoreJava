package com.dollarsbank.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.SavingsAccount;
import com.dollarsbank.model.Transactions;

public class DollarsBankController {
	
	public static Scanner sc = new Scanner(System.in);
	private AccountDAO accDAO = new AccountDAOClass();
	private TransactionDAO transDAO = new TransactionDAOClass();
	private CustomerDAO custDAO = new CustomerDAOClass();
	
	public void startUp() {
		
		do {
			// Print header for app opening
			int login = -1;
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
				
				if (option == 2) {
					if (login == -1) {
						continue;
					}
					if (login == 0) {
						return;
					}
					if (login == 1) {
						// MAIN MENU
					}
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
		Customer newCust = new Customer();
		System.out.println();
		DollarsBankApplication.menuMakeAccount();
		System.out.println("Customer Name:");
		newCust.setName(sc.nextLine());
		
		System.out.println("Customer Address:");
		newCust.setAddress(sc.nextLine());
		
		do {
			System.out.println("Customer Username:");
			String temp = sc.nextLine();
			
			if(custDAO.uniqueUsername(temp)) {
				newCust.setUsername(temp);
				break;
			}
			else {
				System.out.println("Username not available - Try Again!");
			}
		}while(true);
			
		String phonePattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

		do {
			System.out.println("Customer Contact Number:");
			String temp = sc.nextLine();
			
			if (temp.matches(phonePattern)) {
				newCust.setPhone(temp);
				break;
			}
			else {
				System.out.println("Not valid phone number - Try Again!");
			}
			
		}while(true);
		
		String passPattern  = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
		
		do {
			System.out.println("Password: 8 Characters Min With Lower, Upper, Number, and Special");
			String temp = sc.nextLine();
			
			if (temp.matches(passPattern)) {
				newCust.setPassword(temp);
				break;
			}
			else {
				System.out.println("Invalid Password - Try Again!");
			}
			
		}while(true);
		
		double min = 5.0;
		SavingsAccount newAcc = new SavingsAccount();
		Transactions newTrans = new Transactions();
		
		do {
			try {
				System.out.println("Initial Deposit Amount: Minimum $5.00 (input #'s only)");
				double temp = sc.nextDouble();
				sc.nextLine();
				
				if (temp >= min) {
					newAcc.setBalance(temp);
					newTrans.setTransBalance(temp);
					newTrans.setTimestamp(LocalDateTime.now());
					newTrans.setDescription("Initial Deposit Amount in account ");
					break;
				}
				else {
					System.out.println("Invalid initial balance - Try Again!");
				}
			} catch (InputMismatchException e ) {
				System.out.println("Invalid input for initial balance - Try Again!");
				sc.nextLine();
			}
			
		}while(true);
		
		if (custDAO.addCustomer(newCust)) {
			if(accDAO.addAccount(newCust.getUsername(), newAcc)) {
				if(transDAO.addTransaction(newCust.getUsername(), newTrans)) {
					System.out.println("\n Account Created!\n");
				}
			}
		}
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
				accDAO.setAccounts(custDAO.getUser().getId());
				transDAO.setTransactions(custDAO.getUser().getId());
				return 1;
				
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
