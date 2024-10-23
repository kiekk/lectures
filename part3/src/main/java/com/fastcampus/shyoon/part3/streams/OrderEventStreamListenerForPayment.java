package com.fastcampus.shyoon.part3.streams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * redis에 등록된 event들을 실시간으로 수신하기 위한 listener
 */
@Component
public class OrderEventStreamListenerForPayment implements StreamListener<String, MapRecord<String, String, String>> {

    int paymentProcessId = 0;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        Map<String, String> map = message.getValue();
        System.out.println(map);

        String userId = map.get("userId");
        String productId = map.get("productId");
        String price = map.get("price");

        // 결제 로직
        String paymentIdStr = Integer.toString(paymentProcessId++);

        // 결제 완료 이벤트 발행
        Map<String, String> fieldMap = Map.of("userId", userId, "productId", productId, "price", price, "paymentProcessId", paymentIdStr);

        stringRedisTemplate.opsForStream().add("payment-events", fieldMap);

        System.out.println("[Order consumed] Created payment: " + paymentIdStr);
    }
}
