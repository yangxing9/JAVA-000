package com.cache;


import com.TestserviceApplication;
import com.cache.cacheimpl.CacheUtils;
import com.service.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/19 0019 14:38
 */
@Aspect
@Component
public class MyAspectCache {

    @Autowired
    private CacheUtils cacheUtils;

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.cache.MyCache)")
    private void cut() {
        // do nothing
    }

    /**
     * 环绕通知
     */
    @Around("cut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 读取缓存注解
        MyCache myCache = this.getMethodAnnotation(joinPoint);
        // 读取类注解
        CacheConfig cacheConfig = this.getClassAnnotation(joinPoint);
        // 获取方法传入参数
        Object[] params = joinPoint.getArgs();
        // 获得解释之后的key
        String strKey = this.getKey(cacheConfig, myCache, params);
        System.out.println("解释之后的key: " + strKey);
        // 在方法执行前判断是否存在缓存
        Object object = cacheUtils.getCache(strKey, myCache);
        if (object == null) {
            // 创建缓存
            object = this.createCache(joinPoint, strKey, myCache);
        }
        return object;
    }

    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    private MyCache getMethodAnnotation(JoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);

        // 拿到方法定义的注解信息
        return AnnotationUtils.getAnnotation(objMethod, MyCache.class);
    }

    /**
     * 获取类中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    private CacheConfig getClassAnnotation(JoinPoint joinPoint) throws NoSuchMethodException {
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        return targetClass.getDeclaredAnnotation(CacheConfig.class);
    }


    /**
     * 解析key表达式，得到实际的key
     *
     * @param myCacheable
     * @param params
     * @return
     */
    private String getKey(CacheConfig cacheConfig, MyCache myCacheable, Object[] params) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        // 获得原始key的表达式
        String strSourceKey = myCacheable.key();
        int intSeq = -1;
        String strSearchSeq = null;
        int intStartPos = 0;
        // 用SPEL解析表达式
        while (++intSeq < params.length) {
            strSearchSeq = "#p" + intSeq;
            intStartPos = StringUtils.indexOf(strSourceKey, strSearchSeq, intStartPos);
            if (intStartPos < 0) {
                break;
            } else {
                ctx.setVariable("p" + intSeq, params[intSeq]);
            }
        }
        // 执行表达式
        Expression expression = parser.parseExpression(strSourceKey);
        String strKey = expression.getValue(ctx).toString();
        // 拼接上缓存名称,spring cache会加上前缀,是在CacheConfig中配置的。
        if (cacheConfig != null) {
            strKey = cacheConfig.cacheNames()[0] + ":" + strKey;
        }
        return strKey;
    }

    /**
     * 创建缓存
     *
     * @param joinPoint
     * @param strKey
     * @param myCacheable
     * @return
     * @throws Throwable
     */
    private Object createCache(ProceedingJoinPoint joinPoint, String strKey, MyCache myCacheable) throws Throwable {
        // 没有缓存则执行目标方法
        // 获取目标方法的名称
        String methodName = joinPoint.getSignature().getName();
        System.out.println("目标执行方法: " + methodName);
        // 执行源方法
        Object object = joinPoint.proceed();

        cacheUtils.putKey(strKey,object,myCacheable);
        return object;
    }
}
