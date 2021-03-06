package com.bundledev.elasticrest.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bundledev.elasticrest.ElasticTestCase;
import com.bundledev.elasticrest.RestClientConfig;
import com.bundledev.elasticrest.TestConfig;
import com.bundledev.elasticrest.document.helpers.MessageEntity;
import com.bundledev.elasticrest.document.helpers.MessageEntityByIdTypeReference;
import com.bundledev.elasticrest.document.request.DeleteRequest;
import com.bundledev.elasticrest.document.request.DocumentRequest;
import com.bundledev.elasticrest.document.request.IndexRequest;
import com.bundledev.elasticrest.document.request.QueryByIdRequest;
import com.bundledev.elasticrest.document.request.UpdateRequest;
import com.bundledev.elasticrest.exception.IndexDocumentException;
import com.bundledev.elasticrest.exception.QueryByIdNotFoundException;
import com.bundledev.elasticrest.index.IndexService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestClientConfig.class, TestConfig.class })
public class DocumentServiceTest extends ElasticTestCase {

	private static final String INDEX = "inttests";
	private static final String TYPE = "inttest";
	private static final String EXISTING_ID_1 = "1";
	private static final String EXISTING_ID_1_MESSAGE = "This is a message";

	@Autowired
	private DocumentService documentService;

	@Autowired
	private IndexService indexService;

	@Before
	public void setUp() throws Exception {
		indexDocument(EXISTING_ID_1, EXISTING_ID_1_MESSAGE);
		indexDocument("elastic_1", "This is a document about elastic");
		indexDocument("elastic_2", "Another document about elastic");

		indexService.refreshIndexes(INDEX);
	}

	@Test
	public void querybyId() throws Exception {
		QueryByIdRequest request = new QueryByIdRequest(INDEX, TYPE, EXISTING_ID_1);
		request.setTypeReference(new MessageEntityByIdTypeReference());
		request.setRefresh(DocumentRequest.Refresh.NOW);
		MessageEntity entity = documentService.queryById(request);

		assertNotNull(entity);
		assertEquals(EXISTING_ID_1_MESSAGE, entity.getMessage());
	}

	@Test
	public void querybyId_addId() throws Exception {
		QueryByIdRequest request = new QueryByIdRequest(INDEX, TYPE, EXISTING_ID_1);
		request.setAddId(true);
		request.setTypeReference(new MessageEntityByIdTypeReference());

		MessageEntity entity = documentService.queryById(request);

		assertNotNull(entity);
		assertEquals(EXISTING_ID_1_MESSAGE, entity.getMessage());
		assertEquals(EXISTING_ID_1, entity.getId());
	}

	@Test
	public void querybyId_NonExistingId() throws Exception {
		QueryByIdRequest request = new QueryByIdRequest(INDEX, TYPE, "non_existing");
		request.setTypeReference(new MessageEntityByIdTypeReference());
		try {
			documentService.queryById(request);
			fail("A QueryByIdNotFoundException should have been thrown");
		} catch (QueryByIdNotFoundException e) {
			assertEquals(INDEX, e.getIndex());
			assertEquals(TYPE, e.getType());
			assertEquals("non_existing", e.getId());
		}
	}

	@Test(expected = QueryByIdNotFoundException.class)
	public void querybyId_NonExistingIndex() throws Exception {
		QueryByIdRequest request = new QueryByIdRequest("NonExisting", TYPE, EXISTING_ID_1);
		request.setTypeReference(new MessageEntityByIdTypeReference());

		documentService.queryById(request);
	}

	@Test
	public void index() {
		MessageEntity entity = new MessageEntity();
		entity.setMessage("An index with an id");

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, "index_1");
		indexRequest.setEntity(entity);

		String id = documentService.index(indexRequest);

		assertEquals("index_1", id);
	}

	@Test
	public void index_noId() {
		MessageEntity entity = new MessageEntity();
		entity.setMessage("An index without an id");

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE);
		indexRequest.setEntity(entity);

		String id = documentService.index(indexRequest);

		// This is a default generated id by elasticsearch, therefore this
		// should work
		assertEquals(20, id.length());
	}

	@Test
	public void remove() {
		MessageEntity entity = new MessageEntity();
		entity.setMessage("An index with an id to be deleted");

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, "delete_id");
		indexRequest.setEntity(entity);

		String id = documentService.index(indexRequest);
		assertEquals("delete_id", id);

		String remove = documentService.remove(new DeleteRequest(INDEX, TYPE, id));

		assertEquals("OK", remove);
	}

	@Test(expected = IndexDocumentException.class)
	public void remove_nonExisting() {
		documentService.remove(new DeleteRequest(INDEX, TYPE, "non_existing_delete"));
	}

	@Test
	public void createDocument() {
		MessageEntity entity = new MessageEntity();
		entity.setMessage("An create index with an id");

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, "create_1");
		indexRequest.setEntity(entity);

		documentService.index(indexRequest);

		try {
			documentService.create(indexRequest);
			fail("An IndexDocumentException should have been thrown");
		} catch (IndexDocumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void updateDocument() {
		MessageEntity entity = new MessageEntity();
		entity.setId("test_update");

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, "test_update");
		indexRequest.setEntity(entity);
		indexRequest.setRefresh(DocumentRequest.Refresh.NOW);
		documentService.index(indexRequest);

		entity.setMessage("Updated message");

		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, "test_update");
		updateRequest.setEntity(entity);
		documentService.update(updateRequest);

		QueryByIdRequest test_update = new QueryByIdRequest(INDEX, TYPE, "test_update");
		test_update.setTypeReference(new MessageEntityByIdTypeReference());
		MessageEntity updatedMessage = documentService.queryById(test_update);

		assertEquals("Updated message", updatedMessage.getMessage());
	}

	private void indexDocument(String id, String message) {
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setMessage(message);

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, id);
		indexRequest.setEntity(messageEntity);

		documentService.index(indexRequest);
	}

}