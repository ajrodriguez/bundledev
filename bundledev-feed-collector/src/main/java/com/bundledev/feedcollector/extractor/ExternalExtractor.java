package com.bundledev.feedcollector.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bundledev.crawler.IExtractor;
import com.bundledev.feedcollector.extractor.external.ExternalExtractorRelation;
import com.rometools.rome.feed.synd.SyndEntry;

public class ExternalExtractor extends Extractor {

	private static final Logger log = LoggerFactory.getLogger(FeedExtractor.class);

	public ExternalExtractor(String origin, SyndEntry entry) {
		super(origin, entry);
	}

	@Override
	public String extractContent() {
		IExtractor extractor = ExternalExtractorRelation.getExternalExtractorMap().get(getOrigin().toUpperCase());
		return extractor != null ? extractor.crawlContent(getEntry().getLink()) : null;
	}
}
