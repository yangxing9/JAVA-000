package com.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/19 0019 14:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCache {

    /**
     * 缓存key
     */
    String key();

    /**
     * 是否缓存空值
     */
    boolean cacheNull() default false;

    /**
     * 生存时间，单位是秒，默认为-1(永不过期)
     */
    int ttl() default -1;

    /**
     * 生存状态
     * true:每访问一次，将刷新存活时间
     * false:不刷新存活时间，时间一到就清除
     */
    boolean state() default true;

    CacheEnum cacheStrategy();
}
