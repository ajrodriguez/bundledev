package com.bundledev.elasticrest.document.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexResponse {

	@JsonProperty(value = "_index")
	private String index;

	@JsonProperty(value = "_type")
	private String type;

	@JsonProperty(value = "_id")
	private String id;

	@JsonProperty(value = "_version")
	private long version;

	@JsonProperty(value = "result")
	private String result;

	@JsonProperty(value = "created")
	private Boolean created;

	@JsonProperty(value = "_shards")
	private Shards shards;

	@JsonProperty(value = "forced_refresh")
	private Boolean forcedRefresh;
}
