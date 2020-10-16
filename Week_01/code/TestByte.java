package code;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/16 0016 14:09
 */
public class TestByte {

    private static int a = 99;
    private static final int b = 100;

    public static void main(String[] args) {
        TestByte demo = new TestByte();
        long x = 5;
        long y = 10;
        long z = x + y;
        int res = demo.add(a,b);
        for (int i = 0; i < res; i++) {
            if (i < 2){
                System.out.println("结果：" + i * 2);
            }
        }
    }

    private int add(int a,int b){
        return a + b;
    }

}
