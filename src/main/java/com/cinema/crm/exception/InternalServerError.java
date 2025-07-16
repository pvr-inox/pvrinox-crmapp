package com.cinema.crm.exception;

public class InternalServerError extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public InternalServerError (String message) {
		super(message);
	}
}
