package com.bundledev.elasticrest.exception;

import java.text.MessageFormat;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * Parent exception for all custom Elastic based exceptions while using the
 * client.
 */
@Slf4j
public class ElasticClientException extends RuntimeException {

	private static final long serialVersionUID = 7471226786467005590L;

	public ElasticClientException(String message) {
		super(message);
	}

	public ElasticClientException(String message, Throwable cause) {
		super(message, cause);
	}

	static String createMessage(final String template, final Object... args) {
		if (template != null && !template.isEmpty()) {
			final String message = MessageFormat.format(template, args);
			return message;
		} else {
			log.info("method:{}.{}()|message:\'{}\'|argument[s]:{}", "ElasticClientException", "createMessage",
					"Not exists value for the template passed by parameter!", Arrays.toString(args));
		}
		return null;
	}
}
