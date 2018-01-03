package com.bundledev.elasticrest.search.response;

import com.bundledev.elasticrest.document.response.Hits;
import com.bundledev.elasticrest.document.response.Shards;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * <ul>
 * <li>took – time in milliseconds for Elasticsearch to execute the search
 * <li>timed_out – tells us if the search timed out or not
 * <li>_shards – tells us how many shards were searched, as well as a count of
 * the successful/failed searched shards
 * <li>hits – search results
 * 
 * @author igutierrez
 * @param <T>
 */
@Data
@NoArgsConstructor
public class QueryResponse<T> {

	private Long took;

	@JsonProperty(value = "timed_out")
	private Boolean timedOut;

	@JsonProperty(value = "_shards")
	private Shards shards;

	private Hits<T> hits;
}
