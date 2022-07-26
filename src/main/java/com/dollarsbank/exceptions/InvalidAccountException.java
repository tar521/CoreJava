package com.dollarsbank.exceptions;

public class InvalidAccountException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidAccountException() {
		super("Selected Account is not Associated with user.");
	}

}
