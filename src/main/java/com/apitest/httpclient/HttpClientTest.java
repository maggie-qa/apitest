package com.apitest.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientTest {
    public static void main(String[] args) throws IOException {
        // HttpClient使用步骤
        // 创建httpclent默认连接池
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 返回体
        HttpEntity httpEntity = null;
        // 创建HttpGet方法
        HttpGet get = new HttpGet("http://www.baidu.com");
        // 使用excute方法调用创建好的实例
        try {
            CloseableHttpResponse response = httpclient.execute(get);
            // 获取response的状态码
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {// 如果返回值为200，则处理httpentity
                httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity);
                System.out.println(result);
            } else {  //未返回200，则打印statusCode
                System.out.println(statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpEntity != null) { // 关闭流
                    EntityUtils.consume(httpEntity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
