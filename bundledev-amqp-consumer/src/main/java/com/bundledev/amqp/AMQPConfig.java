package com.bundledev.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class AMQPConfig {

	public static final String QUEUE_NAME = "bundledev-feed";

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public Queue queue(RabbitAdmin rabbitAdmin) {
		Queue queue = new Queue(QUEUE_NAME, false);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		return factory;
	}
}