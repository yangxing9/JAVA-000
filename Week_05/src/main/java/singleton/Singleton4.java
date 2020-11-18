package singleton;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 19:08
 */
public enum Singleton4 {

    /**
     * 枚举，枚举类的单例和普通的“饿汉模式”一样，都是在类加载（调用静态方法）的时候初始化。但是枚举类的另一个优点是能预防反射和序列化
     *
     * 当单例类里有其他静态方法的时候，推荐使用静态内部类的形式。
     * 当单例类里只有getInstance()方法的时候，推荐直接new一个静态的单例对象。
     * 当需要防止反射和序列化破坏单例的时候，推荐用枚举类的单例模式
     */
    INSTANCE;

    public Singleton4 getInstance() {
        return INSTANCE;
    }

    Singleton4() {
        System.out.println("枚举类单例实例化啦");
    }


}
