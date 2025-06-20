package com.inflearn.toby.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiTemplate {

    private final ApiExecutor DEFAULT_API_EXECUTOR;
    private final ExRateExtractor DEFAULT_EX_RATE_EXECUTOR;

    public ApiTemplate() {
        this.DEFAULT_API_EXECUTOR = new HttpClientApiExecutor();
        this.DEFAULT_EX_RATE_EXECUTOR = new ErApiExRateExtractor();
    }

    public ApiTemplate(ApiExecutor apiExecutor, ExRateExtractor exRateExecutor) {
        this.DEFAULT_API_EXECUTOR = apiExecutor;
        this.DEFAULT_EX_RATE_EXECUTOR = exRateExecutor;
    }

    public BigDecimal getExRate(String currency, String urlString) {
        return getExRate(currency, urlString, DEFAULT_API_EXECUTOR, DEFAULT_EX_RATE_EXECUTOR);
    }

    public BigDecimal getExRate(String currency, String urlString, ApiExecutor apiExecutor) {
        return getExRate(currency, urlString, apiExecutor, DEFAULT_EX_RATE_EXECUTOR);
    }

    public BigDecimal getExRate(String currency, String urlString, ExRateExtractor exRateExtractor) {
        return getExRate(currency, urlString, DEFAULT_API_EXECUTOR, exRateExtractor);
    }

    public BigDecimal getExRate(String currency, String urlString, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
        URI uri;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            response = apiExecutor.execute(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return exRateExtractor.extract(currency, response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
