package com.bundledev.feedcollector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.bundledev")
@SpringBootApplication
public class FeedCollectorApplication {

	private static final Logger logger = LoggerFactory.getLogger(FeedCollectorApplication.class);

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(FeedCollectorApplication.class, args)));
	}
}
