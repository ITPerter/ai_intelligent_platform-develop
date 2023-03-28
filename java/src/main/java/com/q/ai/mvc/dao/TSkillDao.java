package com.q.ai.mvc.dao;

import com.q.ai.component.io.Page;
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

}
