package com.bundledev.elasticrest.document.request;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentRequest {

	public enum Refresh {
		NOW("true"), WAIT("wait_for"), NONE("false");

		private String name;

		Refresh(String name) {
			this.name = name;
		}

		@JsonValue
		public String getName() {
			return name;
		}
	}

	private String index;

	private String type;

	private String id;

	private Refresh refresh;

	public DocumentRequest(String index, String type) {
		super();
		this.index = index;
		this.type = type;
	}

	public DocumentRequest(String index, String type, String id) {
		super();
		this.index = index;
		this.type = type;
		this.id = id;
	}

	public DocumentRequest(String index, String type, String id, Refresh refresh) {
		super();
		this.index = index;
		this.type = type;
		this.id = id;
		this.refresh = refresh;
	}
}
