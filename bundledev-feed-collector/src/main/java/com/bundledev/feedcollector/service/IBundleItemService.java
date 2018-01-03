package com.bundledev.feedcollector.service;

import com.bundledev.feedcollector.persistence.model.BundleItemCategoryEntity;

public interface IBundleItemService {

	BundleItemCategoryEntity getCategoryByValue(final String categoryValue);

	boolean isEntriesByOriginUpdatedToDate(String origin, String newEntryLink);

	boolean isEntryPersisted(String newEntryLink);
}
