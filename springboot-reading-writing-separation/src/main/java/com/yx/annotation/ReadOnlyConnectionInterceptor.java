package com.yx.annotation;

import com.yx.config.DbContextHolder;
import com.yx.enums.DbType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/2 0002 16:27
 */
@Aspect
@Component
@Slf4j
public class ReadOnlyConnectionInterceptor implements Ordered {


    @Around("@annotation(readOnlyConnection)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, ReadOnlyConnection readOnlyConnection) throws Throwable {
        try {
            log.info("---------------set database connection  read only---------------");
            DbContextHolder.slave();
            Object result = proceedingJoinPoint.proceed();
            return result;
        }finally {
            DbContextHolder.clearDbType();
            log.info("---------------clear database connection---------------");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}