package com.example.wiremock03stubbing;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.security.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class StubbingTest {

    private static final String BAELDUNG_PATH = "/baeldung";
    private static final WireMockServer wireMockServer = new WireMockServer();
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private CloseableHttpResponse response;

    @BeforeClass
    public static void startServer() {
        wireMockServer.start();
    }

    @AfterClass
    public static void stopServer() {
        wireMockServer.stop();
    }

    @Test
    public void stubbingTest01() throws IOException {
        stubFor(get(urlEqualTo("/some/thing"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Hello world!")));
        HttpGet request = new HttpGet("http://localhost:8080/some/thing");
        HttpResponse httpResponse = httpClient.execute(request);
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        assertEquals(404, httpClient.execute(new HttpGet("http://localhost:8080/some/thing/else")).getStatusLine().getStatusCode());
    }

    /**
     * Simplified stubbing using Get, Post, Put, Delete
     *
     */
    @Test
    public void stubbingTest02() throws IOException {
        stubFor(get("/example02")
                .willReturn(aResponse().withStatus(200)));
        assertEquals(200, httpClient.execute(new HttpGet("http://localhost:8080/example02")).getStatusLine().getStatusCode());

        stubFor(get("/fine-with-body")
                .willReturn(ok("body content")));
        response = httpClient.execute(new HttpGet("http://localhost:8080/fine-with-body"));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("body content", convertResponseToString(response));

        stubFor(get("/json")
                .willReturn(okJson("{ \"message\": \"Hello\" }")));
        response = httpClient.execute(new HttpGet("http://localhost:8080/json"));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("{ \"message\": \"Hello\" }", convertResponseToString(response));

        stubFor(post("/sorry-no")
                .willReturn(unauthorized()));
        response = httpClient.execute(new HttpGet("http://localhost:8080/sorry-no"));
        assertEquals(404, response.getStatusLine().getStatusCode());


        stubFor(put("/status-only")
                .willReturn(aResponse().withStatus(418).withBody("status")));
//        response = httpClient.execute(new HttpPut("http://localhost:8080/status-only"));
//        assertEquals(418, response.getStatusLine().getStatusCode());

        stubFor(delete("/fine")
                .willReturn(okJson("OK")));
//        assertEquals(200, httpClient.execute(new HttpDelete("http://localhost:8080/fine")).getStatusLine().getStatusCode());

        stubFor(post("/redirect")
                .willReturn(temporaryRedirect("/new/place")));

    }

    private static String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String stringResponse = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return stringResponse;
    }
}
