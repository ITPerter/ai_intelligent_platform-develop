package com.q.ai.component;

import com.q.ai.mvc.dao.SessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SpringAsync {

    @Autowired
    SessionDao sessionDao;

    /**
     * 必须使用其他类调用才能异步，因为异步是使用代理实现的
     *
     * @throws InterruptedException
     */
    @Async
    public void doAsync(String name) throws InterruptedException {
        System.out.println("开始执行异步任务……" + name);
        Thread.sleep(10 * 1000);
        System.out.println("执行异步任务结束……");
    }


}
