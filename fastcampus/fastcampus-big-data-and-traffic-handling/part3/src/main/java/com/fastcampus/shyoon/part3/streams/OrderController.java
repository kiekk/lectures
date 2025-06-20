package com.fastcampus.shyoon.part3.streams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/order")
    public String order(@RequestParam String userId,
                        @RequestParam String productId,
                        @RequestParam String price) {
        Map<String, String> fieldMap = Map.of("userId", userId, "productId", productId, "price", price);
        stringRedisTemplate.opsForStream().add("order-events", fieldMap);

        System.out.println("Order created");
        return "ok";
    }

}
