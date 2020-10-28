package code;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/28 0028 14:04
 */
public class OkHttpDemo {

    private static Logger log = LoggerFactory.getLogger(OkHttpDemo.class);

    private static final int OK = 200;

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build();

    public static void httpGet(String url) {
        if (url == null || "".equals(url)) {
            log.error("url为null!");
            return;
        }
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            parseAndPrintRespinse(response);
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }finally {
            if (response != null){
                response.close();
            }
        }
    }

    public static void httpGet(String url, Map<String, String> headers) {
        if (CollectionUtils.isEmpty((Collection<?>) headers)) {
            httpGet(url);
        }

        Request.Builder builder = new Request.Builder();
        headers.forEach((String key, String value) -> builder.header(key, value));
        Request request = builder.get().url(url).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            parseAndPrintRespinse(response);
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }finally {
            if (response != null){
                response.close();
            }
        }
    }

    private static void parseAndPrintRespinse(Response response) throws IOException {
        if (response.code() == OK) {
            log.info("OkHttpDemo GET 请求成功; [url={}]", response.request().url());
            log.info("响应状态为:" + response.code());
            log.info("响应内容长度为:" + response.body().contentLength());
            log.info("响应内容为:" + response.body().string());
        } else {
            log.warn("Http GET 请求失败; [errorCode = {} , url={}]", response.code(), response.request().url());
        }
    }
}
