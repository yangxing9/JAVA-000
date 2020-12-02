package com.yx.config;

import com.yx.enums.DbType;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/2 0002 16:25
 */
@Slf4j
public class DbContextHolder {

    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<>();
    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void setDbType(DbType dbType){
        if (dbType==null) {
            throw new NullPointerException();
        }
        contextHolder.set(dbType);
    }

    public static DbType getDbType(){
        return contextHolder.get() == null ? DbType.MASTER : contextHolder.get();
    }

    public static void master() {
        setDbType(DbType.MASTER);
        log.info(" ========> this is master");
    }

    public static void slave() {
        //  轮询
        int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999) {
            counter.set(-1);
        }
        if (index == 0) {
            setDbType(DbType.SLAVE1);
            log.info(" ========> this is slave1");
        } else {
            setDbType(DbType.SLAVE2);
            log.info(" ========> this is slave2");
        }
    }


    public static void clearDbType(){
        contextHolder.remove();
    }
}
