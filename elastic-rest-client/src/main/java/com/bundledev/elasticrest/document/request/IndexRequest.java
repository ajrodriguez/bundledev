package com.bundledev.elasticrest.document.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request to use when inserting a new document, or overwriting an existing
 * document. If you do not provide an <code>id</code>, a POST is used, so an id
 * is generated by Elastic. If you do provide an <code>id</code>, a PUT is used
 * and that id is used.
 */
@Getter
@Setter
@NoArgsConstructor
public class IndexRequest extends DocumentRequest {

	private Object entity;

	public IndexRequest(String index, String type) {
		super(index, type, null);
	}

	public IndexRequest(String index, String type, String id) {
		super(index, type, id);
	}
}