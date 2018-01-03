package com.bundledev.crawler.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bundledev.crawler.dto.PostDTO;
import com.bundledev.crawler.util.JsoupUtil;
import com.bundledev.crawler.util.Util;

public class AtlassianExtractor extends PostExtractor {

	private static final Logger log = LoggerFactory.getLogger(AtlassianExtractor.class);

	@Override
	public PostDTO retrievePostDTO(String urlPageFrom) {

		final PostDTO post = new PostDTO();

		try {

			Document doc = JsoupUtil.getJsoupDocument(urlPageFrom, Util.UTF8_CHARSET, USER_AGENT);
			doc.outputSettings().escapeMode(EscapeMode.xhtml);

			configurePostData(post, doc);

		} catch (Exception e) {
			log.error("method:{}.{}|cause:\'{}\'|message:\'{}\'|exception:\'{}\'|extra:\'{}\'", "AtlassianExtractor",
					"retrievePostDTO", e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e, urlPageFrom);
		}

		return post;
	}

	protected void configurePostData(final PostDTO post, final Document docHtml) {

		// 1- CONTENT

		Elements elementsContent = docHtml
				.select(".blog > .aui-page-panel-inner > *:not(.latest-blogs, .signup-container, .comments)");
		String contentPost = !Util.isEmpty(elementsContent) ? elementsContent.outerHtml() : null;
		post.setContent(contentPost);

		// 3- MARKERS

		// final List<String> markersPost = new ArrayList<String>();
		// Elements elementsMarkers = docHtml.select("a[href*=categories]");
		// elementsMarkers.forEach(marker -> markersPost.add(marker.text()));
		// post.setMarkers(markersPost);
	}
}
