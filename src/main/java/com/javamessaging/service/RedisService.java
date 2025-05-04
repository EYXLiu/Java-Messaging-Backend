package com.javamessaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;;

@Service
public class RedisService {
    
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void setUserOnline(String id) {
        redisTemplate.opsForValue().set("online:" + id, "true");
    }

    public void setUserOffline(String id) {
        redisTemplate.delete("online:" + id);
    }

    public boolean isUserOnline(String id) {
        return redisTemplate.hasKey("online:" + id);
    }

    public void cacheLastMessage(String messageJson) {
        redisTemplate.opsForValue().set("last", messageJson);
    }
}
