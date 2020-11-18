package work1;

import java.lang.reflect.Method;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 13:54
 */
public class MyAspectImpl implements MyAspect {

    @Override
    public void before(Object proxy, Method method, Object[] args) {
        System.out.println("==============开始代理之前=================");
    }

    @Override
    public void after(Object proxy, Method method, Object[] args) {
        System.out.println("==============开始代理之后=================");
    }
}
