package code;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/10/27 0027 21:07
 */
public class HttpClientDemo {

    private static Logger log = LoggerFactory.getLogger(HttpClientDemo.class);

    /**
     * 设置连接超时时间(单位毫秒)
     */
    private static final int CONNECT_TIME_OUT = 5000;
    /**
     * 设置请求超时时间(单位毫秒)
     */
    private static final int CONNECTION_REQUEST_TIME_OUT = 5000;
    /**
     * socket读写超时时间(单位毫秒)
     */
    private static final int SOCKET_TIME_OUT = 5000;

    private static final CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    public static void testHttpClientGetWithOutParams(String url){

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(CONNECT_TIME_OUT)
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIME_OUT)
                    .setSocketTimeout(SOCKET_TIME_OUT)
                    .build();

            httpGet.setConfig(requestConfig);

            response = httpClient.execute(httpGet);
            parseAndPrintResponse(response,url);
        } catch (IOException e) {
            throw new RuntimeException("HttpClient GET 请求失败,url:" + url, e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void parseAndPrintResponse(CloseableHttpResponse response,String url) throws IOException {
        HttpEntity responseEntity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            log.info("HttpClientDemo GET 请求成功; [url={}]", url);
            log.info("响应状态为:" + response.getStatusLine());
            log.info("响应内容长度为:" + responseEntity.getContentLength());
            log.info("响应内容为:" + EntityUtils.toString(responseEntity));
        } else {
            log.warn("Http GET 请求失败; [errorCode = {} , url={}]", response.getStatusLine().getStatusCode(), url);
        }
    }

}
