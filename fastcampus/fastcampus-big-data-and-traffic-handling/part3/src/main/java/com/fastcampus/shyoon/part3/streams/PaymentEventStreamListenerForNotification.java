package com.fastcampus.shyoon.part3.streams;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * redis에 등록된 event들을 실시간으로 수신하기 위한 listener
 */
@Component
public class PaymentEventStreamListenerForNotification implements StreamListener<String, MapRecord<String, String, String>> {

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        Map<String, String> map = message.getValue();
        System.out.println(map);

        String userId = map.get("userId");
        String paymentProcessId = map.get("paymentProcessId");

        // 결제 완료 건에 대한 SMS 발송 처리
        // ...

        System.out.println("[Payment Consumed] userId: " + userId + " paymentProcessId: " + paymentProcessId);
    }
}
