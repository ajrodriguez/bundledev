package com.bundledev.elasticrest.cluster.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClusterHealth {

	@JsonProperty(value = "cluster_name")
	private String clusterName;

	@JsonProperty(value = "status")
	private String status;

	@JsonProperty(value = "number_of_nodes")
	private int numberOfNodes;

	@JsonProperty(value = "number_of_data_nodes")
	private Object numberOfDataNodes;

	@JsonProperty(value = "timed_out")
	private Boolean timedOut;

	@JsonProperty(value = "active_primary_shards")
	private int activePrimaryShards;

	@JsonProperty(value = "active_shards")
	private int activeShards;

	@JsonProperty(value = "relocating_shards")
	private int relocatingShards;

	@JsonProperty(value = "initializing_shards")
	private int initializingShards;

	@JsonProperty(value = "unassigned_shards")
	private int unassignedShards;

	@JsonProperty(value = "delayed_unassigned_shards")
	private int delayedUnassignedShards;

	@JsonProperty(value = "number_of_pending_tasks")
	private int numberOfPendingTasks;

	@JsonProperty(value = "number_of_in_flight_fetch")
	private int numberOfInFlightFetch;

	@JsonProperty(value = "task_max_waiting_in_queue_millis")
	private int taskMaxWaitingInQueueMillis;
}
