/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/10 0010 15:40
 */
public class ProductCustomer {

    private static final Integer MAX_NUMS = 100;

    private Integer curNum = 0;

    public static void main(String[] args) {
        ProductCustomer productCustomer = new ProductCustomer();
        Runnable productTask = () -> {
            try {
                productCustomer.product();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable customerTask = () -> {
            try {
                productCustomer.customer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable customerTask2 = () -> {
            try {
                productCustomer.customer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread productThread = new Thread(productTask,"productTask");
        Thread customerThread = new Thread(customerTask,"customerTask");
        Thread customerThread2 = new Thread(customerTask2,"customerTask2222");
        productThread.start();
        customerThread.start();
        customerThread2.start();
    }

    private synchronized void product() throws InterruptedException {
        while (true){
            System.out.println(Thread.currentThread().getName() + ",当前货仓为：" + curNum + "");
            if (curNum >= MAX_NUMS){
                System.out.println(Thread.currentThread().getName() + ",当前货仓已满，数量为" + curNum + "，停止生产");
                wait();
            }else {
                curNum++;
            }
            notifyAll();
        }
    }

    private synchronized void customer() throws InterruptedException {
        while (true){
            System.out.println(Thread.currentThread().getName() + ",开始消费，货仓为+ " + curNum);
            if (curNum > 0){
                curNum--;
            }else {
                System.out.println(Thread.currentThread().getName() + "货仓已空，停止消费");
                wait();
            }
            notifyAll();
        }
    }
}
