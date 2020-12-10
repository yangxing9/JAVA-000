package com.yx1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yangxing
 * @version 1.0.0
 * @Description
 * @createTime 2019年10月25日 16:45
 */
@SpringBootApplication
@MapperScan("com.yx1.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
