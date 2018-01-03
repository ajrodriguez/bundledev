package com.bundledev.elasticrest.document;

import static com.bundledev.elasticrest.helper.AddIdHelper.addIdToEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bundledev.elasticrest.document.request.DeleteRequest;
import com.bundledev.elasticrest.document.request.DocumentRequest;
import com.bundledev.elasticrest.document.request.IndexRequest;
import com.bundledev.elasticrest.document.request.QueryByIdRequest;
import com.bundledev.elasticrest.document.request.UpdateRequest;
import com.bundledev.elasticrest.document.response.GetByIdResponse;
import com.bundledev.elasticrest.document.response.IndexResponse;
import com.bundledev.elasticrest.exception.IndexDocumentException;
import com.bundledev.elasticrest.exception.QueryByIdNotFoundException;
import com.bundledev.elasticrest.exception.QueryExecutionException;
import com.bundledev.elasticrest.util.ClientEndpoint;
import com.bundledev.elasticrest.util.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Used to execute actions that interact with the documents in an Elasticsearch
 * index.
 */
@Slf4j
@Service
public class DocumentService {

	private static final String STRING_FORMATTER = "/%s/%s/%s";

	private final RestClient client;
	private final ObjectMapper objectMapper;

	@Autowired
	public DocumentService(RestClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}

	/**
	 * By specifying the unique identification of an object we can return only that
	 * object. If we cannot find the object we throw an
	 * {@link QueryByIdNotFoundException}.
	 *
	 * @param request
	 *            Object containing the required parameters
	 * @param <T>
	 *            Type of the object to be mapped to
	 * @return Found object of type T
	 */
	public <T> T queryById(QueryByIdRequest request) {

		if (request.getTypeReference() == null) {
			throw new QueryExecutionException("The TypeReference in the request cannot be null");
		}

		try {
			String endpoint = String.format(STRING_FORMATTER, request.getIndex(), request.getType(), request.getId());
			Response response = client.performRequest(HttpMethod.GET.name(), endpoint, getRequestParams(request));

			GetByIdResponse<T> queryResponse = objectMapper.readValue(response.getEntity().getContent(),
					request.getTypeReference());

			if (!queryResponse.getFound()) {
				throw new QueryByIdNotFoundException(request.getIndex(), request.getType(), request.getId());
			}

			T entity = queryResponse.getSource();

			if (request.getAddId()) {
				addIdToEntity(request.getId(), entity);
			}

			return entity;

		} catch (ResponseException re) {
			if (re.getResponse().getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				throw new QueryByIdNotFoundException(request.getIndex(), request.getType(), request.getId());
			} else {
				log.warn("method:{}.{}()|message:\'{}\'|argument[s]:{}", "DocumentService", "queryById",
						"Problem while executing request");
				throw new QueryExecutionException("Error when executing a document - queryById");
			}
		} catch (IOException e) {
			log.error("method:{}.{}()|message:\'{}\'|argument[s]:{}", "DocumentService", "queryById",
					"Problem while executing request");
			throw new QueryExecutionException("Error when executing a document - queryById");
		}
	}

	/**
	 * Index the provided document using the provided parameters. If an id is
	 * provided we do an update, of no id is provided we do an inset and we return
	 * the id.
	 *
	 * @param request
	 *            Object containing the required parameters
	 * @return Generated ID
	 */
	public String index(IndexRequest request) {

		String method;
		String endpoint;
		if (request.getId() != null) {
			method = HttpMethod.PUT.name();
			endpoint = String.format(STRING_FORMATTER, request.getIndex(), request.getType(), request.getId());
		} else {
			method = HttpMethod.POST.name();
			endpoint = String.format("/%s/%s", request.getIndex(), request.getType());
		}

		return doIndex(request, method, endpoint);
	}

