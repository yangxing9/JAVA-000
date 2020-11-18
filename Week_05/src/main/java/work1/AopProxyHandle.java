package work1;

import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 10:21
 */
public class AopProxyHandle<T> implements InvocationHandler {

    /**
     * 目标代理类
     */
    private T proxyObject;

    /**
     * 切面类
     */
    private MyAspect myAspect;

    public AopProxyHandle(T proxyObject,MyAspect myAspect) {
        this.proxyObject = proxyObject;
        this.myAspect = myAspect;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isNeedAop(proxyObject,method)){
            myAspect.before(proxy,method,args);
        }
        System.out.println("被代理对象：" + proxy.getClass());
        System.out.println("代理对象：" + proxy.getClass());
        System.out.println("被调用的方法：" + method.getName());
        System.out.println("被调用方法的参数：" + Arrays.toString(args));
        Object obj = method.invoke(proxyObject, args);
        if (isNeedAop(proxyObject,method)){
            myAspect.after(proxy, method, args);
        }
        return obj;
    }

    public static boolean isNeedAop(Object proxy, Method method) {
        try {
            // 获得所有方法，查找方法注解
            Method[] methods = proxy.getClass().getDeclaredMethods();
            for (Method m : methods) {
                // 获得方法注解
                EnableProxy s = m.getAnnotation(EnableProxy.class);
                if (s != null && Objects.equals(m.getName(),method.getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
