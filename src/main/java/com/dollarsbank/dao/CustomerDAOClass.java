package com.dollarsbank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dollarsbank.application.DollarsBankApplication;
import com.dollarsbank.model.Customer;

public class CustomerDAOClass implements CustomerDAO{
	
	private Connection conn;
	
	public CustomerDAOClass() {
		conn = DollarsBankApplication.conn;
	}
	
	private Customer user;
	
	public Customer getUser() {
		return user;
	}

	public void setUser(Customer user) {
		this.user = user;
	}

	@Override
	public boolean login(String username, String password) throws SQLException {

		PreparedStatement pstmt = conn.prepareStatement("select * from customer where username = ? and password = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		pstmt.setString(1, username);
		pstmt.setString(2, password);

		ResultSet rs = pstmt.executeQuery();
		
		// if empty - reenter information
		if (!rs.next()) {
			return false;
		}
		
		rs.first();
		user = new Customer(rs.getInt("id"), rs.getString("username"),
					rs.getString("password"), rs.getString("name"),
					rs.getString("address"), rs.getString("phone"));
		
		return true;
	}

	
//	private Customer loadCustomer() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	

}
