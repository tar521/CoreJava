package com.dollarsbank.dao;

import java.sql.SQLException;

import com.dollarsbank.model.Transactions;

public interface TransactionDAO {

	public void setTransactions(int id) throws SQLException;

	public boolean addTransaction(String username, Transactions newTrans);

}
