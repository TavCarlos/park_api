package com.demoparkapi.exceptions;

public class CodigoUniqueViolationExecption extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CodigoUniqueViolationExecption(String message) {
		super(message);
		}
}
