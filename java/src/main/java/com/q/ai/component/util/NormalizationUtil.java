package com.q.ai.component.util;

import com.q.ai.component.io.RsException;
import com.time.nlp.TimeNormalizer;
import com.time.nlp.TimeUnit;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class NormalizationUtil {
    public static LocalDateTime getNormalizationTime(String time){
        if(StringUtils.isEmpty(time)){
            return null;
        }
        URL url = NormalizationUtil.class.getResource("/time-nlp/TimeExp.m");
        TimeNormalizer normalizer = null;
        try {
            normalizer = new TimeNormalizer(url.toURI().toString());
        } catch (Exception e) {
            throw new RsException("资源文件加载失败：" + e.getMessage());
        }
        normalizer.setPreferFuture(true);

        normalizer.parse(time);// 抽取时间
        TimeUnit[] unit = normalizer.getTimeUnit();

        if(unit.length==0){
            return null;
        }

        return LocalDateTime.ofInstant(unit[0].getTime().toInstant(), ZoneId.systemDefault());


    }

}
