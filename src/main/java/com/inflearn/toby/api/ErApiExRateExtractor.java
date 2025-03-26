package com.inflearn.toby.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inflearn.toby.exrate.ExRateData;

import java.math.BigDecimal;

public class ErApiExRateExtractor implements ExRateExtractor {
    @Override
    public BigDecimal extract(String currency, String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExRateData exRateData = mapper.readValue(response, ExRateData.class);
        return exRateData.rates().get(currency);
    }
}
