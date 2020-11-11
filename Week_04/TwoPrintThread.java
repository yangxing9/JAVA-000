import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/10 0010 13:55
 */
public class TwoPrintThread {

    /**
     * 实现两个线程交替打印奇偶数
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Runnable task1 = () -> {
            synchronized (lock){
                for (int i = 0; i < 100; i++) {
                    if ((i & 1) == 0){
                        System.out.println(Thread.currentThread().getName() + ": " + i);
                        lock.notifyAll();
                        try {
                            if (i < 99){
                                lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Runnable task2 = () -> {
            synchronized (lock){
                for (int i = 0; i < 100; i++) {
                    if ((i & 1) == 1){
                        System.out.println(Thread.currentThread().getName() + ": " + i);
                        lock.notifyAll();
                        try {
                            if (i < 99){
                                lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread one = new Thread(task1,"打印偶数");
        Thread two = new Thread(task2,"打印奇数");
        one.start();
        Thread.sleep(1000);
        two.start();
    }

    /**
     * 实现两个个线程交替打印奇偶数
     * 使用condition实现
     */
    public static void main2(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Runnable task1 = () -> {
            try {
                lock.lock();
                for (int i = 0; i < 100; i++) {
                    if ((i & 1) == 0){
                        System.out.println(Thread.currentThread().getName() + ": " + i);
                        condition.signal();
                        if (i < 99){
                            condition.await();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        };
        Runnable task2 = () -> {
            try {
                lock.lock();
                for (int i = 0; i < 100; i++) {
                    if ((i & 1) == 1){
                        System.out.println(Thread.currentThread().getName() + ": " + i);
                        condition.signal();
                        if (i < 99){
                            condition.await();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        };

        Thread one = new Thread(task1,"打印偶数");
        Thread two = new Thread(task2,"打印奇数");
        one.start();
        Thread.sleep(1000);
        two.start();
    }

}
