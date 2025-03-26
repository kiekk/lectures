package com.inflearn.toby.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

public interface ExRateExtractor {
    BigDecimal extract(String currency, String response) throws JsonProcessingException;
}
