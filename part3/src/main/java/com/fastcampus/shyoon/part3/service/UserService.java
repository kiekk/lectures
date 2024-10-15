package com.fastcampus.shyoon.part3.service;

import com.fastcampus.shyoon.part3.dto.UserProfile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private final ExternalApiService externalApiService;
    private final StringRedisTemplate stringRedisTemplate;

    public UserService(ExternalApiService externalApiService, StringRedisTemplate stringRedisTemplate) {
        this.externalApiService = externalApiService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // cache-aside pattern으로 캐싱 구현
    public UserProfile getUserProfile(String userId) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        String userName;
        String cachedName = ops.get("nameKey: " + userId);
        if (cachedName != null) {
            userName = cachedName;
        } else {
            userName = externalApiService.getUserName(userId);
            ops.set("nameKey: " + userId, userName, 5, TimeUnit.SECONDS);
        }

        // @Cacheable annotation으로 캐싱
        int userAge = externalApiService.getUserAge(userId);

        return new UserProfile(userName, userAge);
    }
}
