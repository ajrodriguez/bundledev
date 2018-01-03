package com.bundledev.elasticrest.document.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hits<T> {

	private List<Hit<T>> hits;

	private Long total;

	@JsonProperty("max_score")
	private Double maxScore;
}
