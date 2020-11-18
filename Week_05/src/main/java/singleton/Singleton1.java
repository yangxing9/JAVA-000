package singleton;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 19:08
 */
public class Singleton1 {

    /**
     * 饿汉式写法，实际上单例是在本类加载的时候才实例化的，只有当你以某种方式调用了这个类的时候，它才会进行初始化，
     *              而不是jvm启动的时候就初始化，而jvm本身会确保类的初始化只执行一次
     *              如果不使用这个单例对象的话，内存中根本没有Singleton实例对象，也就是和“懒汉模式”是一样的效果
     *
     *      单例类里除了getInstance()方法还有一些其他静态方法，这样当调用其他静态方法的时候，也会初始化实例，解决方案：只要加个内部类就行了
     */
    private final static Singleton1 instance = new Singleton1();

    private Singleton1(){};

    public static Singleton1 getInstance(){
        return instance;
    }


}
