package com.bundledev.feedcollector.extractor.external;

import java.util.HashMap;
import java.util.Map;

import com.bundledev.crawler.IExtractor;
import com.bundledev.crawler.extractor.AtlassianExtractor;

public final class ExternalExtractorRelation {

	private static final Map<String, IExtractor> externalExtractorMap = new HashMap<>();

	static {
		externalExtractorMap.put("ATLASSIAN", new AtlassianExtractor());
	}

	public static Map<String, IExtractor> getExternalExtractorMap() {
		return externalExtractorMap;
	}
}
