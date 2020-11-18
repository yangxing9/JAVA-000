package singleton;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 19:08
 */
public class Singleton3 {

    /**
     * 静态内部类，和懒汉一样，都是在类加载（调用静态方法）的时候初始化，比懒汉优点就是可以存在其他静态方法
     */
    private Singleton3(){};

    public static Singleton3 getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder{
        private static final Singleton3 INSTANCE = new Singleton3();
    }

}
