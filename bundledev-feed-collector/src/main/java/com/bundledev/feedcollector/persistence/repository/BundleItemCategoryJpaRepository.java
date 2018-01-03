package com.bundledev.feedcollector.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bundledev.feedcollector.persistence.model.BundleItemCategoryEntity;

public interface BundleItemCategoryJpaRepository extends JpaRepository<BundleItemCategoryEntity, Long> {

	List<BundleItemCategoryEntity> findByValueIgnoreCase(final String categoryValue);
}