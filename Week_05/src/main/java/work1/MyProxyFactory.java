package work1;

import org.springframework.cglib.proxy.Proxy;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 10:24
 */
public class MyProxyFactory<T> {


    private static Object getServiceBase(Object obj,MyAspect myAspect) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new AopProxyHandle(obj,myAspect));
    }

    /**
     * 获取对象方法
     * @param obj
     * @return
     */
    public static <T> T getService(T obj,MyAspect myAspect){
        return (T) getServiceBase(obj, myAspect);
    }

    /**
     * 获取对象方法
     * @param className
     * @return
     */
    public static <T> T getService(String className,MyAspect myAspect){
        Object obj = null;
        try {
            obj = getServiceBase(Class.forName(className).newInstance(), myAspect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

    /**
     * 获取对象方法
     * @param clz
     * @return
     */
    public static <T>T getService(Class clz,MyAspect myAspect){
        Object obj = null;
        try {
            obj = getServiceBase(clz.newInstance(), myAspect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }

}
