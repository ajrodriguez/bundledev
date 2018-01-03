package com.bundledev.crawler;

@FunctionalInterface
public interface IExtractor {

	public String crawlContent(String link);
}
