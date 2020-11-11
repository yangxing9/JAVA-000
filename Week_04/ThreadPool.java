import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/10 0010 16:55
 */
public class ThreadPool {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                10,
                2000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new DefaultThreadFactory("yax")
        );
        int core = executor.getCorePoolSize();
        int active = executor.getActiveCount();
        System.out.println(core);
        System.out.println(active);

        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("aaa");
                }
            });
        }

        Thread.sleep(1000);
        System.out.println(core);
        System.out.println(active);
    }

}