	/**
	 * Throws an exception if the provided request contains an id that already
	 * exists.
	 *
	 * @param request
	 *            IndexRequest containing the entity to index
	 * @return The id of the object, which is the same as the one you have provided.
	 */
	public String create(IndexRequest request) {

		if (request.getId() == null) {
			throw new QueryExecutionException("Executing create request without an identifier");
		}
		String endpoint = String.format(STRING_FORMATTER + ClientEndpoint.DOCUMENT_CREATE_ENDPOINT, request.getIndex(),
				request.getType(), request.getId());
		return doIndex(request, HttpMethod.PUT.name(), endpoint);
	}

	private String doIndex(IndexRequest request, String method, String endpoint) {

		try {
			HttpEntity requestBody = new StringEntity(objectMapper.writeValueAsString(request.getEntity()),
					ContentType.APPLICATION_JSON);
			Response response = client.performRequest(method, endpoint, getRequestParams(request), requestBody);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_CONFLICT) {
				log.warn("method:{}.{}()|message:\'{}\'", "DocumentService", "doIndex",
						"Version conflict, trying to create an existing document?");
				throw new IndexDocumentException("Document already exists");
			} else if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
				log.warn("method:{}.{}()|message:\'{}\'|argument[s]:{}", "DocumentService", "doIndex",
						"Problem while indexing a document", response.getStatusLine().getReasonPhrase());
				throw new IndexDocumentException("Could not index a document, status code is " + statusCode);
			}

			IndexResponse queryResponse = objectMapper.readValue(response.getEntity().getContent(),
					IndexResponse.class);
			return queryResponse.getId();

		} catch (IOException e) {

			log.error("method:{}.{}()|message:\'{}\'|exception:{}", "DocumentService", "doIndex",
					"Problem while executing request to index a document",
					e.getMessage() != null ? e.getMessage() : "NULL");
			throw new IndexDocumentException("Error when indexing a document");
		}
	}

	public String remove(DeleteRequest request) {

		try {
			String endpoint = String.format(STRING_FORMATTER, request.getIndex(), request.getType(), request.getId());
			Response response = client.performRequest(HttpMethod.DELETE.name(), endpoint);
			return response.getStatusLine().getReasonPhrase();

		} catch (IOException e) {

			log.error("method:{}.{}()|message:\'{}\'|exception:{}", "DocumentService", "remove",
					"Problem while removing a document", e.getMessage() != null ? e.getMessage() : "NULL");
			throw new IndexDocumentException("Error when removing a document");
		}
	}

	public String update(UpdateRequest request) {
		String endpoint = String.format(STRING_FORMATTER + ClientEndpoint.DOCUMENT_UPDATE_ENDPOINT, request.getIndex(),
				request.getType(), request.getId());
		try {
			HttpEntity requestBody = new StringEntity(objectMapper.writeValueAsString(request.getEntity()),
					ContentType.APPLICATION_JSON);
			Response response = client.performRequest(HttpMethod.POST.name(), endpoint, getRequestParams(request),
					requestBody);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {

				log.warn("method:{}.{}()|message:\'{}\'|argument[s]:{}", "DocumentService", "doIndex",
						"Problem while updating a document", response.getStatusLine().getReasonPhrase());
				throw new IndexDocumentException("Could not update a document, status code is " + statusCode);
			}

			IndexResponse queryResponse = objectMapper.readValue(response.getEntity().getContent(),
					IndexResponse.class);
			return queryResponse.getId();

		} catch (IOException e) {

			log.error("method:{}.{}()|message:\'{}\'|exception:{}", "DocumentService", "remove",
					"Problem while executing request to update a document",
					e.getMessage() != null ? e.getMessage() : "NULL");
			throw new IndexDocumentException("Error when updating a document");
		}

	}

	private Map<String, String> getRequestParams(DocumentRequest request) {
		Map<String, String> params = new HashMap<>();
		if (request.getRefresh() != null && !request.getRefresh().equals(DocumentRequest.Refresh.NONE)) {
			params.put("refresh", request.getRefresh().getName());
		}
		return params;
	}

}
