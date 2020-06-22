package ksu.poma.dbutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ksu.poma.dbutils.model.CustomHttpResponse;
import ksu.poma.dbutils.model.DocumentHttpResponse;
import ksu.poma.model.ProjectInfo;
import ksu.poma.model.RevisionedDocument;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.UUID;

//This helps with CRUD operations
public class DocumentClient {

    private CouchClient couchClient;
    private ObjectMapper jsonObjectMapper;
    private String databaseName;

    Logger logger = LoggerFactory.getLogger(DocumentClient.class);

    public DocumentClient(){}
    public DocumentClient(final CouchClient couchClient, final String databaseName, final ObjectMapper expectedMatcher) {
        this.couchClient = couchClient;
        this.databaseName = databaseName;
        this.jsonObjectMapper = expectedMatcher;
    }

    public DocumentClient(final CouchClient couchClient, final ObjectMapper expectedMatcher) {
        this.couchClient = couchClient;
        this.jsonObjectMapper = expectedMatcher;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    private String formatUri(final String uri) {
        return String.format("%s/%s", databaseName, uri.replaceAll(" ", "%20"));
    }

    public CustomHttpResponse get(String id){
        return  couchClient.executeRequest(couchClient.GET(formatUri(id)));
    }

    public CustomHttpResponse getAll(){
        return  couchClient.executeRequest(couchClient.GET(formatUri("_all_docs")));
    }

    public <T extends RevisionedDocument> DocumentHttpResponse update(final T objectInfo){
        if (Objects.nonNull(objectInfo)) {
            if(Objects.nonNull(objectInfo.get_id()) && Objects.nonNull(objectInfo.get_rev())){
                return createOrUpdate(objectInfo);
            } else {
                throw new IllegalArgumentException("Object missing _rev - revision information");
            }
        } else {
            throw new IllegalArgumentException("Object to be updated in the DB is null");
        }
    }

    public <T extends RevisionedDocument> DocumentHttpResponse create(final T objectInfo){
        if (Objects.nonNull(objectInfo)) {
            if(Objects.isNull(objectInfo.get_id()) || objectInfo.get_rev().trim().isEmpty()){
                objectInfo.set_id(UUID.randomUUID().toString());
            }
            return createOrUpdate(objectInfo);
        } else {
            throw new IllegalArgumentException("Object missing _rev - revision information");
        }
    }

    private <T extends RevisionedDocument> DocumentHttpResponse createOrUpdate(final T objectInfo){
        DocumentHttpResponse documentHttpResponse = new DocumentHttpResponse();
        try {
            String json = jsonObjectMapper.writerFor(objectInfo.getClass()).writeValueAsString(objectInfo);
            HttpUriRequest httpUriRequest = couchClient.PUT(formatUri(objectInfo.get_id()), json);
            documentHttpResponse =  couchClient.executeRequest(DocumentHttpResponse.class, httpUriRequest);

        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
        return documentHttpResponse;
    }

}
