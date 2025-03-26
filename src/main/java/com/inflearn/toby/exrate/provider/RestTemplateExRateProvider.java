package com.inflearn.toby.exrate.provider;

import com.inflearn.toby.exrate.ExRateData;
import com.inflearn.toby.payment.ExRateProvider;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Objects;

public class RestTemplateExRateProvider implements ExRateProvider {

    private final RestTemplate restTemplate;

    public RestTemplateExRateProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExRate(String currency) {
        String urlString = "https://open.er-api.com/v6/latest/" + currency;
        return Objects.requireNonNull(restTemplate.getForObject(urlString, ExRateData.class)).rates().get(currency);
    }
}
