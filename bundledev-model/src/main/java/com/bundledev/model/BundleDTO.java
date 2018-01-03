package com.bundledev.model;

import java.io.Serializable;
import java.util.Set;

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
public class BundleDTO implements Serializable {

	private static final long serialVersionUID = -5898378247168511380L;

	public static final String INDEX_PARAM = "bundledev";

	public static final String TYPE_PARAM = "bundle-item";

	private String id;

	private String title;

	private String uri;

	private String generatedId;

	private String origin;

	private String content;

	private String creator;

	private Set<BundleCategoryDTO> categories;
}
