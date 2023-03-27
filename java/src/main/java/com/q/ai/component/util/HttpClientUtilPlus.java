package com.q.ai.component.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class HttpClientUtilPlus {
    // post请求
    public static JSONObject doPostJson(URI uri, Map map) throws IOException {
        String result = "";
        HttpPost httpPost = new HttpPost(uri);

        // 将map对象转换为json字符串，并放入entity中
        StringEntity entity = new StringEntity(JSON.toJSONString(map), "UTF-8");

        // post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        try {
            // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            HttpResponse response = httpClient.execute(httpPost);
            int res = response.getStatusLine().getStatusCode();
            if (res == 200) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

        }
        return JSON.parseObject(result);
    }
}
