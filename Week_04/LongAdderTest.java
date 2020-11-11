import java.util.concurrent.atomic.LongAdder;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/10 0010 19:38
 */
public class LongAdderTest {

    public static void main(String[] args) {
        final LongAdder longAdder = new LongAdder();
        System.out.println(longAdder.longValue());
        longAdder.increment();
        System.out.println(longAdder.longValue());
        longAdder.add(1);
        System.out.println(longAdder.longValue());
        longAdder.reset();
        System.out.println(longAdder.longValue());
        longAdder.add(100);
        long res = longAdder.sum();
        System.out.println(res);
    }

}
