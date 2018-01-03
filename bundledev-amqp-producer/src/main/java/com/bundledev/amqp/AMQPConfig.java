package com.bundledev.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

	private final static String QUEUE_NAME = "bundledev-feed";
	private final static String EXCHANGE_NAME = "bundledev-feed-exchange";

	@Bean
	public Queue queue() {
		return new Queue(QUEUE_NAME, false);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}

	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
	}

	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setQueue(QUEUE_NAME);
		rabbitTemplate.setExchange(EXCHANGE_NAME);
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		return rabbitTemplate;
	}
}