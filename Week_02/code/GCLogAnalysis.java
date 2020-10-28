package code;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/22 0022 11:01
 * 这个示例除了可以用来进行GC日志分析之外，稍微修改一下，还可以用
 *  作其他用途：
 *      比如让缓存的对象变多，在限制堆内存的情况下，就可以模拟 内存溢出 。
 *      增加运行时长，比如加到30分钟或者更长，我们就可以用前面介绍过的VisualVM 等工具来实时监控和观察。
 *      当然，我们也可以使用全局静态变量来缓存，用来模拟 内存泄漏 ，以及进行堆内存Dump的试验和分析。
 *      加大每次生成的数组的大小，可以用来模拟 大对象/巨无霸对象 （大对象/巨无霸对象主要是G1中的概念，比如超过1MB的数组）。
 */
public class GCLogAnalysis {

    private static Random random = new Random();
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long timeOut = TimeUnit.SECONDS.toMillis(1);
        long end = start + timeOut;
        LongAdder counter = new LongAdder();
        System.out.println("开始执行");
        int cacheSize = 2000;
        Object[] cacheGarbage = new Object[cacheSize];
        while (System.currentTimeMillis() < end){
            Object garbage = generateGarbage(1000 * 1024);
            counter.increment();
            int randowIndex = random.nextInt(2 * cacheSize);
            if (randowIndex < cacheSize){
                cacheGarbage[randowIndex] = garbage;
            }
        }
        System.out.println("执行结束，共生成对象次数：" + counter.longValue());
    }

    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object res = null;
        switch (type){
            case 0:
                res = new int[randomSize];
                break;
            case 1:
                res = new byte[randomSize];
                break;
            case 2:
                res = new double[randomSize];
                break;
            default:
                StringBuilder sb = new StringBuilder();
                String randomString = "randomString-anything";
                while (sb.length() < randomSize){
                    sb.append(randomString);
                    sb.append(max);
                    sb.append(randomString);
                }
                res = sb.toString();
                break;
        }
        return res;
    }

}
