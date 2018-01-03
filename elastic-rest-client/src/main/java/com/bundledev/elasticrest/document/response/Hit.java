package com.bundledev.elasticrest.document.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hit<T> {

	@JsonProperty(value = "_index")
	private String index;

	@JsonProperty(value = "_type")
	private String type;

	@JsonProperty(value = "_id")
	private String id;

	@JsonProperty(value = "_score")
	private Double score;

	@JsonProperty(value = "_source")
	private T source;
}
