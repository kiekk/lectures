package com.example.api.service;

import com.example.api.kafka.producer.CouponCreateProducer;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;

    public ApplyService(CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer) {
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
    }

    public void apply(Long userId) {
        // redis 로 대체
        Long count = couponCountRepository.increment();

        // 발급 가능 개수 확인
        if (count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }

}
