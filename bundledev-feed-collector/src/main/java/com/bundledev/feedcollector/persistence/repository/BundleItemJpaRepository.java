package com.bundledev.feedcollector.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bundledev.feedcollector.persistence.model.BundleItemEntity;

public interface BundleItemJpaRepository extends JpaRepository<BundleItemEntity, Long> {

	Optional<BundleItemEntity> findFirstByOriginOrderByPublicationTimestampDesc(String origin);

	Optional<BundleItemEntity> findByGeneratedId(String generatedId);
}