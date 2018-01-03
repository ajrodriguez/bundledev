package com.bundledev.elasticrest.document.request;

import lombok.NoArgsConstructor;

/**
 * Request used to provide the parameters that are required to remove a
 * document.
 */
@NoArgsConstructor
public class DeleteRequest extends DocumentRequest {

	public DeleteRequest(String index, String type, String id) {
		super(index, type, id);
	}
}
