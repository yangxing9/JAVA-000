package router;

import java.util.Collection;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 20:38
 */
public interface Router<T> {

    T route(Collection<T> server);

}
