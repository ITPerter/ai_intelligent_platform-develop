package com.q.ai.mvc.service;

import com.q.ai.mvc.dao.IntentionDao;
import com.q.ai.mvc.dao.po.Intention;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class IntentionService {
    @Resource
    IntentionDao intentionDao;

    /**
     * 通过任务技能id获取意图列表
     * @param id
     * @return
     */
    public List<Intention> getIntentionByTaskId(int id,int number,int limit) {
        return intentionDao.getIntentionByTaskId(id,number,limit);
    }

    /**
     * 通过id获取
     * @param id
     * @return
     */
    public int getCountByTaskId(int id) {
        return intentionDao.getCountByTaskId(id);
    }

    public Intention getByNumber(String number){
        return intentionDao.getByNumber(number);
    }
}
