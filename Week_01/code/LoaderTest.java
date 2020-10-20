package code;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/16 0016 11:03
 */
public class LoaderTest {

    /**
     * Class.forName()和ClassLoader.loadClass()区别
     *
     *      Class.forName()：将类的.class文件加载到jvm中之外，还会对类进行解释，执行类中的static块；
     *      ClassLoader.loadClass()：只干一件事情，就是将.class文件加载到jvm中，不会执行static中的内容,只有在newInstance才会去执行static块。
     *      Class.forName(name,initialize,loader)带参函数也可控制是否加载static块。并且只有调用了newInstance()方法采用调用构造函数，创建类的对象 。
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = LoaderTest.class.getClassLoader();
        System.out.println("类加载器：" + classLoader);
        //使用ClassLoader.loadClass()来加载类，不会执行初始化块
        classLoader.loadClass("classloadTest.Hello").newInstance();
        // 使用Class.forName()来加载类，默认会执行初始化块
//        Class.forName("classloadTset.Hello");

        // 使用Class.forName()来加载类，并指定ClassLoader，false 初始化时不执行静态块,true则加载
//        Class.forName("classloadTset.Hello", true, classLoader);
    }

}
