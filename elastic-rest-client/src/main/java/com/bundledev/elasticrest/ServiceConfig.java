package com.bundledev.elasticrest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class ServiceConfig {

	@Bean
	public ObjectMapper buildObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}
}
