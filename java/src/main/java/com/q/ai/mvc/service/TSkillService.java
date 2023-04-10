package com.q.ai.mvc.service;

import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.dao.TSkillDao;
import com.q.ai.mvc.dao.po.TSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TSkillService {
    @Resource
    TSkillDao tSkillDao;

    @Autowired
    RequestContext requestContext;

    /**
     * 通过编码获取技能id
     * @param number
     * @return
     */
    public Long getSkillId(String number) {
        Long id = tSkillDao.getSkillId(number);
        return id;
    }

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


    /**
     * 存储/修改一个机技能数据
     * @param tSkill
     * @return
     */
    public int save(TSkill tSkill) {
        if (tSkill.getId() != 0) {//编辑
            tSkill.setUpdateTime(LocalDateTime.now());
            return tSkillDao.updateTaskById(tSkill);
        } else {//新增
            int userId = requestContext.getSession().getUserId();
            tSkill.setCreatorId(userId);
            tSkill.setCreateTime(LocalDateTime.now());
            return tSkillDao.insertTask(tSkill);
        }
    }

    public int deleteTaskById(List<Integer> idList) {
        return tSkillDao.deleteTaskById(idList);
    }

    public int insertTaskSkill(TSkill tSkill) {
        int userId = requestContext.getSession().getUserId();
        tSkill.setCreatorId(userId);
        tSkill.setCreateTime(LocalDateTime.now());
        return tSkillDao.insertTaskSkill(tSkill);
    }

    public List<TSkill> getQuestionAnsweringSkills(int number, int size) {
        return tSkillDao.getQuestionAnsweringSkills(number,size);
    }

    public int getQuestionAnsweringCount() {
        return tSkillDao.getQuestionAnsweringCount();
    }

    /**
     * 添加问答技能
     * @param tSkill
     * @return
     */
    public int addQuestionAnsweringSkill(TSkill tSkill) {
        int userId = requestContext.getSession().getUserId();
        tSkill.setCreatorId(userId);
        tSkill.setCreateTime(LocalDateTime.now());
        return tSkillDao.addQuestionAnsweringSkill(tSkill);
    }

    public int deleteQuestionAnsweringSkills(List<Long> skillIdList) {
        return tSkillDao.deleteQuestionAnsweringSkills(skillIdList);
    }

    public int updateQuestionAnsweringSkill(TSkill tSkill) {
        tSkill.setUpdateTime(LocalDateTime.now());
        return tSkillDao.updateQuestionAnsweringSkill(tSkill);
    }
}
