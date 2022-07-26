package com.dollarsbank.dao;

import java.sql.Connection;

import com.dollarsbank.application.DollarsBankApplication;

public class AccountDAOClass implements AccountDAO{
	
	private Connection conn;
	
	public AccountDAOClass() {
		conn = DollarsBankApplication.conn;
	}

}
