package com.dollarsbank.model;

import java.time.LocalDateTime;

public class SavingsAccount implements Account {

	private int id;
	private double balance;
	private LocalDateTime dateCreated;
	private final String accountType = "SAVINGS";

	public SavingsAccount() {
		id = -1;
		balance = 0;
		dateCreated = LocalDateTime.now();
	}
	
	public SavingsAccount(int id, double balance) {
		super();
		this.id = id;
		this.balance = balance;
		dateCreated = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public boolean transfer(Account acc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean withdrawal(double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deposit(double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createAccount() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAccount() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "SavingsAccount [id=" + id + ", balance=" + balance + ", dateCreated=" + dateCreated + ", accountType="
				+ accountType + "]";
	}
}
