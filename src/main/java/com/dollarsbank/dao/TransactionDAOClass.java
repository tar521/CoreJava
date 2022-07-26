package com.dollarsbank.dao;

import java.sql.Connection;

import com.dollarsbank.application.DollarsBankApplication;

public class TransactionDAOClass implements TransactionDAO {

	private Connection conn;
	
	public TransactionDAOClass() {
		conn = DollarsBankApplication.conn;
	}
}
