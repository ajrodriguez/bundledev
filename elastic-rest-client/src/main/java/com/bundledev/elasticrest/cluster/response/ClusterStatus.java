package com.bundledev.elasticrest.cluster.response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ClusterStatus {

	GREEN("green"), YELLOW("yellow"), RED("red");

	private final String status;

	private ClusterStatus(String status) {
		this.status = status;
	}

	@JsonValue
	public String getStatus() {
		return status;
	}
}
