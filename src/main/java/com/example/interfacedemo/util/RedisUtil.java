package com.example.interfacedemo.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 根据key读取数据
     */
    public Object get(final String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入数据
     */
    public boolean set(final String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
