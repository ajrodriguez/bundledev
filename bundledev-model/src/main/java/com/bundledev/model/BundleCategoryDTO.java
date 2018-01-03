package com.bundledev.model;

import java.io.Serializable;

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
public class BundleCategoryDTO implements Serializable {

	private static final long serialVersionUID = -1290092301476413658L;

	private String value;
}
