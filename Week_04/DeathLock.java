/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/10 0010 14:52
 */
public class DeathLock {

    public static void main(String[] args) throws InterruptedException {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Runnable task1 = () -> {
            synchronized (lock1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2){
                    System.out.println("aaaaa");
                }
            }
        };

        Runnable task2 = () -> {
            synchronized (lock2){
                synchronized (lock1){
                    System.out.println("bbbbb");
                }
            }
        };

        Thread a = new Thread(task1,"aaaa");
        Thread b = new Thread(task2,"bbbb");
        a.start();
        b.start();
    }

}
