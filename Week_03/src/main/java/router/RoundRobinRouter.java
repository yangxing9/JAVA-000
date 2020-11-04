package router;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 20:42
 */
public class RoundRobinRouter<T> implements Router<T> {

    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public T route(Collection<T> server) {
        if (server == null || server.size() == 0) {
            throw new IllegalArgumentException("Empty source.");
        }

        if (server.size() == 1) {
            return (T) server.toArray()[0];
        }

        int size = server.size();

        return (T) server.toArray()[index.getAndIncrement() % size];
    }
}
