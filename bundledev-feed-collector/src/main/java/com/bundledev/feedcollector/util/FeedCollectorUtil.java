package com.bundledev.feedcollector.util;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

public final class FeedCollectorUtil {

	private FeedCollectorUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String md5FromString(final String link) {
		return !StringUtils.isEmpty(link) ? DigestUtils.md5DigestAsHex(link.getBytes()) : null;
	}
}
