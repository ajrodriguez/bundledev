package com.bundledev.elasticrest;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.sniff.Sniffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

import com.bundledev.elasticrest.listener.LoggingFailureListener;

import lombok.extern.slf4j.Slf4j;

/**
 * Factory bean for creating the RestClient instance(s)
 */
@Slf4j
@Component
public class RestClientFactoryBean extends AbstractFactoryBean<RestClient> {

	@Qualifier("clientProperties")
	@Autowired
	private RestClientProperties properties;

	// Minimal library that allows to automatically discover nodes from a
	// running Elasticsearch cluster and set them to an existing RestClient
	// instance. It retrieves by default the nodes that belong to the cluster
	// using the Nodes Info API and uses Jackson to parse the obtained JSON
	// response.
	private Sniffer sniffer;

	private final LoggingFailureListener loggingFailureListener;

	@Autowired
	public RestClientFactoryBean(LoggingFailureListener loggingFailureListener) {
		this.loggingFailureListener = loggingFailureListener;
	}

	@Override
	protected RestClient createInstance() throws Exception {

		HttpHost[] hosts = new HttpHost[properties.getHostnames().length];
		for (int i = 0; i < hosts.length; i++) {
			hosts[i] = HttpHost.create(properties.getHostnames()[i]);
		}

		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		// TODO Credentials in property file
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "changeme"));

		RestClient restClient = RestClient.builder(hosts).setFailureListener(loggingFailureListener)
				.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
					@Override
					public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
						return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
					}
				}).build();
		this.sniffer = Sniffer.builder(restClient).build();

		return restClient;
	}

	@Override
	protected void destroyInstance(RestClient instance) throws Exception {
		try {
			log.info("method:{}.{}()|message:\'{}\'|argument[s]:{}", "RestClientFactoryBean", "destroyInstance",
					"Closing the Elasticsearch sniffer");
			instance.close();
			this.sniffer.close();
		} catch (IOException exc) {
			log.warn("method:{}.{}|cause:\'{}\'|exception:\'{}\'", "RestClientFactoryBean", "destroyInstance",
					"Failed to close the Elasticsearch sniffer", exc.getMessage());
		}
	}

	@Override
	public Class<?> getObjectType() {
		return RestClient.class;
	}
}
