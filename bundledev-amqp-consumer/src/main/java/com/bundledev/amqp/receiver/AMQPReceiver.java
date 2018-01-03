package com.bundledev.amqp.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bundledev.amqp.AMQPConfig;
import com.bundledev.elasticrest.document.DocumentService;
import com.bundledev.elasticrest.document.request.IndexRequest;
import com.bundledev.model.BundleDTO;

@Component
public class AMQPReceiver implements IReceiver {

	@Autowired
	private DocumentService documentService;

	@RabbitListener(queues = AMQPConfig.QUEUE_NAME)
	public void receive(final BundleDTO item) {
		if (item != null) {
			IndexRequest indexRequest = new IndexRequest(BundleDTO.INDEX_PARAM, BundleDTO.TYPE_PARAM);
			indexRequest.setEntity(item);
			documentService.index(indexRequest);
		}
	}
}