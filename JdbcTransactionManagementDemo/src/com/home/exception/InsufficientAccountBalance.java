package com.home.exception;

public class InsufficientAccountBalance extends Exception {
	
private static final long serialVersionUID = 1380349681562411821L;
	
	public InsufficientAccountBalance(String message) {
		super(message);
	}

}
