package com.q.ai.component.session;

import com.q.ai.biz.entity.Session;

import java.util.Map;

/**
 * 通过不同的实现类快速替换session存储
 */
public interface SessionUtil {

    public Session getById(String id);

    public int delById(String id);

    public void save(Session session);

    public Session buildIntentAndFillSlot(Session session, String intentNumber, Map<String, Object> slot2ValueMap, String userOriginString);

    public Session buildIntentionAndFillWordSlot(Session session, String intentNumber, Map<String, Object> slot2ValueMap, String userOriginString);

}
