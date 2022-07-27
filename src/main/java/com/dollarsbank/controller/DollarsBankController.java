package com.dollarsbank.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dollarsbank.application.DollarsBankApplication;
import com.dollarsbank.dao.AccountDAO;
import com.dollarsbank.dao.AccountDAOClass;
import com.dollarsbank.dao.CustomerDAO;
import com.dollarsbank.dao.CustomerDAOClass;
import com.dollarsbank.dao.TransactionDAO;
import com.dollarsbank.dao.TransactionDAOClass;
import com.dollarsbank.exceptions.IllegalOptionException;
import com.dollarsbank.exceptions.InvalidAccountException;
import com.dollarsbank.exceptions.InvalidWithdrawalException;
import com.dollarsbank.exceptions.PasswordIncorrectException;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.SavingsAccount;
import com.dollarsbank.model.Transactions;
import com.dollarsbank.utility.ColorUtility;

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
				System.out.print(ColorUtility.CYAN_TEXT);
				int option = sc.nextInt();
				sc.nextLine();
				System.out.print(ColorUtility.TEXT_RESET);
				
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
						System.out.print("\033[2J");
						session();
					}
				}
				
				if (option == 3) {
					return;
				}
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println(ColorUtility.RED_TEXT + "Not a listed option. Please input a valid option.\n" + ColorUtility.TEXT_RESET);
			}
			catch (IllegalOptionException e) {
				System.out.println(ColorUtility.RED_TEXT + "Not a listed option. Please input a valid option.\n" + ColorUtility.TEXT_RESET);
			}
			
		}while(true);
	}
	
	// create account and user
	private void newUser() {
		Customer newCust = new Customer();
		System.out.println();
		DollarsBankApplication.menuMakeAccount();
		System.out.println("Customer Name:" + ColorUtility.CYAN_TEXT);
		newCust.setName(sc.nextLine());
		
		System.out.println(ColorUtility.TEXT_RESET + "Customer Address:" + ColorUtility.CYAN_TEXT);
		newCust.setAddress(sc.nextLine());
		
		do {
			System.out.println(ColorUtility.TEXT_RESET +"Customer Username:" + ColorUtility.CYAN_TEXT);
			String temp = sc.nextLine();
			
			if(custDAO.uniqueUsername(temp)) {
				newCust.setUsername(temp);
				break;
			}
			else {
				System.out.println(ColorUtility.RED_TEXT + "Username not available - Try Again!" + ColorUtility.TEXT_RESET);
			}
		}while(true);
			
		String phonePattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

		do {
			System.out.println(ColorUtility.TEXT_RESET + "Customer Contact Number:" + ColorUtility.CYAN_TEXT);
			String temp = sc.nextLine();
			
			if (temp.matches(phonePattern)) {
				newCust.setPhone(temp);
				break;
			}
			else {
				System.out.println(ColorUtility.TEXT_RESET + ColorUtility.RED_TEXT + "Not valid phone number - Try Again!" + ColorUtility.TEXT_RESET);
			}
			
		}while(true);
		
		String passPattern  = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[!@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
		Pattern p = Pattern.compile(passPattern);		
		
		do {
			System.out.println(ColorUtility.TEXT_RESET + "Password: " + ColorUtility.PURPLE_TEXT + "8 Characters Min With Lower, Upper, Number, and Special" + ColorUtility.CYAN_TEXT);
			String temp = sc.nextLine();
			temp = temp.replaceAll("\n", "");
			temp = temp.replaceAll("\\s", "");
			Matcher m = p.matcher(temp);
			
			if (m.matches()) {
				newCust.setPassword(temp);
				break;
			}
			else {
				System.out.println(ColorUtility.TEXT_RESET + ColorUtility.RED_TEXT + "Invalid Password - Try Again!" + ColorUtility.TEXT_RESET);
			}
			
		}while(true);
		
		double min = 5.0;
		SavingsAccount newAcc = new SavingsAccount();
		Transactions newTrans = new Transactions();
		
		do {
			try {
				System.out.println(ColorUtility.TEXT_RESET +"Initial Deposit Amount: " + ColorUtility.PURPLE_TEXT + "Minimum $5.00 (input #'s only)" + ColorUtility.CYAN_TEXT);
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
					System.out.println(ColorUtility.TEXT_RESET + ColorUtility.RED_TEXT + "Invalid initial balance - Try Again!" + ColorUtility.TEXT_RESET);
				}
			} catch (InputMismatchException e ) {
				System.out.println(ColorUtility.TEXT_RESET + ColorUtility.RED_TEXT + "Invalid input for initial balance - Try Again!" + ColorUtility.TEXT_RESET);
				sc.nextLine();
			}
			
		}while(true);
		
		if (custDAO.addCustomer(newCust)) {
			if(accDAO.addAccount(newCust.getUsername(), newAcc)) {
				if(transDAO.addTransaction(newCust.getUsername(), newTrans)) {
					System.out.println(ColorUtility.GREEN_TEXT + "\nAccount Created!\n" + ColorUtility.TEXT_RESET);
				}
			}
		}
	}
	
	private int existingUser() {
		do {
			DollarsBankApplication.loginMenu();
			System.out.println(ColorUtility.PURPLE_TEXT + "[Input 'exit' to return to previous menu]");
			System.out.println(ColorUtility.TEXT_RESET + "Username:" + ColorUtility.CYAN_TEXT);
			String username = sc.nextLine();
			if (username.equalsIgnoreCase("exit")) {
				return -1;
			}
			
			System.out.println(ColorUtility.TEXT_RESET + "Password:" + ColorUtility.CYAN_TEXT);
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
				System.out.println(ColorUtility.TEXT_RESET + ColorUtility.RED_TEXT + "Connection Error Occurred: Please try again later.\n" + ColorUtility.TEXT_RESET);
				return 0;
			} catch(PasswordIncorrectException e) {
				System.out.println(ColorUtility.TEXT_RESET + ColorUtility.RED_TEXT + "Invalid Credentials. Try Again!" + ColorUtility.TEXT_RESET);
				continue;
			}
			
			
		}while (true);
	}
	
	private void session() {
		do {
			System.out.println();
			DollarsBankApplication.mainMenu();
			try {
				System.out.print(ColorUtility.CYAN_TEXT);
				int option = sc.nextInt();
				sc.nextLine();
				System.out.print(ColorUtility.TEXT_RESET);
				
				switch (option) {
				case 1: // Deposit
					accountAction("Deposit");
					break;
				case 2: // Withdraw
					accountAction("Withdrawal");
					break;
				case 3: // Funds transfer
					if (accDAO.getAccounts().size() < 2) {
						System.out.println(ColorUtility.RED_TEXT + "\nCannot perform transfer: You have only one account\n" + ColorUtility.TEXT_RESET);
					} else {
						//accountAction("Transfer");
					}
					break;
				case 4: // view trans
					System.out.println(ColorUtility.BLUE_TEXT + "+-----------------------------+");
					System.out.println("| 5 Most Recent Transactions: |");
					System.out.println("+-----------------------------+\n" + ColorUtility.TEXT_RESET);
					System.out.println(transDAO);
					break;
				case 5: // view customer info
					System.out.print(ColorUtility.TEXT_RESET);
					System.out.println(custDAO.getUser());
					break;
				case 6:
					custDAO.setUser(null);
					accDAO.signOut();
					transDAO.signOut();
					System.out.print(ColorUtility.TEXT_RESET);
					System.out.println("\nSigning Out...\n");
					System.out.print(ColorUtility.TEXT_RESET);
					return;
				default:
					throw new IllegalOptionException();
				}
			}catch(InputMismatchException e) {
				System.out.println(ColorUtility.RED_TEXT + "\nInvalid input - Please input a listed option\n" + ColorUtility.TEXT_RESET);
				sc.nextLine();
			}catch(IllegalOptionException e) {
				System.out.println(ColorUtility.RED_TEXT + "\nNot a listed option - Please input a listed option\n" + ColorUtility.TEXT_RESET);
			}
			
			
		}while(true);
	}
	
	private void accountAction(String action) {
		String menu = action.replaceAll("[a-zA-Z]", "-");
		System.out.println();
		System.out.println(ColorUtility.BLUE_TEXT + "+-" + menu + "--------+");
		System.out.println("| " + action + " Wizard |");
		System.out.println("+-" + menu + "--------+" + ColorUtility.TEXT_RESET);
		do {
			try {
				System.out.println(ColorUtility.TEXT_RESET + ColorUtility.PURPLE_TEXT + "[To cancel " + action + " input -1]" + ColorUtility.TEXT_RESET);
				System.out.println("Select Account for " + action + ColorUtility.CYAN_TEXT);
				System.out.println(accDAO);
				System.out.println(ColorUtility.TEXT_RESET + "Account ID: " + ColorUtility.CYAN_TEXT);
				
				int option = sc.nextInt();
				sc.nextLine();
				
				System.out.print(ColorUtility.TEXT_RESET);
				
				if (option == -1) {
					return;
				}
				//VALID USER ACCOUNT
				SavingsAccount acc = validUserAccount(option);
				if (acc == null) {
					throw new InvalidAccountException();
				}
				
				System.out.println(ColorUtility.TEXT_RESET + "\nInput non-zero amount to " + action + ":" + ColorUtility.CYAN_TEXT);
				double amount = sc.nextDouble();
				sc.nextLine();
				System.out.print(ColorUtility.TEXT_RESET);
				
				if (amount <= 0) {
					throw new InputMismatchException();
				}
				
				switch (action) {
				case "Deposit":
					acc.setBalance(amount + acc.getBalance());
					Transactions dep = new Transactions(custDAO.getUser().getId(), "Deposit of " + amount + " for account ", acc.getBalance(), LocalDateTime.now(), acc.getId());
					accDAO.updateAccountBalance(acc);
					transDAO.addTransaction(dep);
					System.out.println(ColorUtility.GREEN_TEXT + "\nSuccessful Deposit For Account " + acc.getId() + "!");
					System.out.println(ColorUtility.PURPLE_TEXT + "Current Balance: " + acc.getBalance() + "\n" + ColorUtility.TEXT_RESET);
					break;
				case "Withdrawal":
					double newbal = acc.getBalance() - amount;
					if (newbal >= 0) {
						acc.setBalance(newbal);
						Transactions with = new Transactions(custDAO.getUser().getId(), "Withdrawal of " + amount + " for account ", acc.getBalance(), LocalDateTime.now(), acc.getId());
						accDAO.updateAccountBalance(acc);
						transDAO.addTransaction(with);
						System.out.println(ColorUtility.GREEN_TEXT + "\nSuccessful Withdrawal For Account " + acc.getId() + "!");
						System.out.println(ColorUtility.PURPLE_TEXT + "Current Balance: " + acc.getBalance() + "\n" + ColorUtility.TEXT_RESET);
					}
					else {
						throw new InvalidWithdrawalException();
					}
					break;
				case "Transfer":
					break;
				}
				
				return;
				
			} catch(InputMismatchException e) {
				System.out.println(ColorUtility.RED_TEXT + "\nInput is not an account ID or is an invalid amount. Please chose a listed option.\n" + ColorUtility.TEXT_RESET);
			}catch (InvalidAccountException e) {
				System.out.println(ColorUtility.RED_TEXT + "\nSelected Account is not Associated with your account. Try Again!" + ColorUtility.TEXT_RESET);
			}catch (InvalidWithdrawalException e) {
				System.out.println(ColorUtility.RED_TEXT + "\nCannot Withdraw more than account balance." + ColorUtility.TEXT_RESET);
			}
		} while(true);
	}

	private SavingsAccount validUserAccount(int searchId) {
		SavingsAccount result = null;
		for (SavingsAccount s : accDAO.getAccounts()) {
			if (s.getId() == searchId) {
				result = s;
				break;
			}
		}
		return result;
	}
	
}
