package work1;

import java.lang.reflect.Method;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 10:25
 */
public interface MyAspect {

    void before(Object proxy, Method method, Object[] args);

    void after(Object proxy, Method method, Object[] args);

}
