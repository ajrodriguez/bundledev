package com.bundledev.crawler.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class KeyUtil {

	private static final Logger logger = LoggerFactory.getLogger(KeyUtil.class);

	private static final String ALGORITHM = "SHA-256";
	private static final String CHARSET = "UTF-8";
	private static final int RADIX = Character.MAX_RADIX;

	public static String digestKey(final String user, final String key) {
		if (user != null && user.length() > 0) {
			return digest(user, key);
		}
		return null;
	}

	private static String digest(final String salt, final String text) {
		if (text != null && text.length() > 0) {
			try {
				final MessageDigest md = MessageDigest.getInstance(ALGORITHM);
				md.reset();
				if (salt != null && salt.length() > 0) {
					md.update(salt.getBytes(CHARSET));
				}
				final byte[] d = md.digest(text.getBytes(CHARSET));
				return toString(d);
			} catch (final NoSuchAlgorithmException e) {
				logger.error("method:{}.{}|cause:\'{}\'|message:\'{}\'|exception:\'{}\'", "KeyUtil", "digest",
						e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
			} catch (final UnsupportedEncodingException e) {
				logger.error("method:{}.{}|cause:\'{}\'|message:\'{}\'|exception:\'{}\'", "KeyUtil", "digest",
						e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
			}
		}
		return null;
	}

	private static String toString(final byte[] array) {
		if (Util.isEmpty(array)) {
			return null;
		} else if (array.length == 0) {
			return "";
		}

		final StringBuilder sb = new StringBuilder();
		for (byte b : array) {
			sb.append(toString(b));
		}
		return sb.toString();
	}

	private static String toString(final byte b) {
		final int i = b & 0xFF;
		final String s = Integer.toString(i, RADIX);
		return s.length() > 1 ? s : "0" + s;
	}
}
