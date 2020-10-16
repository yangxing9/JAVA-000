package code;

import code.MyClassLoader;

import java.lang.reflect.Method;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/16 0016 11:36
 */
public class MyClassLoaderTest {

    /**
     * 强行加载自己定义的 java.lang.String 类时，会报错 ：Prohibited package name: java.lang
     */
    public static void main(String [] args) throws Exception{
        MyClassLoader myloader = new MyClassLoader();
        String path1 = "E:\\JAVA-000\\classes\\production\\JAVA-000\\java\\lang\\String.class";
        Class c = myloader.findClass(path1);
        Object obj = c.newInstance();
        System.out.println(obj.getClass().getName());
        Method m = c.getMethod("say", null);
        m.invoke(obj, null);
    }

}
