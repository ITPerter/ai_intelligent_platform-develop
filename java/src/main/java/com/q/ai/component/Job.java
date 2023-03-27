package com.q.ai.component;

import com.q.ai.App;
import com.q.ai.mvc.dao.SessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling//可以在启动类上注解也可以在当前文件
public class Job {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SessionDao sessionDao;

    @Scheduled(cron = "0 42 16 * * ?")
    public void delSession() {
        logger.info("定时任务");
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        sessionDao.delByTime(yesterday);
    }
}

