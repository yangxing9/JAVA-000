package com.cache.cacheimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/19 0019 15:55
 */
@Component
public class RedisUtils {


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据key获取对应的value值
     * @param key
     * @return
     */
    public Object getValue(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 根据key获取 值，如果值存在，且配置了生存状态为true，且超时时间不为永久，则刷新存活时间
     * @param key
     * @param state
     * @param ttl
     * @return
     */
    public Object getCache(String key, boolean state, int ttl) {
        Object obj = getValue(key);
        if (obj != null && state && ttl != -1) {
            // 存在缓存&每次访问重置TTL&非永不过期
            // 每次访问后重新刷新TTL，还原为原来值
            redisTemplate.expire(key,ttl,TimeUnit.SECONDS);
        }
        return obj;
    }

    /**
     * 缓存对象
     * @param key
     * @param value
     * @param cacheNull
     * @return
     * @throws Throwable
     */
    public Object putValue(String key, Object value,boolean cacheNull, int ttl) {
        if (value != null) {
            // 设置缓存
            if (ttl == -1){
                redisTemplate.boundValueOps(key).set(value);
            }else {
                redisTemplate.boundValueOps(key).set(value, ttl, TimeUnit.SECONDS);
            }
        } else {
            // 判断是否缓存null
            if (cacheNull) {
                redisTemplate.boundValueOps(key).set(null, ttl, TimeUnit.SECONDS);
            }
        }
        return value;
    }
}
