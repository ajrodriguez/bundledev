package com.bundledev.feedcollector.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = BundleItemCategoryEntity.TABLE_NAME)
public class BundleItemCategoryEntity implements Serializable {

	private static final long serialVersionUID = -1290092301476413658L;

	public static final String TABLE_NAME = "bundle_item_category";

	public static final String VALUE_COLUMN_NAME = "value";

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = VALUE_COLUMN_NAME, nullable = false)
	private String value;
}
