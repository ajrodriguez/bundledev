package com.bundledev.elasticrest.exception;

/**
 * Exception thrown while interacting with the cluster API.
 */
public class ClusterApiException extends ElasticClientException {

	private static final long serialVersionUID = -6908768193416492454L;

	public ClusterApiException(String message) {
		super(message);
	}

	public ClusterApiException(String message, Throwable cause) {
		super(message, cause);
	}
}
