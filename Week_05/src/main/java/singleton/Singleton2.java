package singleton;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 19:08
 */
public class Singleton2 {

    /**
     * 懒汉式写法，double check
     * volatile是为了防止多线程下代码 24行 的指令重排序
     * 写法复杂，使用双重检验保证线程安全，使用volatile保证不会指令重拍导致不安全，实际上最复杂
     */
    private volatile static Singleton2 instance;

    private Singleton2(){};

    public static Singleton2 getInstance(){
        // 判空是为了减少同步方法的使用，提高效率
        if (instance == null){
            // 加锁和判空是为了防止多线程下重复实例化单例。
            synchronized (Singleton2.class){
                if (instance == null){
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }


}
