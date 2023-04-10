package com.q.ai.mvc.dao;

import com.q.ai.component.io.Page;
import com.q.ai.mvc.dao.po.Intention;
import com.q.ai.mvc.dao.po.TSkill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TSkillDao {
    /**
     * 通过id获取任务型技能
     * @return
     */
    public TSkill getTaskById(@Param("id") int id);

    /**
     * 通过编码获取技能id
     * @param number
     * @return
     */
    public Long getSkillId(@Param("number") String number);

    /**
     * 获取任务型技能分页数据（从offset开始检索，检索limit条数据）
     * @param offset
     * @param limit
     * @return
     */
    public List<TSkill> getTaskList(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 获取总的任务技能数
     * @return
     */
    public int getTaskCount();

    /**
     * 通过技能名查询数据
     * @param name
     * @param offset
     * @param limit
     * @return
     */
    public List<TSkill> getTaskListByName(@Param("name") String name,@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 更新任务技能数据
     * @param tSkill
     * @return
     */
    int updateTaskById(TSkill tSkill);

    int insertTask(TSkill tSkill);

    /**
     * 通过id列表删除任务技能数据
     * @param idList
     * @return
     */
    int deleteTaskById(List<Integer> idList);

    /**
     * 添加任技能
     * @param tSkill
     * @return
     */
    int insertTaskSkill(TSkill tSkill);

    List<TSkill> getQuestionAnsweringSkills(@Param("offset") int offset,@Param("limit") int size);

    int getQuestionAnsweringCount();

    int addQuestionAnsweringSkill(TSkill tSkill1);

    /**
     * 删除问答型技能
     * @param skillIdList
     * @return
     */
    int deleteQuestionAnsweringSkills(List<Long> skillIdList);

    /**
     * 更新问答型技能
     * @param tSkill
     * @return
     */
    int updateQuestionAnsweringSkill(TSkill tSkill);
}
