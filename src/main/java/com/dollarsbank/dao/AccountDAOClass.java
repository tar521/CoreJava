package com.dollarsbank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dollarsbank.application.DollarsBankApplication;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.SavingsAccount;

public class AccountDAOClass implements AccountDAO{
	
	private Connection conn;
	
	public AccountDAOClass() {
		conn = DollarsBankApplication.conn;
	}
	
	private List<SavingsAccount> accounts;
	
	@Override
	public void setAccounts(int userId) throws SQLException{
		
		accounts = new ArrayList<SavingsAccount>();
		
		PreparedStatement pstmt = conn.prepareStatement("select * from account where cust_id = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		pstmt.setInt(1, userId);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			accounts.add(new SavingsAccount(rs.getInt("id"), rs.getDouble("balance")));
		}
		rs.close();
		pstmt.close();
	}

	@Override
	public boolean addAccount(String username, SavingsAccount acc) {
		try {
			PreparedStatement prestmt = conn.prepareStatement("SELECT * FROM customer WHERE username = ?");
			prestmt.setString(1, username);
			
			ResultSet rs = prestmt.executeQuery();
			
			int userId = 0;
			if(rs.next()) {
				userId = rs.getInt("id");
			}
			else {
				System.out.println("User not found - Please Terminate Program");
				return false;
			}

			rs.close();
			prestmt.close();
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO account(balance, date_created, type, cust_id) VALUES (?, ?, ?, ?)");
			pstmt.setDouble(1, acc.getBalance());
			pstmt.setString(2, acc.getDateCreated().toString());
			pstmt.setString(3, acc.getAccountType());
			pstmt.setInt(4, userId);
			
			int result = pstmt.executeUpdate();
			
			pstmt.close();
			
			if (result > 0) {
				return true;
			}
		}catch(SQLException e) {
			System.out.println("Error Occurred - Please Terminate Program");
		}
		return false;
	}

}
