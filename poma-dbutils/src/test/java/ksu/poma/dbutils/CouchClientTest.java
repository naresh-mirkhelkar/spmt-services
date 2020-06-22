package ksu.poma.dbutils;

import ksu.poma.dbutils.model.CustomHttpResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CouchClientTest {

    String uri = "http://localhost:5984/prj-mgmt";

    @Test
    void testExecuteRequest() {
        CouchClient couchClient = new CouchClient(uri,5,"admin","password");
        CustomHttpResponse customHttpResponse = couchClient.executeRequest(couchClient.GET("3d48324a18484bf2f2f14cdbd10023a2"));
        assertNotNull(customHttpResponse);
        assertEquals(200, HttpStatus.SC_OK);
        System.out.println(customHttpResponse.getContent());
    }
}