import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/11 0011 19:27
 */
public class HomeWork {

    private final static AtomicInteger result = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        method9();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    /**
     * 使用 线程join确保任务线程执行完成后执行主线程
     */
    private static void method1() throws InterruptedException {
        Runnable task = () -> result.set(sum());

        Thread thread = new Thread(task,"获取斐波那契值");

        thread.start();
        // 使用join确保线程执行完成
        thread.join();
    }

    /**
     * 使用 countDownLatch 阻塞等到线程 countDown后执行
     */
    private static void method2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable task = () -> {
            result.set(sum());
            countDownLatch.countDown();
        };

        Thread thread = new Thread(task,"获取斐波那契值");
        thread.start();

        // 使用 countDownLatch 阻塞等到线程 countDown后执行
        countDownLatch.await();
    }

    /**
     * 使用 cyclicBarrier 阻塞等到计算线程执行完后，回调执行，同时线程池需要 shutdown
     */
    private static void method3() throws InterruptedException {
        long start = System.currentTimeMillis();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(1,new Runnable() {
            @Override
            public void run() {
                System.out.println("异步计算结果为：" + result);
                System.out.println("使用时间："+ (System.currentTimeMillis() - start) + " ms");
            }
        });
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                4,
                8,
                  5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new DefaultThreadFactory("获取斐波那契值线程池"));

        Runnable task = () -> {
            result.set(sum());
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        threadPoolExecutor.submit(task);
        threadPoolExecutor.shutdown();
    }


    /**
     * 使用 callable 返回计算的值，将值赋予result，通过 Future 的 get 方法阻塞主线程
     */
    private static void method4() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Callable<Integer> task = () -> sum();

        Future<Integer> future = executorService.submit(task);
        result.set(future.get());

        executorService.shutdown();
    }

    /**
     * 使用 CompletableFuture 返回计算的值，通过回调将值赋予 result
     */
    private static void method5() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CompletableFuture.supplyAsync(() -> sum(), executorService)
                .thenAcceptAsync(res -> result.set((Integer) res)).join();
        executorService.shutdown();
    }

    /**
     * 主线程中 自旋不断监测 result是否已被赋值
     */
    private static void method6() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Runnable task = () -> result.set(sum());

        executorService.submit(task);
        while (result.get() == 0);
        executorService.shutdown();
    }

    /**
     * 使用 condition 如主线程拿到的值未被赋值则阻塞，等待异步计算线程释放信号
     */
    private static void method7() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Lock lock = new ReentrantLock();
        Condition calComplete = lock.newCondition();
        Runnable task = () -> {
            try {
                lock.lock();
                result.set(sum());
                calComplete.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        };

        executorService.submit(task);

        lock.lock();
        try {
            while (result.get() == 0) {
                calComplete.await();
            }
        } finally {
            lock.unlock();
        }

        executorService.shutdown();
    }


    private static void method8() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Object lock = new Object();
        Runnable task = () -> {
            synchronized (lock){
                result.set(sum());
                lock.notifyAll();
            }
        };

        executorService.submit(task);
        synchronized (lock) {
            while (result.get() == 0) {
                lock.wait();
            }
        }
        executorService.shutdown();
    }

    private static void method9() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Semaphore semaphore = new Semaphore(1);
        Runnable task = () -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.set(sum());
            semaphore.release();
        };

        executorService.submit(task);
        semaphore.acquire();
        while (result.get() == 0) {
            semaphore.release();
        }
        executorService.shutdown();
    }


    private static int sum() {
        return fibo(60);
    }

    /**
     * @param a [1 - ...]
     * @return 第 a 个斐波那契值
     */
    private static int fibo(int a) {
        if ( a <= 2) return 1;
        int first = 1;
        int secoed = 1;
        int res = 0;
        for (int i = 3; i <= a; i++) {
            res = first + secoed;
            first = secoed;
            secoed = res;
        }
        return res;
    }

}
