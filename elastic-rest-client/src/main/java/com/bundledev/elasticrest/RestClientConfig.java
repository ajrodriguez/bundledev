package com.bundledev.elasticrest;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties({ RestClientProperties.class })
public class RestClientConfig {
}
