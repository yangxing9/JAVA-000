import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/11 0011 14:54
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws Exception {
        testInvorkAll();
    }

    private static void testCountDownLatch() throws Exception {
        long start = System.currentTimeMillis();
        int max = 100;
        Map<Integer,Integer> map = new ConcurrentHashMap<>();
        map.put(1,0);
        AtomicInteger a = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        CountDownLatch countDownLatch = new CountDownLatch(max);
        Runnable task = () -> {
            try {
                if (map.get(1) != null){
                    int i = 10 / 0;
                }else{
                    System.out.println("发生异常，不执行");
                    a.incrementAndGet();
                    return;
                }
                System.out.println("正常输出结果");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("执行异常");
                map.remove(1);
            }finally {
                countDownLatch.countDown();
//                System.out.println(Thread.currentThread().getName() + ":::" + countDownLatch.getCount());
            }
        };
        for (int i = 0; i < max; i++) {
            executorService.submit(task);
        }
        countDownLatch.await();
        System.out.println("耗时："+ (System.currentTimeMillis() - start));
        System.out.println(a.get());
    }

    private static void testInvorkAll() throws InterruptedException, TimeoutException, ExecutionException {
        long start = System.currentTimeMillis();
        int max = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        Callable<Integer> task = () -> {
            try {
                Thread.sleep(10000);
                System.out.println("正常结束");
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("执行异常");
                return -1;
            }
        };
        List<Future<Integer>> futures = new ArrayList<>();
//        List list = new ArrayList(max);
        for (int i = 0; i < 2; i++) {
            Future future = executorService.submit(task);
            futures.add(future);
        }

        int res = 0;
        for (Future<Integer> future : futures) {
            boolean cancelRes = future.cancel(true);
            System.out.println("取消结果：" + cancelRes);
        }
        for (Future<Integer> future : futures) {
            System.out.println(future.isCancelled());
        }
        System.out.println("耗时："+ (System.currentTimeMillis() - start));
        System.out.println(res);
    }
}
