package router;

import java.util.Collection;
import java.util.Random;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 20:39
 */
public class RandomRouter<T> implements Router<T> {

    private final Random random = new Random();

    @Override
    public T route(Collection<T> server) {
        if (server == null || server.size() == 0) {
            throw new IllegalArgumentException("Empty server.");
        }

        if (server.size() == 1) {
            return (T) server.toArray()[0];
        }

        int size = server.size();
        /**
         *  random select
         */
        int randomIndex = random.nextInt(size);

        return (T) server.toArray()[randomIndex];
    }

}
