package com.dollarsbank.exceptions;

public class IllegalOptionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public IllegalOptionException() {
		super("Inputted option does not exist.\nPlease input a listed option.");
	}
}
