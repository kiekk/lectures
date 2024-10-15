package com.fastcampus.shyoon.part3.service;

import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    public String getUserName(String userId) {
        // 실제로는 외부 서비스 or DB 호춣
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (userId.equals("A")) {
            return "Adam";
        } else if (userId.equals("B")) {
            return "Bob";
        } else {
            return "unknown";
        }
    }

    public int getUserAge(String userId) {
        return 20;
    }
}
