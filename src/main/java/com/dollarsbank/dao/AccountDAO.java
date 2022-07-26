package com.dollarsbank.dao;

import java.sql.SQLException;

import com.dollarsbank.model.Customer;
import com.dollarsbank.model.SavingsAccount;

public interface AccountDAO {

	public void setAccounts(int userId) throws SQLException;

	public boolean addAccount(String username,SavingsAccount acc);

}
