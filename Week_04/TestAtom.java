import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/10 0010 19:29
 */
public class TestAtom {

    public static void main(String[] args) {
        AtomicInteger a = new AtomicInteger();
        int res = a.get();
        System.out.println(res);
        // 先获取再自增
        int res1 = a.getAndIncrement();
        System.out.println(res1);
        // 先自增再获取
        int res2 = a.incrementAndGet();
        System.out.println(res2);

        int res3 = a.addAndGet(5);
        System.out.println(res3);
        int res4 = a.getAndAdd(3);
        System.out.println(res4);
        System.out.println(a.get());
        a.set(2);
        System.out.println(a.get());
        // 如果是期待的值，则更新为新值，返回true
        boolean b = a.compareAndSet(2,8);
        System.out.println(a.get());
        System.out.println(b);
    }

}
