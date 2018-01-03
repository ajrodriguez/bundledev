package com.bundledev.elasticrest.cluster;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bundledev.elasticrest.cluster.response.ClusterHealth;
import com.bundledev.elasticrest.exception.ClusterApiException;
import com.bundledev.elasticrest.util.ClientEndpoint;
import com.bundledev.elasticrest.util.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Service that provides access to the Elastic cluster services.
 */
@Slf4j
@Service
public class ClusterService {

	private final RestClient client;
	private final ObjectMapper objectMapper;

	@Autowired
	public ClusterService(RestClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}

	/**
	 * Returns the current health of the cluster as a {@link ClusterHealth}
	 * object.
	 *
	 * @return ClusterHealth containing the basic properties of the health of
	 *         the cluster
	 */
	public ClusterHealth checkClusterHealth() {
		try {
			Response response = client.performRequest(HttpMethod.GET.name(), ClientEndpoint.CLUSTER_HEALTH_ENDPOINT);
			HttpEntity entity = response.getEntity();
			return objectMapper.readValue(entity.getContent(), ClusterHealth.class);

		} catch (IOException e) {

			log.error("method:{}.{}()|message:\'{}\'|exception:{}", "ClusterService", "checkClusterHealth",
					"Problem while executing request", e.getMessage() != null ? e.getMessage() : "NULL");
			throw new ClusterApiException("Error when checking the health of the cluster");
		}
	}

}
