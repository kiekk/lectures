package com.inflearn.toby.exrate.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inflearn.toby.api.ApiExecutor;
import com.inflearn.toby.api.ExRateExtractor;
import com.inflearn.toby.api.SimpleApiExecutor;
import com.inflearn.toby.exrate.ExRateData;
import com.inflearn.toby.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class WebApiExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) {
        String urlString = "https://open.er-api.com/v6/latest/" + currency;
        return runApiForExRate(currency, urlString, new SimpleApiExecutor(), (currency1, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            ExRateData exRateData = mapper.readValue(response, ExRateData.class);
            return exRateData.rates().get(currency1);
        });
    }

    private BigDecimal runApiForExRate(String currency, String urlString, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
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
