package com.controller;

import com.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheService cacheService;

    @GetMapping("/get")
    Object insert(){
        redisTemplate.boundValueOps("yangxing").set("yx",1, TimeUnit.MINUTES);

        System.out.println(redisTemplate.hasKey("yangxing"));
        System.out.println(redisTemplate.boundValueOps("yangxing").get());
        return new Object();
    }


    @GetMapping("/cache")
    Object testCache(){
        return cacheService.cacheStudent(2);
    }

}
