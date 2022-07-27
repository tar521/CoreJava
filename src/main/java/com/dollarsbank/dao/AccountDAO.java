package com.dollarsbank.dao;

import java.sql.SQLException;
import java.util.List;

import com.dollarsbank.model.Account;

public interface AccountDAO {

	public void setAccounts(int userId) throws SQLException;

	public boolean addAccount(String username, Account acc);
	
	boolean addAccount(int userId, Account acc);

	public void signOut();

	public List<Account> getAccounts();

	boolean updateAccountBalance(Account acc);

	public String transferToString(int option);

}
