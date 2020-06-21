package ksu.poma.dbutils;

import ksu.poma.dbutils.model.CustomHttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
