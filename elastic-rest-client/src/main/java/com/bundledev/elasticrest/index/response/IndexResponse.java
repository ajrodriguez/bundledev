package com.bundledev.elasticrest.index.response;

import com.bundledev.elasticrest.document.response.Shards;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class IndexResponse {

	@JsonProperty(value = "_shards")
	private Shards shards;
}
