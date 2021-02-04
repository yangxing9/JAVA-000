package proxytest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/17 0017 16:40
 */
public class JdkProxy implements InvocationHandler {

    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始代理");
        Object res = method.invoke(target,args);
        System.out.println("结束代理");
        return res;
    }

    private Object getJDKProxy(Object targetObj){
        this.target = targetObj;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }


    public static void main(String[] args) {
        double a = 30.2;
        double b = 20.1;
        double res = a - b;
        System.out.println(res);
        JdkProxy jdkProxy = new JdkProxy();
        UserManager user = (UserManager) jdkProxy.getJDKProxy(new UserManagerImpl());
        user.addUser("test","password");
    }
}
