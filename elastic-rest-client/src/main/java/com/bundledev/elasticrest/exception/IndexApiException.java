package com.bundledev.elasticrest.exception;

/**
 * Exception thrown when having problems interacting with the index api.
 */
public class IndexApiException extends ElasticClientException {
    
	private static final long serialVersionUID = 5540335341394287445L;

	public IndexApiException(String message) {
        super(message);
    }

    public IndexApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
