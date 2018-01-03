package com.bundledev.elasticrest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component(value = "clientProperties")
@ConfigurationProperties(prefix = "elastic-rest")
public class RestClientProperties {

	// relaxed binding
	// crawler.domainName Standard camel case syntax.

	// crawler.domain-name Dashed notation, recommended for use in .properties
	// and
	// .yml files.

	// crawler.domain_name Underscore notation, alternative format for use in
	// .properties and .yml files.

	// CRAWLER_DOMAIN_NAME Upper case format. Recommended when using a system
	// environment variables.

	// private String domainName;

	// private String domainLanguage;

	private String[] hostnames;
}
