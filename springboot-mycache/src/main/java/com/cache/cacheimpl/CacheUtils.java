package com.cache.cacheimpl;


import com.cache.MyCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/19 0019 15:56
 */
@Component
public class CacheUtils {

    @Autowired
    private RedisUtils redisUtils;

    /**
     *  读取现有缓存
     * @param key 实际key,非key表达式
     * @param myCacheable 是否刷新存活时间
     * @return
     */
    public Object getCache(String key, MyCache myCacheable){
        Object res = null;
        switch (myCacheable.cacheStrategy()){
            case REDIS:
                res = redisUtils.getCache(key,myCacheable.state(),myCacheable.expireTime());
                break;
            case EHCACHE:
                break;
            case MEMORYCACHE:
                break;
            default:
                System.out.println("===");
        }
        return res;
    }


    public Object putKey(String key, Object value, MyCache myCacheable){
        Object res = null;
        switch (myCacheable.cacheStrategy()){
            case REDIS:
                res = redisUtils.putValue(key,value,myCacheable.cacheNull(),myCacheable.expireTime());
                break;
            case EHCACHE:
                break;
            case MEMORYCACHE:
                break;
            default:
                System.out.println("===");
        }
        return res;
    }
}
