package com.dollarsbank.exceptions;

public class PasswordIncorrectException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public PasswordIncorrectException() {
		super("Username or Password is incorrect");
	}

}
