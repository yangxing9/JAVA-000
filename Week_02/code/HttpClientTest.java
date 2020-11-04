package code;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/28 0028 14:54
 */
public class HttpClientTest {

    private final static String url = "http://127.0.0.1:8808/aaa/test";

    public static void main(String[] args) {
        HttpClientDemo.testHttpClientGetWithOutParams(url);
//        OkHttpDemo.httpGet(url);
    }
}
