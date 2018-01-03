package com.bundledev.elasticrest.exception;

public class IndexDocumentException extends RuntimeException {
    
	private static final long serialVersionUID = 312357280236560527L;

	public IndexDocumentException(String message) {
        super(message);
    }

    public IndexDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
