package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Intention;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IntentionDao {

    /**
     * 通过任务型技能id获取对应的意图列表
     * @param id
     * @param offset
     * @param size
     * @return
     */
    public List<Intention> getIntentionByTaskId(@Param("id") int id,@Param("number") int number,@Param("size") int size);

    /**
     * 通过任务id获取对应意图数量
     * @param id
     * @return
     */
    public int getCountByTaskId(@Param("id") int id);

    Intention getByNumber(@Param("number") String number);
}
