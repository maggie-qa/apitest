package com.apitest.utils;

import com.sun.tools.internal.jxc.ap.Const;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class HttpClientUtils {
    public static String doGet(String url) {
//        // 设置本地代理
//        HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
//        RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();
//        //默认代理设置
//        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 返回体
        HttpEntity httpEntity = null;
        // 创建HttpGet方法
        HttpGet get = new HttpGet(url);
        // 使用excute方法调用创建好的实例
        try {
            CloseableHttpResponse response = httpclient.execute(get);
            // 获取response的状态码
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {// 如果返回值为200，则处理httpentity
                httpEntity = response.getEntity();
                return EntityUtils.toString(httpEntity);
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
        return "error";
    }

    public static String doPost(String url, String params) {
        // 创建HttpClient连接池
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 返回体
        HttpEntity httpEntity = null;
        // 创建HttpPost方法
        HttpPost post = new HttpPost(url);
        // 设置参数格式为json格式
        post.setHeader("content-type", "application");
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        // 将params动态处理

        if (!isEmpty(params)) {
            // 将String类型的参数按照"&"分割
            String[] array_params = params.split("&");
            {
                // 再按照"="进行分割
                for (String array_param : array_params) {
                    String[] param = array_param.split("=");
                    parameters.add(new BasicNameValuePair(param[0], param[1]));
                }
            }
        }

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8"); // 将List转为httpEntity
            post.setEntity(entity);  // 传参
            CloseableHttpResponse response = httpclient.execute(post);
            // 获取response的状态码
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {// 如果返回值为200，则处理httpentity
                httpEntity = response.getEntity();
                return EntityUtils.toString(httpEntity);
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
        return "error";
    }

    public static String doPostJson(String url, String json) {
        // 创建httpClient连接池
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        HttpEntity httpEntity = null;
        // 设置参数,将String类型通过StringEntity转为json
        if (!isEmpty(json)) {
            StringEntity entity = new StringEntity(json, "utf-8");
            // 设置header
            post.setHeader("content-type", "application/json");
            post.setEntity(entity);
            try {
                CloseableHttpResponse response = httpClient.execute(post);
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == 200) {
                    httpEntity = response.getEntity();
                    return EntityUtils.toString(httpEntity);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpEntity != null) {
                    try {
                        EntityUtils.consume(httpEntity);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "error";

    }

    public static void main(String[] args) {
//        HttpClientUtils.doGet("http://118.24.13.38:8080/goods/UserServlet?method=loginMobile&loginname=test1&loginpass=test1");
//        System.out.println(doPost("http://118.24.13.38:8080/goods/UserServlet", "method=loginMobile&loginname=test1&loginpass=test1"));
        System.out.println(doPostJson("http://118.24.13.38:8080/goods/json2", "{\"count\":3}"));

    }

}
