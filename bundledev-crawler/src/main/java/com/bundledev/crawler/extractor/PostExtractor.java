package com.bundledev.crawler.extractor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.bundledev.crawler.IExtractor;
import com.bundledev.crawler.dto.PostDTO;
import com.bundledev.crawler.util.Util;

public abstract class PostExtractor implements IExtractor {

	protected static String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.89 Safari/537.36";

	@Override
	public String crawlContent(final String link) {
		return this.extractPostData(link).getContent();
	}

	protected PostDTO extractPostData(final String urlPageFrom) {
		PostDTO post = retrievePostDTO(urlPageFrom);
		if (post != null) {
			cleanHTML(post);
			disambiguate(post);
		}
		return post;
	}

	/**
	 * Clean HTML of all private fields of T PostDTO with @Whitelist basic of @Jsoup
	 * elements.
	 *
	 * @param postDTO
	 */
	protected void cleanHTML(final PostDTO postDTO) {
		try {
			BeanInfo bi = Introspector.getBeanInfo(postDTO.getClass());
			final List<PropertyDescriptor> propDes = Arrays.asList(bi.getPropertyDescriptors());
			final Object[] input = null;
			for (PropertyDescriptor prop : propDes) {
				final Method readMethod = prop.getReadMethod();
				if (readMethod != null && readMethod.getReturnType().isAssignableFrom(String.class)) {
					final String value = (String) readMethod.invoke(postDTO, input);
					if (!Util.isEmpty(value)) {
						String valueC = Jsoup.clean(value, Whitelist.basic());
						valueC = StringEscapeUtils.unescapeHtml4(valueC);
						final Method writeMethod = prop.getWriteMethod();
						writeMethod.invoke(postDTO, valueC);
					}
				}
			}
		} catch (IntrospectionException | IllegalArgumentException | IllegalAccessException
				| InvocationTargetException e) {

		}
	}

	protected void disambiguate(final PostDTO PostDTO) {

	}

	/**
	 * Retrieve the basic version of a post associated to url param.
	 *
	 * @param urlPageFrom
	 *            URL page.
	 * @param htmlPageCrawl
	 *            html content from URL
	 * @return The basic version of a site.
	 */
	public abstract PostDTO retrievePostDTO(final String urlPageFrom);
}
