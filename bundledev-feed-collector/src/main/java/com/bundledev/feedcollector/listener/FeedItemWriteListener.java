package com.bundledev.feedcollector.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bundledev.amqp.sender.ISender;
import com.bundledev.feedcollector.converter.IBundleItemConverter;
import com.bundledev.feedcollector.persistence.model.BundleItemEntity;

@Component
public class FeedItemWriteListener implements ItemWriteListener<BundleItemEntity> {

	@Autowired
	private IBundleItemConverter bundleItemConverter;

	@Autowired
	private ISender amqpProducer;

	@Override
	public void beforeWrite(List<? extends BundleItemEntity> items) {
		// Auto-generated method stub
	}

	@Override
	public void afterWrite(List<? extends BundleItemEntity> items) {
		items.forEach(entity -> amqpProducer.send(bundleItemConverter.convertEntityToItem(entity)));
	}

	@Override
	public void onWriteError(Exception exception, List<? extends BundleItemEntity> items) {
		// Auto-generated method stub
	}

}
