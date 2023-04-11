package com.q.ai.mvc.service;

import com.alibaba.fastjson.JSONObject;
import com.q.ai.component.annotation.LogMethod;
import com.q.ai.component.session.RequestContext;
import com.q.ai.component.io.RsException;
import com.q.ai.component.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 算法处理业务
 */
@Service
public class NlpService {

    /**
     * 算法处理接口
     */
    @Value("${nlp_host}")
    private String nlp_url;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @param robotId
     * @return
     */
    @LogMethod(true)
    public boolean train(int robotId) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("bot_id", robotId);
        bodyMap.put("msgtype", "train_bot");
        bodyMap.put("track_id", MDC.get(RequestContext.TRACK_ID));
        JSONObject nlp = postNlp(bodyMap);
        System.out.println("----------------------------------------------\n"
                            +nlp.toJSONString()+
                            "-----------------------------------------------");
        return true;
    }

    /**
     * @param robotId
     * @param chatMsg
     * @param intentNumber
     * @param slotNumber
     * @return
     */
    public JSONObject getIntent(int robotId, String chatMsg, String intentNumber, String slotNumber) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("payload", chatMsg);
        bodyMap.put("bot_id", robotId);
        bodyMap.put("msgtype", "nluparse");
        bodyMap.put("track_id", MDC.get(RequestContext.TRACK_ID));
        if (!StringUtils.isEmpty(intentNumber)) {
            bodyMap.put("intent_name", intentNumber);
            if (!StringUtils.isEmpty(slotNumber)) {
                bodyMap.put("wordslot_num", slotNumber);
            }
        }


        JSONObject dataJson = postNlp(bodyMap);


        return dataJson;

    }

    /**
     *
     * @param request
     * @return
     */
    @LogMethod(true)
    private JSONObject postNlp(Map<String, Object> request) {
        if (StringUtils.isEmpty(nlp_url)) {
            throw new RsException("请配置nlp服务地址");
        }
        String rs = HttpClientUtil.doJson(nlp_url, request);
        logger.info("NLP入参：{}，NLP返回：{}", request, rs);
        JSONObject rsJson = JSONObject.parseObject(rs);
        Integer code = rsJson.getInteger("error_code");
        if( code == null || code != 0){
            throw new RsException("nlp服务出错："+rsJson);
        }
        return rsJson.getJSONObject("data");
    }
}
