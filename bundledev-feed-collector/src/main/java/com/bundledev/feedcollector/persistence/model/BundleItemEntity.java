package com.bundledev.feedcollector.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = BundleItemEntity.TABLE_NAME)
public class BundleItemEntity implements Serializable {

	private static final long serialVersionUID = -5898378247168511380L;

	public static final String TABLE_NAME = "bundle_item";

	public static final String TITLE_COLUMN_NAME = "title";
	public static final String LINK_COLUMN_NAME = "link";
	public static final String GENERATEID_COLUMN_NAME = "generatedId";
	public static final String ORIGIN_COLUMN_NAME = "origin";
	public static final String CONTENT_COLUMN_NAME = "content";
	public static final String PUBLICATION_COLUMN_NAME = "publication_ts";
	public static final String COLLECT_COLUMN_NAME = "collect_ts";
	public static final String CREATOR_COLUMN_NAME = "creator";

	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = TITLE_COLUMN_NAME, length = 255)
	private String title;

	@Column(name = LINK_COLUMN_NAME)
	private String link;

	@Column(name = GENERATEID_COLUMN_NAME, unique = true)
	private String generatedId;

	@Column(name = ORIGIN_COLUMN_NAME)
	private String origin;

	@Column(name = CONTENT_COLUMN_NAME)
	@Lob
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = PUBLICATION_COLUMN_NAME, nullable = true, insertable = true, updatable = false, length = 6)
	private Date publicationTimestamp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = COLLECT_COLUMN_NAME, nullable = true, insertable = true, updatable = false, length = 6)
	private Date collectTimestamp;

	@Column(name = CREATOR_COLUMN_NAME)
	private String creator;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = BundleItemCategoryEntity.class)
	@JoinTable(name = "bundle_item_categories", joinColumns = @JoinColumn(name = BundleItemEntity.TABLE_NAME
			+ "_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = BundleItemCategoryEntity.TABLE_NAME
					+ "_id", referencedColumnName = "id"))
	private Set<BundleItemCategoryEntity> categories;
}
