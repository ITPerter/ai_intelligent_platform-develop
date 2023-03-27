package com.q.ai.component.session;

import com.q.ai.biz.entity.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RequestContext {

    //MDC为“Mapped Diagnostic Context"
    public static final String TRACK_ID = "trackId";

    @Resource(name = "mysql")
    private SessionUtil sessionUtil;

    //耗时记录
    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    private ThreadLocal<Session> session = new ThreadLocal<>();

    public void init(String sessionId) {
        Session session = sessionUtil.getById(sessionId);
        if (null == session) {
            session = new Session(sessionId);
            sessionUtil.save(session);
        }
        this.session.set(session);
        startTime.set(System.currentTimeMillis());
    }

    public long getStartTime() {
        return startTime.get();
    }

    public Session getSession() {
        return session.get();
    }

    /**
     * save into threadLocal
     */
    public void save(Session session){
        this.session.set(session);
    }

    public void destroy() {
        sessionUtil.save(session.get());
        startTime.remove();
        ;
        //session 要的更改要保存回去
        session.remove();
    }

}
