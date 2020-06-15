package com.himansh.exceptions;

public class TodoException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TodoException() {
        super();
    }

    public TodoException(String message) {
        super(message);
    }
}
