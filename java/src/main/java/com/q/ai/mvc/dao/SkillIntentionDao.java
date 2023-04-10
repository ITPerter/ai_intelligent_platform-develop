package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Intention;

import java.util.List;
import java.util.Map;

public interface SkillIntentionDao {
    public List<Intention> getIntentions(Long id);

    int addSkillIntention(Map<String, Object> map);
}
