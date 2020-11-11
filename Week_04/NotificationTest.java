/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/9 0009 16:31
 */
public class NotificationTest {

    private volatile boolean go = false;

    public static void main(String args[]) throws InterruptedException {
        final NotificationTest test = new NotificationTest();

        Runnable waitTask = new Runnable(){
            @Override
            public void run(){
                try {
                    test.shouldGo();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.println(Thread.currentThread() + " finished Execution");
            }
        };

        Runnable notifyTask = new Runnable(){

            @Override
            public void run(){
                test.go();
                System.out.println(Thread.currentThread() + " finished Execution");
            }
        };

        //will wait
        Thread t1 = new Thread(waitTask, "WT1");
        //will wait
        Thread t2 = new Thread(waitTask, "WT2");
        //will wait
        Thread t3 = new Thread(waitTask, "WT3");
        //will notify
        Thread t4 = new Thread(notifyTask,"NT1");

        //starting all waiting thread
        t1.start();
        t2.start();
        t3.start();

        //pause to ensure all waiting thread started successfully
        Thread.sleep(200);

        //starting notifying thread
        t4.start();

    }
    /**
     * wait and notify can only be called from synchronized method or bock
     */
    private synchronized void shouldGo() throws InterruptedException {
        while(go != true){
            System.out.println(Thread.currentThread()
                    + " is going to wait on this object");
            wait(); //release lock and reacquires on wakeup
            System.out.println(Thread.currentThread() + " is woken up");
        }
        //resetting condition
        go = false;
    }

    /**
     * both shouldGo() and go() are locked on current object referenced by "this" keyword
     */
    private synchronized void go() {
        while (go == false){
            System.out.println(Thread.currentThread()
                    + " is going to notify all or one thread waiting on this object");
            //making condition true for waiting thread
            go = true;
//            notify(); // only one out of three waiting thread WT1, WT2,WT3 will woke up
            notifyAll(); // all waiting thread  WT1, WT2,WT3 will woke up
        }

    }


}
