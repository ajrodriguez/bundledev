package com.bundledev.elasticrest.indicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import com.bundledev.elasticrest.cluster.ClusterService;
import com.bundledev.elasticrest.cluster.response.ClusterHealth;
import com.bundledev.elasticrest.cluster.response.ClusterStatus;

@Component
public class ElasticHealthIndicator extends AbstractHealthIndicator {

	private static final String CLUSTER_NAME_KEY = "clusterName";
	private static final String NUMBER_NODES_KEY = "numberOfNodes";

	private final ClusterService clusterService;

	@Autowired
	public ElasticHealthIndicator(ClusterService clusterService) {
		this.clusterService = clusterService;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {

		ClusterHealth clusterHealth = clusterService.checkClusterHealth();

		if (clusterHealth != null) {
			switch (ClusterStatus.valueOf(clusterHealth.getStatus())) {
			case GREEN:
			case YELLOW:
				builder.up();
				break;
			case RED:
			default:
				builder.down();
				break;
			}
			builder.withDetail(CLUSTER_NAME_KEY, clusterHealth.getClusterName());
			builder.withDetail(NUMBER_NODES_KEY, clusterHealth.getNumberOfNodes());
		}
	}
}
