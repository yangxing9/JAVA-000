package com.service;

import com.cache.CacheEnum;
import com.cache.MyCache;
import com.po.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/19 0019 18:07
 */
@Component
public class CacheService {


//    @MyCache(key = "'cache'+#p0", ttl = 30, state = true,cacheStrategy = CacheEnum.REDIS)
//    @MyCache(key = "'cache'+#p0", ttl = 30, cacheStrategy = CacheEnum.REDIS)
    @MyCache(key = "'yx'+#p0",cacheNull = true, cacheStrategy = CacheEnum.REDIS)
    public Student cacheStudent(int index){
        Student student = new Student();
        student.setId("123");
        student.setName("yangxing");
        student.setAge(25);
        return null;
    }

}
