package ksu.poma.dbutils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

                // Ensure each request is eligible for GZip
                httpClientBuilder.addInterceptorFirst(
                        (HttpRequestInterceptor)
                                (httpRequest, httpContext) -> {
                                    httpRequest.setHeader("Accept","application/json");
                                    httpRequest.setHeader("Content-Type", "application/json");

                                    if (!httpRequest.containsHeader("Authorization")) {
                                        if (Objects.nonNull(userName)
                                                && Objects.nonNull(password)
                                                && !(userName.trim().isEmpty() || password.trim().isEmpty())) {
                                            httpRequest.addHeader(
                                                    "Authorization",
                                                    String.format(
                                                            "Basic %s",
                                                            Base64.getEncoder()
                                                                    .encodeToString((userName + ":" + password).getBytes())));
                                        }
                                    }
                                });

                CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
                httpClntByRef.set(closeableHttpClient);
            }
        }catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public CloseableHttpClient getHttpClient() {
        if(!Objects.isNull(httpClntByRef.get())){
            return httpClntByRef.get();
        }
        throw new IllegalStateException("Failed to create http client object");
    }
}
