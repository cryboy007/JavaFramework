package com.robin.core.exception.oper;

public class OperationInWorkException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6631656832465161285L;
	public OperationInWorkException(RuntimeException ex) {
		super(ex);
	}
	public OperationInWorkException(String message) {
		super(message);
	}
}
