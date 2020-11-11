import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/10 0010 13:55
 */
public class ThreePrintThread {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition first = lock.newCondition();
        Condition second = lock.newCondition();
        Condition third = lock.newCondition();
        Runnable task1 = () -> {
            try {
                lock.lock();
                for (int i = 0; i < 100; i++) {
                    if ((i % 3) == 0){
                        System.out.println(Thread.currentThread().getName() + ": " + i);
                        second.signal();
                        if (i < 99){
                            first.await();
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
                    if ((i % 3) == 1){
                        System.out.println(Thread.currentThread().getName() + ": " + i);
                        third.signal();
                        if (i < 97){
                            second.await();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        };

        Runnable task3 = () -> {
            try {
                lock.lock();
                for (int i = 0; i < 100; i++) {
                    if ((i % 3) == 2){
                        System.out.println(Thread.currentThread().getName() + ": " + i);
                        first.signal();
                        if (i < 98){
                            third.await();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        };

        Thread one = new Thread(task1,"第一个");
        Thread two = new Thread(task2,"第二个");
        Thread three = new Thread(task3,"第三个");
        one.start();
        Thread.sleep(100);
        two.start();
        Thread.sleep(100);
        three.start();
    }

    public static void main2(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition first = lock.newCondition();
        Condition second = lock.newCondition();
        Condition third = lock.newCondition();
        AtomicInteger count = new AtomicInteger(0);
        Runnable task1 = () -> {
            try {
                lock.lock();
                while (true) {
                    while ((count.get() % 3) != 0 && count.get() < 99){
                        first.await();
                    }
                    if (count.get() >= 100){
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + count.get());
                    count.getAndIncrement();
                    second.signal();
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
                while (true) {
                    while ((count.get() % 3) != 1){
                        second.await();
                    }
                    if (count.get() >= 100){
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + count.get());
                    count.getAndIncrement();
                    third.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        };

        Runnable task3 = () -> {
            try {
                lock.lock();
                for (;;) {
                    while ((count.get() % 3) != 2 && count.get() < 98){
                        third.await();
                    }
                    if (count.get() >= 99){
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + count.get());
                    count.getAndIncrement();
                    first.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        };

        Thread one = new Thread(task1,"第一个");
        Thread two = new Thread(task2,"第二个");
        Thread three = new Thread(task3,"第三个");
        one.start();
        two.start();
        three.start();
    }

}
