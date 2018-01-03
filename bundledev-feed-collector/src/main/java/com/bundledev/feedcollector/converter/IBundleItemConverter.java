package com.bundledev.feedcollector.converter;

import com.bundledev.feedcollector.persistence.model.BundleItemEntity;
import com.bundledev.model.BundleDTO;
import com.rometools.rome.feed.synd.SyndEntry;

public interface IBundleItemConverter {

	BundleItemEntity convertEntryToEntity(final String origin, SyndEntry feed);

	BundleDTO convertEntityToItem(final BundleItemEntity entity);
}
