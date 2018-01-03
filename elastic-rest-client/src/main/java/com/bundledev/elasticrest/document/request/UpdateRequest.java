package com.bundledev.elasticrest.document.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Specific request to handle updates to documents. Only partial updates are
 * supported at the moment.
 */
@Getter
@Setter
@NoArgsConstructor
public class UpdateRequest extends DocumentRequest {

	private Object entity;

	public UpdateRequest(String index, String type, String id) {
		super(index, type, id);
	}
}
