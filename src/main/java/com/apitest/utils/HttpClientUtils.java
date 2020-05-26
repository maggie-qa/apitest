package com.apitest.utils;

import com.sun.tools.internal.jxc.ap.Const;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
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
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class HttpClientUtils {
    /**
     * get支持
     *
     * @param url
     * @return
     */
    public static boolean openProxy = false;  // 默认代理不开启
    static CloseableHttpClient closeableHttpClient;

    public static void initHttpClient() {  // 初始化HttpClient
        if (closeableHttpClient != null) {  // 保证httpClient只有一个
            return;
        }
        if (openProxy) {
            // 设置本地代理
            HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
            RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();
            //默认代理设置
            closeableHttpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        } else {
            closeableHttpClient = HttpClients.createDefault();
        }

    }

    /**
     * get请求-头部支持
     * @param url
     * @param headerMap
     * @return
     */
    public static String doGet(String url, Map<String, Object> headerMap) {
        initHttpClient();
        HttpEntity httpEntity = null;
        HttpGet httpGet = new HttpGet(url);
        if (!MapUtils.isEmpty(headerMap)) {
            Set<String> headerSet = headerMap.keySet();
            for (String key : headerSet) {
                httpGet.setHeader(key, String.valueOf(headerMap.get(key)));
            }
        }
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                httpEntity = response.getEntity();
                return EntityUtils.toString(httpEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    /**
     * get请求-不支持头部
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * post支持-string
     *
     * @param url
     * @param params
     * @return
     */

    @Deprecated
    public static String doPost(String url, String params) {
        // 创建HttpClient连接池
        initHttpClient();

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
            CloseableHttpResponse response = closeableHttpClient.execute(post);
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

    /**
     * post支持-json,头部为null
     *
     * @param url
     * @param json
     * @return
     */
    public static String doPostJson(String url, String json) {
        return doPostJson(url, json, null);

    }

    /**
     * postjson-头部支持
     *
     * @param url
     * @param json
     * @param headerMap 头部支持
     * @return
     */
    public static String doPostJson(String url, String json, Map<String, Object> headerMap) {
        // 初始化httpClient连接池
        initHttpClient();
        openProxy = true;
        HttpEntity httpEntity = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("content-type", "application/json");
        // 将header转为list添加至请求中
        if (!MapUtils.isEmpty(headerMap)) {// 判断空处理
            Set<String> headerSet = headerMap.keySet();
            for (String key : headerSet) {
                httpPost.setHeader(key, String.valueOf(headerMap.get(key))); // 使用String.valueOf可以将入参修改为String类型
            }
        }
        StringEntity entity = new StringEntity(json, "utf-8");
        try {
            httpPost.setEntity(entity);
            CloseableHttpResponse respone = closeableHttpClient.execute(httpPost);
            StatusLine statusLine = respone.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                httpEntity = respone.getEntity();
                return EntityUtils.toString(httpEntity, "utf-8");
            } else {
                System.out.println(statusLine.getStatusCode());
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
        return "error";
    }

    /**
     * post  -参数map支持
     *
     * @param url
     * @param paramsMap
     * @return
     */
    public static String doPost(String url, Map<String, Object> paramsMap) {  // 传入的参数格式为Map
        // 创建HttpClient连接池
        initHttpClient();
        // 返回体
        HttpEntity httpEntity = null;
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> forparams = new ArrayList<NameValuePair>();
        // 获取map的所有key
        Set<String> keySet = paramsMap.keySet();// keySet可以获取map所有的key值
        for (String key : keySet) {
            {
                forparams.add(new BasicNameValuePair(key, String.valueOf(paramsMap.get(key))));
            }
        }
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(forparams, "utf-8");
            httpPost.setEntity(urlEncodedFormEntity);
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                httpEntity = response.getEntity();
                return EntityUtils.toString(httpEntity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";

    }

    public static void main(String[] args) {
//        HttpClientUtils.doGet("http://118.24.13.38:8080/goods/UserServlet?method=loginMobile&loginname=test1&loginpass=test1");
//        System.out.println(doPost("http://118.24.13.38:8080/goods/UserServlet", "method=loginMobile&loginname=test1&loginpass=test1"));

        String s = "method=loginMobile&loginname=test1&loginpass=test1&test=您好";
        String header = "token=61b3590090982a0185dda9d3bd793b46;userId=123";
        System.out.println(doPostJson("http://118.24.13.38:8080/goods/json2", "{\"count\":3}", StringMapUtils.convert(header, ";")));


    }

}
