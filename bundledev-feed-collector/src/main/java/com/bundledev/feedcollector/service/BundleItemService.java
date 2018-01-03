package com.bundledev.feedcollector.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bundledev.crawler.util.Util;
import com.bundledev.feedcollector.persistence.model.BundleItemCategoryEntity;
import com.bundledev.feedcollector.persistence.model.BundleItemEntity;
import com.bundledev.feedcollector.persistence.repository.BundleItemCategoryJpaRepository;
import com.bundledev.feedcollector.persistence.repository.BundleItemJpaRepository;
import com.bundledev.feedcollector.util.FeedCollectorUtil;

@Service
public class BundleItemService implements IBundleItemService {

	@Autowired
	private BundleItemJpaRepository bundleItemRepository;

	@Autowired
	private BundleItemCategoryJpaRepository bundleItemCategoryRepository;

	public BundleItemCategoryEntity getCategoryByValue(final String categoryValue) {

		BundleItemCategoryEntity categorySelected = null;

		List<BundleItemCategoryEntity> categories = bundleItemCategoryRepository.findByValueIgnoreCase(categoryValue);

		if (!Util.isEmpty(categories)) {
			categorySelected = categories.get(0);
		} else {
			categorySelected = BundleItemCategoryEntity.builder().value(categoryValue.toLowerCase()).build();
		}
		return categorySelected;
	}

	@Override
	public boolean isEntriesByOriginUpdatedToDate(String origin, String newEntryLink) {

		String newEntryGeneratedId = FeedCollectorUtil.md5FromString(newEntryLink);
		Optional<BundleItemEntity> entityOp = bundleItemRepository
				.findFirstByOriginOrderByPublicationTimestampDesc(origin);

		if (entityOp.isPresent() && entityOp.get().getGeneratedId() != null) {
			return entityOp.get().getGeneratedId().equals(newEntryGeneratedId);
		}

		return false;
	}

	@Override
	public boolean isEntryPersisted(String newEntryLink) {
		String newEntryGeneratedId = FeedCollectorUtil.md5FromString(newEntryLink);
		Optional<BundleItemEntity> entityOp = bundleItemRepository.findByGeneratedId(newEntryGeneratedId);
		return entityOp.isPresent();
	}
}
