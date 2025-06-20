package com.example.api.service;

import com.example.api.kafka.producer.CouponCreateProducer;
import com.example.api.repository.AppliedUserRepository;
import com.example.api.repository.CouponCountRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final AppliedUserRepository appliedUserRepository;

    public ApplyService(CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void apply(Long userId) {
        Long apply = appliedUserRepository.add(userId);

        // 이미 발급된 유저
        if (apply != 1) {
            return;
        }

        Long count = couponCountRepository.increment();

        // 발급 가능 개수 확인
        if (count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }

}
