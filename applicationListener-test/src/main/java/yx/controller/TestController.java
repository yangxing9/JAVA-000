package yx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yx.event.MyEvent;
import yx.event.MyEvent2;
import yx.page.BaseResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangxing
 * @version 1.0
 * @date 2021/1/25 0025 16:52
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @PostMapping("/get")
    public BaseResponse get(){
        Map<String,String> res = new HashMap<>();
        res.put("name","yx");
        eventPublisher.publishEvent(new MyEvent(res,"yx"));
        eventPublisher.publishEvent(new MyEvent2(res,"yx"));
        return BaseResponse.success(res);
    }
}
