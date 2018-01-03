package com.bundledev.elasticrest.exception;

/**
 * Exception thrown when eu.luminis.elastic throws an error.
 */
public class QueryExecutionException extends RuntimeException {

	private static final long serialVersionUID = -8208618461428281289L;

	public QueryExecutionException(String message) {
        super(message);
    }

    public QueryExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
