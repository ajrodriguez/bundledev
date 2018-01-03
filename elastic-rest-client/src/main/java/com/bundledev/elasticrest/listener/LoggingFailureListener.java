package com.bundledev.elasticrest.listener;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingFailureListener extends RestClient.FailureListener {

	@Override
	public void onFailure(HttpHost host) {

		log.warn("method:{}.{}()|message:\'{}\'|argument[s]:{}", "LoggingFailureListener", "onFailure",
				"The following host just failed",
				host.getHostName().concat(":").concat(String.valueOf(host.getPort())));
	}
}
