package com.inflearn.toby.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientApiExecutor implements ApiExecutor {
    @Override
    public String execute(URI uri) throws IOException {
        HttpRequest httpRequest = HttpRequest.newBuilder(uri)
                .GET()
                .build();

        try (HttpClient client = HttpClient.newBuilder().build()) {
            return client
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .body();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
