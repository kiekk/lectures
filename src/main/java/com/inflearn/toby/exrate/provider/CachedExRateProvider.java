package com.inflearn.toby.exrate.provider;

import com.inflearn.toby.payment.ExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CachedExRateProvider implements ExRateProvider {

    private final ExRateProvider target;
    private BigDecimal cachedExRate; // 캐시된 환율
    private LocalDateTime cacheExpiryTime; // 캐시 만료 시간

    public CachedExRateProvider(ExRateProvider target) {
        this.target = target;
    }

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        if (cachedExRate == null || cacheExpiryTime.isBefore(LocalDateTime.now())) {
            cachedExRate = target.getExRate(currency);
            cacheExpiryTime = LocalDateTime.now().plusSeconds(3);

            System.out.println("Cache Updated");
        }
        return cachedExRate;
    }
}
