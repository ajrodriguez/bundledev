package com.bundledev.feedcollector.processor;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bundledev.feedcollector.converter.IBundleItemConverter;
import com.bundledev.feedcollector.persistence.model.BundleItemEntity;
import com.rometools.rome.feed.synd.SyndEntry;

@Component
public class FeedItemProcessor implements ItemProcessor<Entry<String, SyndEntry>, BundleItemEntity> {

	private static final Logger logger = LoggerFactory.getLogger(FeedItemProcessor.class);

	@Autowired
	private IBundleItemConverter bundleItemConverter;

	@Override
	public BundleItemEntity process(final Entry<String, SyndEntry> feed) throws Exception {

		BundleItemEntity bundleItem = null;

		try {
			bundleItem = bundleItemConverter.convertEntryToEntity(feed.getKey(), feed.getValue());
		} catch (Exception exc) {
			logger.error("An error has been produced at " + feed.getValue().getLink() + " in processor method", exc);
		}

		return bundleItem;
	}
}
