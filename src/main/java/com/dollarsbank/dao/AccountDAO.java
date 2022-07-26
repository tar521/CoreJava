package com.dollarsbank.dao;

import java.sql.SQLException;
import java.util.List;

import com.dollarsbank.model.Customer;
import com.dollarsbank.model.SavingsAccount;

public interface AccountDAO {

	public void setAccounts(int userId) throws SQLException;

	public boolean addAccount(String username,SavingsAccount acc);

	public void signOut();

	public List<SavingsAccount> getAccounts();

	boolean updateAccountBalance(SavingsAccount acc);

}
