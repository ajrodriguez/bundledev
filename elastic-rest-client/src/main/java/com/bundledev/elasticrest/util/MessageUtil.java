package com.bundledev.elasticrest.util;

import java.text.MessageFormat;
import java.util.Properties;

public abstract class MessageUtil {

	private static Properties messagesProperties = new Properties();

	static {
		messagesProperties = PropertiesLoader.load("messages/messages.properties");
	}

	public static String getMessage(String property, Object... params) {
		return MessageFormat.format(messagesProperties.getProperty(property), params);
	}
}