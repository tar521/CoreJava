package com.dollarsbank.exceptions;

public class InvalidWithdrawalException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InvalidWithdrawalException() {
		super("Cannot Withdraw more than current balance.");
	}	
}
