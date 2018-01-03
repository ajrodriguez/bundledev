package com.bundledev.elasticrest.document.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Response object used when asking for a document by Id. The response contains
 * the entity as provided by the <code>TypeReference</code> in the
 * {@link com.bundledev.elasticrest.document.request.QueryByIdRequest}.
 * 
 * @param <T>
 *            The <code>Entity</code> to return.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetByIdResponse<T> {

	@JsonProperty(value = "_index")
	private String index;

	@JsonProperty(value = "_type")
	private String type;

	@JsonProperty(value = "_id")
	private String id;

	@JsonProperty(value = "found")
	private Boolean found;

	@JsonProperty(value = "_version")
	private int version;

	@JsonProperty(value = "_source")
	private T source;
}
