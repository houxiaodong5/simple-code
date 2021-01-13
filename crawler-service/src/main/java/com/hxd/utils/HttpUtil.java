package com.hxd.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by Administrator on 2020/11/19/019.
 */
public class HttpUtil {

    static final Charset UTF8_ENCODE = Charset.forName("UTF-8");

    public final static String get(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpGet httpget = new HttpGet(url);
            httpget.addHeader("Accept", "text/html");
            httpget.addHeader("Accept-Charset", "utf-8");
            httpget.addHeader("Accept-Encoding", "gzip");
            httpget.addHeader("Accept-Language", "en-US,en");
            httpget.addHeader("User-Agent",
                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        Date date = new Date();
                        System.out.println(date);
                        System.exit(0);
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            return httpclient.execute(httpget, responseHandler);
        } finally {
            httpclient.close();
        }
    }

    /**
     *  发送post请求 参数为json格式
     * @param url 请求url
     * @param jsonData  json格式参数
     * @return String
     * @throws Exception
     */
    public final static String postByJSONString(String url, String jsonData) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Accept", "text/html");
            httpPost.addHeader("Accept-Charset", "utf-8");
            httpPost.addHeader("Accept-Encoding", "gzip");
            httpPost.addHeader("Accept-Language", "en-US,en");
            httpPost.addHeader("User-Agent",
                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.160 Safari/537.22");

            StringEntity stringEntity = new StringEntity(jsonData, UTF8_ENCODE);
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);

            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    Date date = new Date();
                    System.out.println(date);
                    System.exit(0);
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            return httpclient.execute(httpPost, responseHandler);
        } finally {
            httpclient.close();
        }
    }

}