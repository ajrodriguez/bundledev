package com.bundledev.elasticrest.cluster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bundledev.elasticrest.ElasticTestCase;
import com.bundledev.elasticrest.RestClientConfig;
import com.bundledev.elasticrest.TestConfig;
import com.bundledev.elasticrest.cluster.response.ClusterHealth;
import com.bundledev.elasticrest.index.IndexService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestClientConfig.class, TestConfig.class })
public class ClusterServiceTest extends ElasticTestCase {

	@Autowired
	private ClusterService clusterService;

	@Autowired
	private IndexService indexService;

	@Test
	public void checkClusterHealth() throws Exception {
		if (!indexService.indexExist("cluster_test")) {
			indexService.createIndex("cluster_test", "{}");
		}

		ClusterHealth clusterHealth = clusterService.checkClusterHealth();
		assertEquals("docker-cluster", clusterHealth.getClusterName());
		assertEquals(1, clusterHealth.getNumberOfNodes());
		assertEquals("yellow", clusterHealth.getStatus());
		assertTrue(clusterHealth.getActivePrimaryShards() >= 5);
		assertTrue(clusterHealth.getUnassignedShards() >= 5);
		assertFalse(clusterHealth.getTimedOut());
	}

}