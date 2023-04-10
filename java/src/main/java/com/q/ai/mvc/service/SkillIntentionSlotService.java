package com.q.ai.mvc.service;

import com.q.ai.mvc.dao.SkillIntentionDao;
import com.q.ai.mvc.dao.po.Intention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SkillIntentionSlotService {
    @Autowired
    private SkillIntentionDao skillIntentionDao;

    /**
     * 通过技能id获取意图id列表
     * @param id    技能id
     * @return
     */
    public List<Intention> getIntentions(Long id){
        return skillIntentionDao.getIntentions(id);
    }

    /**
     * 添加技能意图关系
     * @param intentionId   意图id
     * @param skillId   技能id
     * @return
     */
    public int addSkillIntention(Long intentionId, int skillId) {
        long min = 100000000L; // 最小值为100000000
        long max = 999999999L; // 最大值为999999999
        // 为关系随机生成一个id
        long FID = (long)(Math.random() * (max - min + 1)) + min;
        // 因为数据类型不统一，所以将其设置成map，方便在xml中转换
        Map<String,Object> map = new HashMap<>();
        map.put("FID",FID);
        map.put("skillId", skillId);
        map.put("intentionId", intentionId);
        return skillIntentionDao.addSkillIntention(map);
    }
}
