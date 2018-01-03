package com.bundledev.feedcollector.extractor;

import com.rometools.rome.feed.synd.SyndEntry;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Extractor {

	private String origin;

	private SyndEntry entry;

	public Extractor(final SyndEntry entry) {
		this.entry = entry;
	}

	public abstract String extractContent();
}
