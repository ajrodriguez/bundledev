package com.bundledev.elasticrest.exception;

import lombok.Getter;

@Getter
public class QueryByIdNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = -7494381619956784319L;
	
	private String index;
    private String type;
    private String id;

    public QueryByIdNotFoundException(String index, String type, String id) {
        super("Document with id " + id + ", type " + type + ", index " + index + " could not be found");
        this.id = id;
        this.type = type;
        this.index = index;
    }
}
