package com.bundledev.elasticrest.document.request;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This request can be used to create a request for one specific entity
 * specified by the index, type and id.
 * </p>
 * <p>
 * If your entity object contains the field <code>id</code>, but you want to
 * generate the id in Elasticsearch, you might want to obtain the id when
 * executing a query and still add it to the entity object. In that case you can
 * use {@link #addId} true to indicate to copy the id into the entity object.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class QueryByIdRequest extends DocumentRequest {

	@SuppressWarnings("rawtypes")
	private TypeReference typeReference;

	private Boolean addId = false;

	public QueryByIdRequest(String index, String type, String id) {
		super(index, type, id);
	}
}
