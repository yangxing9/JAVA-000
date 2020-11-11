import java.util.stream.IntStream;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/10 0010 15:27
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        Runnable task1 = () -> {
            IntStream.range(0,10).forEach((a) -> {
                System.out.println("first" + a);
            });
        };
        Runnable task2 = () -> {
            IntStream.range(0,10).forEach((a) -> {
                System.out.println("second" + a);
            });
        };

        Thread a = new Thread(task1,"first");
        Thread b = new Thread(task2,"second");
        a.start();
        a.join();
        b.start();
    }

}
