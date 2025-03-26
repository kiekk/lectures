package com.inflearn.toby.exrate.provider;

import com.inflearn.toby.api.ApiTemplate;
import com.inflearn.toby.api.ErApiExRateExtractor;
import com.inflearn.toby.api.HttpClientApiExecutor;
import com.inflearn.toby.payment.ExRateProvider;

import java.math.BigDecimal;

public class WebApiExRateProvider implements ExRateProvider {

    private final ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExRate(String currency) {
        String urlString = "https://open.er-api.com/v6/latest/" + currency;
        return apiTemplate.getExRate(currency, urlString, new HttpClientApiExecutor(), new ErApiExRateExtractor());
    }
}
