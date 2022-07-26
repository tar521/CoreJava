package com.dollarsbank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dollarsbank.application.DollarsBankApplication;
import com.dollarsbank.model.Transactions;

public class TransactionDAOClass implements TransactionDAO {

	private Connection conn;
	
	public TransactionDAOClass() {
		conn = DollarsBankApplication.conn;
	}
	
	private List<Transactions> transactions;
	
	@Override
	public List<Transactions> getTransactions() {
		return transactions;
	}

	@Override
	public void setTransactions(int id) throws SQLException {
		transactions = new ArrayList<Transactions>();
		
		PreparedStatement pstmt = conn.prepareStatement("select * from transactions where cust_id = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		pstmt.setInt(1, id);
		
		ResultSet rs = pstmt.executeQuery();
		
		rs.last();
		int count = 0;
		do {
			transactions.add(new Transactions(id, rs.getString("description"), rs.getDouble("trans_balance"), LocalDateTime.parse(rs.getString("timestamp")), rs.getInt("account_id")));
			count++;
		}while(rs.previous() && count < 5);
		
		rs.close();
		pstmt.close();
		
	}

	@Override
	public boolean addTransaction(String username, Transactions newTrans) {
		try {
			PreparedStatement usestmt = conn.prepareStatement("SELECT * FROM customer WHERE username = ?");
			usestmt.setString(1, username);
			
			ResultSet rs = usestmt.executeQuery();
			
			int userId = 0;
			if(rs.next()) {
				userId = rs.getInt("id");
			}
			else {
				System.out.println("User not found - Please Terminate Program");
				return false;
			}

			rs.close();
			usestmt.close();
			
			PreparedStatement accstmt = conn.prepareStatement("SELECT * FROM account WHERE cust_id = ?");
			accstmt.setInt(1, userId);
			
			rs = accstmt.executeQuery();
			
			int accId = 0;
			if(rs.next()) {
				accId = rs.getInt("id");
			}
			else {
				System.out.println("User not found - Please Terminate Program");
				return false;
			}

			rs.close();
			accstmt.close();
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO transactions(cust_id, description, trans_balance, timestamp, account_id) VALUES (?, ?, ?, ?, ?)");
			pstmt.setInt(1, userId);
			pstmt.setString(2, newTrans.getDescription());
			pstmt.setDouble(3, newTrans.getTransBalance());
			pstmt.setString(4, newTrans.getTimestamp().toString());
			pstmt.setInt(5, accId);
			
			int result = pstmt.executeUpdate();
			
			pstmt.close();
			
			if (result > 0) {
				return true;
			}
			
		}catch (SQLException e) {
			System.out.println("Error Occurred - Please Terminate Program");
		}
		return false;
	}
	
	@Override
	public void signOut() {
		transactions = null;
	}

	
}
