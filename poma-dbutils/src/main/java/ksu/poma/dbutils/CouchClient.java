package ksu.poma.dbutils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ksu.poma.dbutils.model.CustomHttpResponse;
import ksu.poma.dbutils.model.DocumentHttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class CouchClient {

    private final DBClient httpClient;
    private final String host;
    private Logger logger = LoggerFactory.getLogger(CouchClient.class);

    public CouchClient(
            final String host,
            final int timeout,
            final String userName,
            final String password
    ){
        this.httpClient = new DBClient(userName, password, timeout);
        this.host = host;
    }

    public CustomHttpResponse executeRequest(final HttpUriRequest request){
        CustomHttpResponse customHttpResponse = new CustomHttpResponse();
        try {
          String dbResponse = httpClient.getHttpClient().execute(request, (response) -> {
              customHttpResponse.setStatusCode(response.getStatusLine().getStatusCode());
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             if (response.getEntity()!=null && response.getEntity().getContent() != null) {
                 response.getEntity().getContent().transferTo(byteArrayOutputStream);
             }
             return new String(byteArrayOutputStream.toByteArray());
         });
          customHttpResponse.setContent(dbResponse);
        } catch (IOException Iox){
            logger.error(Iox.getMessage());
        }
        return customHttpResponse;
    }

    public <T> DocumentHttpResponse executeRequest(final Class<T> objectInfo, final HttpUriRequest httpUriRequest) {
        ObjectMapper objectMapper = getDefaultObjectMapper();
        DocumentHttpResponse documentHttpResponse = new DocumentHttpResponse();
        try {
            String dbResponse = httpClient.getHttpClient().execute(httpUriRequest, (response) -> {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED ||
                        response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    if (response.getEntity() != null && response.getEntity().getContent() != null) {
                        response.getEntity().getContent().transferTo(byteArrayOutputStream);
                    }
                }
                return new String(byteArrayOutputStream.toByteArray());
            });

            if(Objects.nonNull(dbResponse) && !dbResponse.trim().isEmpty()){
                documentHttpResponse = objectMapper.readerFor(objectInfo).readValue(dbResponse);
            }
        } catch (IOException Iox){
            logger.error(Iox.getMessage());
        }
        return documentHttpResponse;
    }

    public static ObjectMapper getDefaultObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    private String buildUrl(final String uri) {
        return String.format(
                "%s/%s", host, (uri.startsWith("/") ? uri.substring(1) : uri));
    }

    public HttpUriRequest GET(final String uri) {
        return new HttpGet(buildUrl(uri));
    }

    public HttpUriRequest POST(final String uri, final String content){
        HttpPost httpPost = new HttpPost(buildUrl(uri));
        httpPost.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
        return httpPost;
    }

    public HttpUriRequest PUT(final String uri, final String content){
        HttpPut httpPut = new HttpPut(buildUrl(uri));
        httpPut.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
        return httpPut;
    }

    public HttpUriRequest DELETE(final String uri) {
        HttpDelete httpDelete = new HttpDelete(buildUrl(uri));
        return httpDelete;
    }
}
