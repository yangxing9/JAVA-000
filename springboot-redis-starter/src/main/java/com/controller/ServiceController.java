package com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private RedisTemplate redisTemplate;

    private Map<String,Integer> map = new HashMap();
    {
        map.put("type",1);
    }

    @GetMapping("/get")
    Object insert(){
        redisTemplate.boundValueOps("yx").set("yx",1, TimeUnit.MINUTES);

        System.out.println(redisTemplate.hasKey("yx"));
        return new Object();
    }


}
