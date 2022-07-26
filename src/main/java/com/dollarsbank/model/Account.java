package com.dollarsbank.model;

public interface Account {

	public boolean transfer(Account acc);
	public boolean withdrawal(double amount);
	public boolean deposit(double amount);
	public boolean createAccount();
	public boolean deleteAccount();
}
