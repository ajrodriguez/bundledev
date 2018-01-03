package com.bundledev.feedcollector.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.rometools.rome.feed.synd.SyndEntry;

public class ExtractorFactoryMethod {

	private static final Logger logger = LoggerFactory.getLogger(ExtractorFactoryMethod.class);

	public static Extractor getExtractor(final String origin, final SyndEntry entry) {

		if (CollectionUtils.isEmpty(entry.getContents())) {
			logger.info("Feed external with origin [{}]", origin);
			return new ExternalExtractor(origin, entry);
		} else {
			return new FeedExtractor(entry);
		}
	}
}
