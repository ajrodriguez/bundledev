package com.bundledev.elasticrest.exception;

/**
 * Exception thrown when something goes wrong in the helper classes.
 */
public class HelperException extends RuntimeException {
	

	private static final long serialVersionUID = -3991370318114320939L;

	public HelperException(String message) {
        super(message);
    }

    public HelperException(String message, Throwable cause) {
        super(message, cause);
    }
}
