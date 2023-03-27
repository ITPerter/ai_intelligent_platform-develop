package com.q.ai.component.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * 使用apache 4.57的HttpClient实现
 */
public class HttpClientUtil {

    public static String doGet(String urlString){

        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(urlString);

        CloseableHttpResponse response = null;
        String result = "";

        try {
            httpGet.setHeader("User-agent","Mozilla/5.0 Chrome 82 http4.57");
            response = client.execute(httpGet);

            int httpCode = response.getStatusLine().getStatusCode();
            if(httpCode == 200){
                //InputStream instream = entity.getContent(); entity里包含流，所以要关闭
                HttpEntity entity = response.getEntity();
                if(entity != null) {
                    result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
                //为什么要调用这个方法，因为：entity.getContent(); entity里包含流，所以要关闭
                EntityUtils.consume(entity);
            }else{
                throw new Exception(HttpCodeEnum.CODE_200.getByCode(httpCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    public static String doJson(String urlString,Object data){

        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(urlString);


        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10_000).setConnectionRequestTimeout(10_000)
                .setSocketTimeout(10_000).build();
        httpPost.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        String result = "";

        try {
            httpPost.setHeader("User-agent","Mozilla/5.0 Chrome 90 http4.57");
            httpPost.setHeader("Content-Type","application/json;charset=utf-8");
            HttpEntity params = new StringEntity(JSON.toJSONString(data),"utf-8");
            httpPost.setEntity(params);

            response = client.execute(httpPost);

            int httpCode = response.getStatusLine().getStatusCode();
            if(httpCode == 200){
                HttpEntity entity = response.getEntity();
                if(entity != null) {
                    result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
                //为什么要调用这个方法，因为：entity.getContent(); entity里包含流，所以要关闭
                EntityUtils.consume(entity);
            }else{
                throw new Exception(HttpCodeEnum.CODE_200.getByCode(httpCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }




}
