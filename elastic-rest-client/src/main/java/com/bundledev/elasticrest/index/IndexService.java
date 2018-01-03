package com.bundledev.elasticrest.index;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bundledev.elasticrest.document.response.Shards;
import com.bundledev.elasticrest.exception.ClusterApiException;
import com.bundledev.elasticrest.exception.IndexApiException;
import com.bundledev.elasticrest.exception.QueryExecutionException;
import com.bundledev.elasticrest.index.response.IndexResponse;
import com.bundledev.elasticrest.util.ClientEndpoint;
import com.bundledev.elasticrest.util.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Exposes index API related services.
 */
@Slf4j
@Service
public class IndexService {

	private final RestClient client;
	private final ObjectMapper objectMapper;

	@Autowired
	public IndexService(RestClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}

	public Boolean indexExist(String indexName) {

		try {
			Response response = client.performRequest(HttpMethod.HEAD.name(), indexName);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == HttpStatus.SC_OK) {
				return true;
			} else if (statusCode == HttpStatus.SC_NOT_FOUND) {
				return false;
			} else {
				log.warn("method:{}.{}()|message:\'{}\'|argument[s]:{}", "IndexService", "indexExist",
						"Problem while checking index existence", response.getStatusLine().getReasonPhrase());
				throw new QueryExecutionException("Could not check index existence, status code is " + statusCode);
			}
		} catch (IOException e) {
			log.error("method:{}.{}()|message:\'{}\'|exception:{}", "DocumentService", "remove",
					"Problem while verifying if index exists", e.getMessage() != null ? e.getMessage() : "NULL");
			throw new IndexApiException("Error when checking for existing index.");
		}
	}

	public void createIndex(String indexName, String requestBody) {

		try {
			HttpEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
			Response response = client.performRequest(HttpMethod.PUT.name(), indexName, new Hashtable<>(), entity);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
				log.warn("method:{}.{}()|message:\'{}\'|argument[s]:{}", "IndexService", "createIndex",
						"Problem while creating an index", response.getStatusLine().getReasonPhrase());
				throw new QueryExecutionException("Could not create index, status code is " + statusCode);
			}

		} catch (UnsupportedEncodingException e) {
			log.error("method:{}.{}()|message:\'{}\'|exception:{}", "IndexService", "createIndex",
					"Problem converting the request body into an http entity",
					e.getMessage() != null ? e.getMessage() : "NULL");
			throw new IndexApiException("Problem converting the request body into an http entity", e);
		} catch (IOException e) {
			log.error("method:{}.{}()|message:\'{}\'|exception:{}", "IndexService", "createIndex",
					"Problem creating new index", e.getMessage() != null ? e.getMessage() : "NULL");
			throw new IndexApiException("Problem creating new index", e);
		}
	}

	public void dropIndex(String indexName) {
		try {
			Response response = client.performRequest(HttpMethod.DELETE.name(), indexName);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= HttpStatus.SC_INTERNAL_SERVER_ERROR) {
				log.warn("method:{}.{}()|message:\'{}\'|argument[s]:{}", "IndexService", "dropIndex",
						"Problem while deleting an index", response.getStatusLine().getReasonPhrase());
				throw new QueryExecutionException("Could not delete index, status code is " + statusCode);
			}
		} catch (IOException e) {
			log.error("method:{}.{}()|message:\'{}\'|exception:{}", "IndexService", "dropIndex",
					"Problem deleting an index", e.getMessage() != null ? e.getMessage() : "NULL");
			throw new IndexApiException("Problem deleting index", e);
		}

	}

	public void refreshIndexes(String... names) {
		try {
			String endpoint = ClientEndpoint.INDEX_REFRESH_ENDPOINT;
			if (names.length > 0) {
				endpoint = "/" + String.join(",", names) + endpoint;
			}

			Response response = client.performRequest(HttpMethod.POST.name(), endpoint);

			if (response.getStatusLine().getStatusCode() >= HttpStatus.SC_BAD_REQUEST) {
				log.warn("method:{}.{}()|message:\'{}\'|argument[s]:{}", "IndexService", "refreshIndexes",
						"Problem while refreshing indexes", String.join(",", names));
			}

			if (log.isDebugEnabled()) {
				HttpEntity entity = response.getEntity();

				IndexResponse refreshResponse = objectMapper.readValue(entity.getContent(), IndexResponse.class);
				Shards shards = refreshResponse.getShards();
				log.debug("method:{}.{}()|message:\'{}\'|argument[s]:{}", "IndexService", "refreshIndexes",
						"Shards refreshed", "total: " + shards.getTotal() + ", successfull: " + shards.getSuccessful()
								+ ", failed: " + shards.getFailed());
			}
		} catch (IOException e) {
			log.error("method:{}.{}()|message:\'{}\'|exception:{}", "IndexService", "refreshIndexes",
					"Problem while executing refresh request", e.getMessage() != null ? e.getMessage() : "NULL");
			throw new ClusterApiException("Error when refreshing indexes." + e.getMessage());
		}
	}
}
