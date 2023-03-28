package com.q.ai.mvc.service;

import com.q.ai.mvc.dao.TSkillDao;
import com.q.ai.mvc.dao.po.TSkill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TSkillService {
    @Resource
    TSkillDao tSkillDao;

    /**
     * 通过id获取任务技能
     * @param id
     * @return
     */
    public TSkill getTaskById(int id) {
        return tSkillDao.getTaskById(id);
    }

    /**
     * 获取分页的任务技能
     * @param number
     * @param size
     * @return
     */
    public List<TSkill> getTaskList(int number,int size) {
        return tSkillDao.getTaskList(number,size);
    }

    /**
     * 获取任务技能总数
     * @return
     */
    public int getTaskCount(){
        return tSkillDao.getTaskCount();
    }

    /**
     * 通过名称获取任务技能
     * @param name
     * @param number
     * @param size
     * @return
     */
    public List<TSkill> getTaskListByName(String name,int number,int size) {
        return tSkillDao.getTaskListByName(name,number,size);
    }
}
