package com.fastcampus.shyoon.part3.streams;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * redis에 등록된 event들을 실시간으로 수신하기 위한 listener
 */
@Component
public class OrderEventStreamListenerForNotification implements StreamListener<String, MapRecord<String, String, String>> {

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        Map<String, String> map = message.getValue();
        System.out.println(map);

        String userId = map.get("userId");
        String productId = map.get("productId");

        // 주문 건에 대한 메일 발송 처리
        // ...

        System.out.println("[Order Consumed] userId: " + userId + " productId: " + productId);
    }
}
