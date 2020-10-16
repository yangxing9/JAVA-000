package code;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/16 0016 11:02
 */
public class MyClassLoader extends ClassLoader {

    @Override
    public Class findClass(String name){
        byte[] b = null;
        try {
            b = getByte(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(null, b, 0, b.length);
    }

    private byte[] getByte(String name) throws IOException{
        FileInputStream in = new FileInputStream(name);
        byte[] b = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        while((len = in.read(b)) != -1){
            out.write(b, 0, len);
        }
        out.close();
        b = out.toByteArray();
        return b;
    }
}
