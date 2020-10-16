package code;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/16 0016 14:51
 */
public class ClassLoad2 extends ClassLoader {

    public static void main(String [] args) throws Exception{
        ClassLoad2 myloader = new ClassLoad2();
        String path = ClassLoad2.class.getClassLoader().getResource("classloadTset/Hello.xlass").getPath();
        Class c = myloader.findClass(path);
        Object obj = c.newInstance();
        System.out.println("类名：" + obj.getClass().getName());
        Method m = c.getMethod("hello", null);
        m.invoke(obj, null);
    }

    @Override
    protected Class<?> findClass(String name){
        byte[] b = null;
        try {
            b = getByte(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(null, b, 0, b.length);
    }

    private byte[] getByte(String name) throws IOException {
        FileInputStream in = new FileInputStream(name);
        byte[] b = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        while((len = in.read(b)) != -1){
            out.write(b, 0, len);
        }
        out.close();
        b = out.toByteArray();
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (255 - b[i]);
        }
        return b;
    }
}
