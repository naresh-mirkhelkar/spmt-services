package ksu.poma.dbutils;

import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


public class DBClient {

    Logger logger = LoggerFactory.getLogger(DBClient.class);
    private AtomicReference<CloseableHttpClient> httpClntByRef = new AtomicReference<>();

    public DBClient(
            final String userName,
            final String password,
            final int timeoutInSeconds
            ){
        initialize(userName,password, timeoutInSeconds);
    }

    private void initialize (final String userName,
                             final String password,
                             final int timeoutInSeconds) {
        try{
            if(Objects.isNull(httpClntByRef.get())){
                PoolingHttpClientConnectionManager poolingManager = new PoolingHttpClientConnectionManager();
                poolingManager.setMaxTotal(100);
                poolingManager.setDefaultMaxPerRoute(100);
                poolingManager.setDefaultConnectionConfig(ConnectionConfig.custom().setCharset(StandardCharsets.UTF_8).build());

                HttpClientBuilder httpClientBuilder = HttpClients.custom().setMaxConnPerRoute(200)
                        .setConnectionManager(poolingManager).setConnectionTimeToLive(timeoutInSeconds, TimeUnit.SECONDS);
                CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
                httpClntByRef.set(closeableHttpClient);
            }
        }catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public CloseableHttpClient getHttpClient() {
        if(Objects.isNull(httpClntByRef.get())){
            return httpClntByRef.get();
        }
        throw new IllegalStateException("Failed to create http client object");
    }
}
