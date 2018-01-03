package com.bundledev.feedcollector.converter;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bundledev.feedcollector.extractor.ExtractorFactoryMethod;
import com.bundledev.feedcollector.persistence.model.BundleItemEntity;
import com.bundledev.feedcollector.persistence.model.BundleItemEntity.BundleItemEntityBuilder;
import com.bundledev.feedcollector.service.IBundleItemService;
import com.bundledev.feedcollector.util.FeedCollectorUtil;
import com.bundledev.model.BundleCategoryDTO;
import com.bundledev.model.BundleDTO;
import com.bundledev.model.BundleDTO.BundleDTOBuilder;
import com.rometools.rome.feed.synd.SyndEntry;

@Component
public class BundleItemConverter implements IBundleItemConverter {

	@Autowired
	private IBundleItemService bundleItemService;

	public BundleItemEntity convertEntryToEntity(final String origin, SyndEntry feed) {

		BundleItemEntityBuilder builder = BundleItemEntity.builder();

		builder.title(feed.getTitle()).link(feed.getLink()).creator(feed.getAuthor()).origin(origin.toUpperCase());

		builder.generatedId(FeedCollectorUtil.md5FromString(feed.getLink()));

		builder.publicationTimestamp(feed.getPublishedDate());
		builder.collectTimestamp(Date.from(Instant.now().atZone(ZoneId.systemDefault()).toInstant()));

		builder.categories(feed.getCategories().stream()
				.map(syndCategory -> bundleItemService.getCategoryByValue(syndCategory.getName()))
				.collect(Collectors.toSet()));

		builder.content(ExtractorFactoryMethod.getExtractor(origin, feed).extractContent());

		return builder.build();
	}

	public BundleDTO convertEntityToItem(final BundleItemEntity entity) {

		BundleDTOBuilder builder = BundleDTO.builder();

		builder.title(entity.getTitle()).uri(entity.getLink()).generatedId(entity.getGeneratedId())
				.origin(entity.getOrigin()).creator(entity.getCreator());

		Optional.ofNullable(entity.getContent())
				.ifPresent(content -> builder.content(Jsoup.clean(content, Whitelist.none())));

		builder.categories(entity.getCategories().stream()
				.map(category -> BundleCategoryDTO.builder().value(category.getValue()).build())
				.collect(Collectors.toSet()));

		return builder.build();
	}

}
