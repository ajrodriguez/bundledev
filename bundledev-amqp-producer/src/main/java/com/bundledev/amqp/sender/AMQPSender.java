package com.bundledev.amqp.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bundledev.model.BundleDTO;

@Component
public class AMQPSender implements ISender {

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private Queue queue;

	@Override
	public void send(final BundleDTO item) {
		this.template.convertAndSend(queue.getName(), item);
	}
}