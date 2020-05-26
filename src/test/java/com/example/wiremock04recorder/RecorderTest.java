package com.example.wiremock04recorder;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertEquals;

public class RecorderTest {

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private CloseableHttpResponse response;
    static int port;

    static {

        try {
            // Get a free port
            ServerSocket s = new ServerSocket(0);
            port = s.getLocalPort();
            s.close();

        } catch (IOException e) {
            // No OPS
        }
    }


    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);

    @Test
    public void stubbingTest01() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/api/uses/2");
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

}
