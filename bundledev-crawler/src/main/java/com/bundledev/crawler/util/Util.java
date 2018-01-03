package com.bundledev.crawler.util;

import java.nio.charset.Charset;
import java.util.Collection;

import org.apache.commons.codec.binary.Base64;

/**
 * Utility methods.
 */
public abstract class Util {

	private static final String UTF_8 = "UTF-8";
	private static final String ISO_8859_1 = "ISO-8859-1";
	public static final Charset UTF8_CHARSET = Charset.forName(UTF_8);
	public static final Charset LATIN1_CHARSET = Charset.forName(ISO_8859_1);
	public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

	/**
	 * Decodes a Base64 text into plain text using a deault charset.
	 * 
	 * @param base64Text
	 *            The Base64 text to be decoded.
	 * @return A plain text decoded from the Base64 text.
	 */
	public static String decodeBase64(final String base64Text) {
		return decodeBase64(base64Text, DEFAULT_CHARSET);
	}

	public static String EncodeBase64(final String plainText) {
		return encodeBase64(plainText, DEFAULT_CHARSET);
	}

	/**
	 * Decodes a Base64 text into plain text using the specified charset.
	 * 
	 * @param base64Text
	 *            The Base64 text to be decoded.
	 * @param charset
	 *            The charset to be use.
	 * @return A plain text decoded from the Base64 text.
	 */
	public static String decodeBase64(final String base64Text, final Charset charset) {
		if (!isEmpty(base64Text)) {
			final byte[] bytes = Base64.decodeBase64(base64Text.getBytes(UTF8_CHARSET));
			if (!isEmpty(bytes)) {
				final String s = new String(bytes, charset == null ? DEFAULT_CHARSET : charset);
				return s;
			}
		}
		return null;
	}

	public static String encodeBase64(final String plainText, final Charset charset) {
		if (!isEmpty(plainText)) {
			final byte[] bytes = Base64.encodeBase64(plainText.getBytes());
			if (!isEmpty(bytes)) {
				final String s = new String(bytes, charset == null ? DEFAULT_CHARSET : charset);
				return s;
			}
		}
		return null;
	}

	public static boolean isEmpty(final Object[] array) {
		return array == null || array.length == 0;
	}

	public static boolean isEmpty(final String[] array) {
		return array == null || array.length == 0;
	}

	public static boolean isEmpty(final String text) {
		return text == null || text.trim().length() == 0;
	}

	public static boolean isEmpty(final Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isEmpty(final byte[] bytes) {
		return bytes == null || bytes.length == 0;
	}
}
