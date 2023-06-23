package com.example.api.service;

import com.example.api.domain.Coupon;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;

    public ApplyService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void apply(Long userId) {
        long count = couponRepository.count();

        // 발급 가능 개수 확인
        if (count > 100) {
            return;
        }

        couponRepository.save(new Coupon(userId));
    }

}
