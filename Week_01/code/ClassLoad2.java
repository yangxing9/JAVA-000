package code;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/16 0016 14:51
 */
public class ClassLoad2 extends ClassLoader {

    private static final int offset = 255;

    public static void main(String [] args) throws Exception{
        ClassLoad2 myloader = new ClassLoad2();
        String path = ClassLoad2.class.getClassLoader().getResource("classloadTest/Hello.xlass").getPath();
        Class<?> c = myloader.findClass(path);
        Object obj = c.newInstance();
        System.out.println("类名：" + obj.getClass().getName());
        Method m = c.getMethod("hello");
        m.invoke(obj);
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
        byte[] bytes = new byte[1024];
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(name);
            out = new ByteArrayOutputStream();
            int len;
            while((len = in.read(bytes)) != -1){
                out.write(bytes, 0, len);
            }
            bytes = out.toByteArray();
        } finally {
            if (out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
        }
        convertByte(bytes);
        return bytes;
    }

    private void convertByte(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (offset - bytes[i]);
        }
    }
}
