package com.inflearn.toby.exrate.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inflearn.toby.api.ApiExecutor;
import com.inflearn.toby.api.SimpleApiExecutor;
import com.inflearn.toby.exrate.ExRateData;
import com.inflearn.toby.payment.ExRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

public class WebApiExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) {
        String urlString = "https://open.er-api.com/v6/latest/" + currency;
        return runApiForExRate(currency, urlString);
    }

    private BigDecimal runApiForExRate(String currency, String urlString) {
        URI uri;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            response = new SimpleApiExecutor().execute(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return extractExRate(currency, response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private BigDecimal extractExRate(String currency, String response) throws JsonProcessingException {
        ExRateData exRateData = parseExRate(response);
        return exRateData.rates().get(currency);
    }

    private ExRateData parseExRate(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, ExRateData.class);
    }
}
