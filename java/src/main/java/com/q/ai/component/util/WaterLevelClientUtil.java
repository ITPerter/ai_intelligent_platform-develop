package com.q.ai.component.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class WaterLevelClientUtil {


    private final static String HOST = "172.17.4.154";
    private final static   int PORT = 8130;
    public static JSONObject selectWater_level(Map map){
        // 参数
        URI uri = null;
        try {
            // 设置uri信息,并将参数集合放入uri;
            // 注:这里也支持一个键值对一个键值对地往里面放setParameter(String key, String value)
            uri = new URIBuilder().setScheme("http").setHost(HOST).setPort(PORT)
                    .setPath("/xitou/ivrservice").build();
            JSONObject object = HttpClientUtilPlus.doPostJson(uri, map);
            return object;
        } catch (URISyntaxException | IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
