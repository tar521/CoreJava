package com.dollarsbank.dao;

import java.sql.SQLException;

import com.dollarsbank.model.Customer;

public interface CustomerDAO {

	
	public boolean login(String username, String password) throws SQLException;
	//public Customer loadCustomer();

	public Customer getUser();

	public boolean uniqueUsername(String temp);

	public boolean addCustomer(Customer newCust);
}
