import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/10 0010 20:21
 */
public class DeathLock2 {

    /**
     * 程序并未执行线程的run方法，由此我们可知，上面的代码会出现死锁，因为主线程2次获取了锁，但是却只释放1次锁，导致线程t永远也不能获取锁。一个线程获取多少次锁，就必须释放多少次锁
     */
    public static void main1(String[] args) throws InterruptedException {
        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock ();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.writeLock().lock();
                System.out.println("Thread real execute");
                lock.writeLock().unlock();
            }
        });

        lock.writeLock().lock();
        lock.writeLock().lock();
        t.start();
        Thread.sleep(200);

        System.out.println("realse one once");
        lock.writeLock().unlock();
    }


    /**
     * 下面的测试代码会产生死锁，因为同一个线程中，在没有释放读锁的情况下，就去申请写锁，这属于锁升级，ReentrantReadWriteLock是不支持的。
     * @param args
     */
    public static void main2(String[] args) {
        ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();
        rtLock.readLock().lock();
        System.out.println("get readLock.");
        rtLock.writeLock().lock();
        System.out.println("blocking");
    }

    /**
     * ReentrantReadWriteLock支持锁降级，下面代码不会产生死锁。这段代码虽然不会导致死锁，但没有正确的释放锁。
     * 从写锁降级成读锁，并不会自动释放当前线程获取的写锁，仍然需要显示的释放，否则别的线程永远也获取不到写锁。
     *
     */
    public static void main(String[] args) {
        ReentrantReadWriteLock rtLock = new ReentrantReadWriteLock();
        rtLock.writeLock().lock();
        System.out.println("writeLock");

        rtLock.readLock().lock();
        System.out.println("get read lock");
    }
}
