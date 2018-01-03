package com.bundledev.feedcollector.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.rometools.rome.feed.synd.SyndEntry;

public class FeedExtractor extends Extractor {

	private static final Logger log = LoggerFactory.getLogger(FeedExtractor.class);

	public FeedExtractor(SyndEntry entry) {
		super(entry);
	}

	@Override
	public String extractContent() {
		return Optional.fromNullable(getEntry().getContents().get(0).getValue()).orNull();
	}
}
