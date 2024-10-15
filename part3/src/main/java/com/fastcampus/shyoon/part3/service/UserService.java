package com.fastcampus.shyoon.part3.service;

import com.fastcampus.shyoon.part3.dto.UserProfile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

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

        String cachedName = ops.get("nameKey: " + userId);
        String userName = cachedName != null ? cachedName : externalApiService.getUserName(userId);

        String cachedAge = ops.get("ageKey: " + userId);
        int userAge = cachedAge != null ? Integer.parseInt(cachedAge) : externalApiService.getUserAge(userId);

        return new UserProfile(userName, userAge);
    }
}
